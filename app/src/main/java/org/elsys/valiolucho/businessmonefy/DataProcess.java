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
        }else if("week".equals(period)) {
            //days
            for(int i = 0; i < data.size(); i++) {
                Transaction transaction = data.get(i);
                String date = transaction.getDate();
                int spaceIndex = date.lastIndexOf(' ');
                labels[i] = date.substring(spaceIndex - 2, spaceIndex);
            }
        }else if("month".equals(period)) {
            //weeks
        }else if("year".equals(period)) {
            //months
        }else {
            //date 2016-02-10
        }
    }

    private void labelTimeGetter() {
        for (int i = 0; i < data.size(); i++) {
            Transaction transaction = data.get(i);
            labels[i] = transaction.getDate().substring(transaction.getDate().lastIndexOf(' '));
        }
    }

    private void labelDateGetter() {
        for (int i = 0; i < data.size(); i++) {
            Transaction transaction = data.get(i);
            labels[i] = transaction.getDate().substring(0, transaction.getDate().lastIndexOf(' '));
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
