package org.elsys.valiolucho.calculator;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import org.elsys.valiolucho.businessmonefy.R;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalcActivity extends AppCompatActivity {
    EditText calcDisplay;
    TextView viewDisplay;
    private Button zero, one, two, three, four, five, six, seven, eight, nine;
    private Button mul, div, add, sub, sqrt, point, neg, percent, equal, delete, backspace, gradiation;
    private Button cancel, ok;
    private ButtonClickListener buttonClick = new ButtonClickListener();
    protected double numBuff;
    protected String operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        calcDisplay = (EditText) findViewById(R.id.calcDisplay);
        viewDisplay = (TextView) findViewById(R.id.textView);

        int idList[] = {R.id.buttonZero,R.id.buttonOne,R.id.buttonTwo,R.id.buttonThree,R.id.buttonFour,R.id.buttonFive,
                R.id.buttonSix,R.id.buttonSeven,R.id.buttonEight,R.id.buttonNine,R.id.buttonPlus,R.id.buttonMinus,
                R.id.buttonMultiply,R.id.buttonDivide,R.id.buttonGradiation,
                R.id.buttonNegative,R.id.buttonPoint, R.id.buttonBackspace, R.id.buttonEqual,
                R.id.buttonDelete,R.id.buttonOk,R.id.buttonCancel};

        for (int id: idList) {
            View v = (View) findViewById(id);
            v.setOnClickListener(buttonClick);
        }
    }

    private double calculate(double val) {
        Calculator calculator = new Calculator(numBuff, val);
        double result = 0.0;
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
            case "%" :
                result = calculator.percent();
                break;
            case "^" :
                result = calculator.grad();
                break;
        }
        return result;
    }

    private double calculate() {
        Calculator calculator = new Calculator(numBuff);
        double result = 0.0;
        /*switch (operation) {
            case "sqrt" :
                result = calculator.sqrt();
                break;
            case "neg" :*/
                result = calculator.neg();
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
                    numBuff = 0;
                    operation = "";
                    operationFlag = false;
                    equalFlag = false;
                    break;
                case R.id.buttonBackspace :
                    String displayStr = calcDisplay.getText().toString();
                    if(displayStr.length() < 1) {
                        break;
                    }
                    displayStr = displayStr.substring(0, displayStr.length()-1);
                    calcDisplay.setText(displayStr);

                    displayStr = viewDisplay.getText().toString();
                    displayStr = displayStr.substring(0, displayStr.length() - 1);
                    viewDisplay.setText(displayStr);
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
                        numBuff = Double.parseDouble(calcDisplay.getText().toString());
                    }catch (NumberFormatException nfe) {
                        Pattern pattern = Pattern.compile("\\d+|-\\d+");
                        Matcher matcher = pattern.matcher(viewDisplayCurr);
                        if(matcher.matches()) {
                            numBuff = Double.parseDouble(viewDisplayCurr);
                        }else{
                            numBuff = 0;
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
                        numBuff = Double.parseDouble(calcDisplay.getText().toString());
                    }catch (NumberFormatException nfe) {
                        Pattern pattern = Pattern.compile("\\d+|-\\d+");
                        Matcher matcher = pattern.matcher(viewDisplayCurr);
                        if(matcher.matches()) {
                            numBuff = Double.parseDouble(viewDisplayCurr);
                        }else{
                            numBuff = 0;
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
                        numBuff = Double.parseDouble(calcDisplay.getText().toString());
                    }catch (NumberFormatException nfe) {
                        Pattern pattern = Pattern.compile("\\d+");
                        Matcher matcher = pattern.matcher(viewDisplayCurr);
                        if(matcher.matches()) {
                            numBuff = Double.parseDouble(viewDisplayCurr);
                        }else{
                            numBuff = 0;
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
                        numBuff = Double.parseDouble(calcDisplay.getText().toString());
                    }catch (NumberFormatException nfe) {
                        Pattern pattern = Pattern.compile("\\d+|-\\d+");
                        Matcher matcher = pattern.matcher(viewDisplayCurr);
                        if(matcher.matches()) {
                            numBuff = Double.parseDouble(viewDisplayCurr);
                        }else{
                            numBuff = 0;
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
                    double secondBuff = 0.0;
                    double result = 0.0;
                    if(operation.equals("neg")) {
                        result = calculate();
                    }else{
                        try {
                            secondBuff = Double.parseDouble(calcDisplay.getText().toString());
                            result = calculate(secondBuff);
                        } catch (NumberFormatException nfe) {
                            String msg = String.format("Can not <%s>, <%s> with <%s>", operation, Double.toString(numBuff), calcDisplay.getText().toString());
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
                        numBuff = Double.parseDouble(calcDisplay.getText().toString());
                    }catch (NumberFormatException nfe) {
                        Pattern pattern = Pattern.compile("\\d+|-\\d+");
                        Matcher matcher = pattern.matcher(viewDisplayCurr);
                        if(matcher.matches()) {
                            numBuff = Double.parseDouble(viewDisplayCurr);
                        }else{
                            numBuff = 0;
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
                        numBuff = Double.parseDouble(calcDisplay.getText().toString());
                    }catch (NumberFormatException nfe) {
                        Pattern pattern = Pattern.compile("\\d+|-\\d+");
                        Matcher matcher = pattern.matcher(viewDisplayCurr);
                        if(matcher.matches()) {
                            numBuff = Double.parseDouble(viewDisplayCurr);
                        }else{
                            numBuff = 0;
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
