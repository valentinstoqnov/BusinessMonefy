package org.elsys.valiolucho.businessmonefy;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalcActivity extends AppCompatActivity {
    TextView calcDisplay;
    TextView viewDisplay;
    private ButtonClickListener buttonClick = new ButtonClickListener();
    protected BigDecimal numBuff = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_EVEN);;
    protected String operation;

    RelativeLayout relativeLayout;
    EditText nameET;
    EditText descriptionET;
    private static final int REQUIRED_NAME_LENGTH = 1;
    private static final int REQUIRED_DESCR_LENGTH = REQUIRED_NAME_LENGTH;

    DataBaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        relativeLayout = (RelativeLayout) findViewById(R.id.mainCalcRLayout);
        calcDisplay = (TextView) findViewById(R.id.calcDisplay);
        viewDisplay = (TextView) findViewById(R.id.textView);
        nameET = (EditText) findViewById(R.id.nameEditText);
        descriptionET = (EditText) findViewById(R.id.descriptionEditText);

        int idList[] = {R.id.buttonZero,R.id.buttonOne,R.id.buttonTwo,R.id.buttonThree,R.id.buttonFour,R.id.buttonFive,
                R.id.buttonSix,R.id.buttonSeven,R.id.buttonEight,R.id.buttonNine,R.id.buttonPlus,R.id.buttonMinus,
                R.id.buttonMultiply,R.id.buttonDivide,R.id.buttonGradiation,
                R.id.buttonNegative,R.id.buttonPoint, R.id.buttonBackspace, R.id.buttonEqual,
                R.id.buttonDelete};

        boolean isMinus = false;
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            isMinus = true;
        }

        if(isMinus) {
            viewDisplay.setTextColor(Color.parseColor("#C85050"));
            for (int id: idList) {
                View v = (View) findViewById(id);
                v.setBackgroundColor(ContextCompat.getColor(this, R.color.colorTrMinus));
                //v.setBackgroundColor(Color.parseColor("#C85050"));
                v.setOnClickListener(buttonClick);
            }
        }else{
            viewDisplay.setTextColor(Color.parseColor("#62F464"));
            for (int id: idList) {
                View v = (View) findViewById(id);
                v.setBackgroundColor(ContextCompat.getColor(this, R.color.colorTrPlus));
                //(Color.parseColor("#62F464"));
                v.setOnClickListener(buttonClick);
            }
        }
        leftSwipeListener(isMinus);
    }

    private void leftSwipeListener(final boolean minus) {
        relativeLayout.setOnTouchListener(new OnSwipeTouchListener(CalcActivity.this) {
            @Override
            public void onSwipeLeft() {
                String name = nameET.getText().toString();
                String description = descriptionET.getText().toString();
                BigDecimal money = new BigDecimal(viewDisplay.getText().toString());
                if (minus) {
                    money = money.negate();
                }
                if(name.length() < REQUIRED_NAME_LENGTH) {
                    Toast.makeText(CalcActivity.this, "Name is too short", Toast.LENGTH_SHORT).show();
                }else {
                    if(description.length() < REQUIRED_DESCR_LENGTH) {
                        Toast.makeText(CalcActivity.this, "Description is too short", Toast.LENGTH_SHORT).show();
                    }else{
                        if(money.compareTo(BigDecimal.ZERO) == 0) {
                            Toast.makeText(CalcActivity.this, "Money can not be zero", Toast.LENGTH_SHORT).show();
                        }else {
                            Transaction transaction = new Transaction(name, description, money);
                            transaction.setDate();
                            myDb = DataBaseHelper.getInstance(getApplicationContext());
                            myDb.insertData(transaction);
                            Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
            }
        });
    }

    private BigDecimal calculate(BigDecimal val) {
        Calculator calculator = new Calculator(numBuff, val);
        BigDecimal result = new BigDecimal(0.0).setScale(2, BigDecimal.ROUND_HALF_EVEN);;
        switch (operation) {
            case "+":
                result = calculator.add();
                break;
            case "-":
                result = calculator.sub();
                break;
            case "*":
                result = calculator.mul();
                break;
            case "/":
                result = calculator.div();
                break;
            /*case "%" :
                result = calculator.percent();
                break;*/
            case "^" :
                result = calculator.grad();
                break;
        }
        return result;
    }

    private BigDecimal calculate() {
        Calculator calculator = new Calculator(numBuff);
        BigDecimal result;
        /*switch (operation) {
            case "sqrt" :
                result = calculator.sqrt();
                break;
            case "neg" :*/
                result = calculator.neg().setScale(2, BigDecimal.ROUND_HALF_EVEN);;
               // break;
        //}
        return result;
    }

    class ButtonClickListener implements View.OnClickListener{
        private boolean equalFlag = false;
        private boolean operationFlag = false;

        @Override
        public void onClick(View v) {
            String viewDisplayCurr = viewDisplay.getText().toString();
            //String vdisp = viewDisplay.getText().toString();
            switch (v.getId()){
                case R.id.buttonDelete :
                    calcDisplay.setText("");
                    viewDisplay.setText("0");
                    numBuff = BigDecimal.ZERO;
                    operation = "";
                    operationFlag = false;
                    equalFlag = false;
                    break;
                case R.id.buttonBackspace :
                    String displayStr = calcDisplay.getText().toString();
                    if(displayStr.length() > 0) {
                        displayStr = displayStr.substring(0, displayStr.length() - 1);
                        calcDisplay.setText(displayStr);

                        displayStr = viewDisplay.getText().toString();
                        displayStr = displayStr.substring(0, displayStr.length() - 1);
                        viewDisplay.setText(displayStr);
                    }
                    break;
                case R.id.buttonPoint :
                    String displayCurr = calcDisplay.getText().toString();
                    if(displayCurr.equals("")) {
                        displayCurr += "0";
                    }
                    displayCurr += '.';
                    calcDisplay.setText(displayCurr);

                    viewDisplayCurr = String.format("%s%s", viewDisplay.getText().toString(), ".");
                    viewDisplay.setText(viewDisplayCurr);
                    break;
                case R.id.buttonPlus :
                    //String vdisp = viewDisplay.getText().toString();
                    try{
                        numBuff = new BigDecimal(calcDisplay.getText().toString());
                    }catch (NumberFormatException nfe) {
                        Pattern pattern = Pattern.compile("\\d+|-\\d+");
                        Matcher matcher = pattern.matcher(viewDisplayCurr);
                        if(matcher.matches()) {
                            numBuff = new BigDecimal(viewDisplayCurr);
                        }else{
                            numBuff = BigDecimal.ZERO;
                            viewDisplayCurr = "0";
                        }
                    }
                    operation = "+";
                    viewDisplayCurr = String.format("%s%s", viewDisplayCurr, operation);
                    viewDisplay.setText(viewDisplayCurr);
                    calcDisplay.setText("");
                    operationFlag = true;
                    break;
                case R.id.buttonMinus :
                    //String vdisp = viewDisplay.getText().toString();
                    try{
                        numBuff = new BigDecimal(calcDisplay.getText().toString());
                    }catch (NumberFormatException nfe) {
                        Pattern pattern = Pattern.compile("\\d+|-\\d+");
                        Matcher matcher = pattern.matcher(viewDisplayCurr);
                        if(matcher.matches()) {
                            numBuff = new BigDecimal(viewDisplayCurr);
                        }else{
                            numBuff = BigDecimal.ZERO;
                            viewDisplayCurr = "0";
                        }
                    }
                    operation = "-";

                    viewDisplayCurr = String.format("%s%s", viewDisplayCurr, operation);
                    viewDisplay.setText(viewDisplayCurr);
                    calcDisplay.setText("");
                    operationFlag = true;
                    break;
                case R.id.buttonMultiply :
                    //String vdisp = viewDisplay.getText().toString();
                    try{
                        numBuff = new BigDecimal(calcDisplay.getText().toString());
                    }catch (NumberFormatException nfe) {
                        Pattern pattern = Pattern.compile("\\d+");
                        Matcher matcher = pattern.matcher(viewDisplayCurr);
                        if(matcher.matches()) {
                            numBuff = new BigDecimal(viewDisplayCurr);
                        }else{
                            numBuff = BigDecimal.ZERO;
                            viewDisplayCurr = "0";
                        }
                    }
                    operation = "*";

                    viewDisplayCurr = String.format("%s%s", viewDisplayCurr, operation);
                    viewDisplay.setText(viewDisplayCurr);
                    operationFlag = true;
                    calcDisplay.setText("");
                    break;
                case R.id.buttonDivide :
                    //String vdisp = viewDisplay.getText().toString();
                    try{
                        numBuff = new BigDecimal(calcDisplay.getText().toString());
                    }catch (NumberFormatException nfe) {
                        Pattern pattern = Pattern.compile("\\d+|-\\d+");
                        Matcher matcher = pattern.matcher(viewDisplayCurr);
                        if(matcher.matches()) {
                            numBuff = new BigDecimal(viewDisplayCurr);
                        }else{
                            numBuff = BigDecimal.ZERO;
                            viewDisplayCurr = "0";
                        }
                    }
                    operation = "/";

                    viewDisplayCurr = String.format("%s%s", viewDisplayCurr, operation);
                    viewDisplay.setText(viewDisplayCurr);
                    calcDisplay.setText("");
                    operationFlag = true;
                    break;
                /*case R.id.buttonPercent :
                    try{
                        numBuff = Double.parseDouble(calcDisplay.getText().toString());
                    }catch (NumberFormatException nfe) {
                        numBuff = Double.parseDouble(viewDisplay.getText().toString());
                    }
                    operation = "%";

                    viewDisplayCurr = String.format("%s%s", viewDisplay.getText().toString(), operation);
                    viewDisplay.setText(viewDisplayCurr);
                    operationFlag = true;
                    break;
                case R.id.buttonSquareRoot :
                    try{
                        numBuff = Double.parseDouble(calcDisplay.getText().toString());
                    }catch (NumberFormatException nfe) {
                        numBuff = Double.parseDouble(viewDisplay.getText().toString());
                    }
                    operation = "sqrt";

                    viewDisplayCurr = String.format("%s(%s)", viewDisplay.getText().toString(), operation);
                    viewDisplay.setText(viewDisplayCurr);
                    operationFlag = true;
                    break;*/
                case R.id.buttonEqual :
                    BigDecimal secondBuff;
                    BigDecimal result = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_EVEN);;
                    if(operation.equals("neg")) {
                        result = calculate();
                    }else{
                        try {
                            secondBuff = new BigDecimal(calcDisplay.getText().toString()).setScale(2, BigDecimal.ROUND_HALF_EVEN);;
                            result = calculate(secondBuff);
                        } catch (NumberFormatException nfe) {
                            String msg = String.format("Can not <%s>, <%s> with <%s>", operation, numBuff.toString(), calcDisplay.getText().toString());
                            calcDisplay.setText(msg);
                        }
                    }
                    DecimalFormat df = new DecimalFormat("###.#");
                    calcDisplay.setText("");
                    operation = "";
                    viewDisplay.setText(df.format(result));
                    equalFlag = true;
                    break;
                case R.id.buttonGradiation :
                    //String vdisp = viewDisplay.getText().toString();
                    try{
                        numBuff = new BigDecimal(calcDisplay.getText().toString());
                    }catch (NumberFormatException nfe) {
                        Pattern pattern = Pattern.compile("\\d+|-\\d+");
                        Matcher matcher = pattern.matcher(viewDisplayCurr);
                        if(matcher.matches()) {
                            numBuff =new BigDecimal(viewDisplayCurr);
                        }else{
                            numBuff = BigDecimal.ZERO;
                            viewDisplayCurr = "0";
                        }
                    }
                    operation = "^";

                    viewDisplayCurr = String.format("%s%s", viewDisplayCurr, operation);
                    viewDisplay.setText(viewDisplayCurr);
                    calcDisplay.setText("");
                    operationFlag = true;
                    break;
                case R.id.buttonNegative :
                    try{
                        numBuff = new BigDecimal(calcDisplay.getText().toString());
                    }catch (NumberFormatException nfe) {
                        Pattern pattern = Pattern.compile("\\d+|-\\d+");
                        Matcher matcher = pattern.matcher(viewDisplayCurr);
                        if(matcher.matches()) {
                            numBuff = new BigDecimal(viewDisplayCurr);
                        }else{
                            numBuff = BigDecimal.ZERO;
                            viewDisplayCurr = "0";
                        }
                    }
                    operation = "neg";

                    viewDisplayCurr = String.format("%s(%s)", operation, viewDisplayCurr);
                    viewDisplay.setText(viewDisplayCurr);
                    calcDisplay.setText("");
                    operationFlag = true;
                    break;
                default :

                    if(equalFlag || operationFlag) {
                        viewDisplay.setText("");
                        equalFlag = false;
                        operationFlag = false;
                    }

                    String num = ((Button) v).getText().toString();
                    String formattedCalcDisplay = String.format("%s%s", calcDisplay.getText().toString(), num);
                    calcDisplay.setText(formattedCalcDisplay);
                    viewDisplayCurr += num;
                    if(viewDisplayCurr.indexOf("0") == 0) {
                        viewDisplayCurr = viewDisplayCurr.substring(1, viewDisplayCurr.length());
                    }
                    viewDisplay.setText(viewDisplayCurr);
                    break;
            }
        }
    }

}
