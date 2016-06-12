package org.elsys.valiolucho.businessmonefy;

import java.math.BigDecimal;
import java.util.ArrayList;

public class DataProcess {

    private ArrayList<Transaction> data;
    private String period;
    private float[] values;
    private String[] labels;

    public DataProcess(ArrayList<Transaction> data, String period) {
        this.data = data;
        this.period = period;
        setValues();
        setLabels();
    }

    public DataProcess(ArrayList<Transaction> data) {
        this.data = data;
    }

    public BigDecimal getIncomings() {
        BigDecimal incomingSum = BigDecimal.ZERO;
        for (Transaction transaction : data) {
            BigDecimal money = transaction.getMoney();
            if(money.compareTo(BigDecimal.ZERO) == 1) {
                incomingSum = incomingSum.add(money);
            }
        }
        return incomingSum.setScale(2, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros();
    }

    public BigDecimal getOutcomings() {
        BigDecimal outcomingSum = BigDecimal.ZERO;
        for (Transaction transaction : data) {
            BigDecimal money = transaction.getMoney();
            if(money.compareTo(BigDecimal.ZERO) == -1) {
                outcomingSum = outcomingSum.add(money);
            }
        }
        return outcomingSum.setScale(2, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros();
    }

    public float[] getMinusVals() {
        ArrayList<BigDecimal> nums = new ArrayList<>();
        int index = 0;
        for (Transaction transaction : data) {
            transaction = data.get(index);
            if(transaction.getMoney().compareTo(BigDecimal.ZERO) == -1) {
                nums.add(transaction.getMoney());
                labels[index] = transaction.getName();
            }
            index++;
        }
        float[] floatNums = new float[nums.size()];
        for (int i = 0; i < nums.size(); i++) {
            floatNums[i] = nums.get(i).floatValue();
        }
        return floatNums;
    }

    private void setValues() {
        this.values = new float[data.size()];
        for (int i = 0; i < data.size(); i++) {
            Transaction transaction = data.get(i);
            values[i] = transaction.getMoney().floatValue();
        }
    }

    private void setLabels() {
        this.labels = new String[data.size()];

        if("today".equals(period) || "yesterday".equals(period)) {
            labelTimeGetter();
        }else {
            for(int i = 0; i < data.size(); i++) {
                labels[i] = data.get(i).getDate();
            }
        }
    }

    private void labelTimeGetter() {
        for (int i = 0; i < data.size(); i++) {
            Transaction transaction = data.get(i);
            labels[i] = transaction.getDate().substring(transaction.getDate().lastIndexOf(' '));
        }
    }

    public float[] getValues(){
        return values;
    }

    public String[] getLabels() {
        return labels;
    }

    public String getPeriod() {
        return period;
    }
}
