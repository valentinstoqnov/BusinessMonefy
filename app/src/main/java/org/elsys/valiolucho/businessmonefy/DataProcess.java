package org.elsys.valiolucho.businessmonefy;

import java.util.ArrayList;

public class DataProcess {

    private ArrayList<Transaction> data;
    private String period;
    private ArrayList<Double> values;
    private ArrayList<String> labels;

    public DataProcess(ArrayList<Transaction> data, String period) {
        this.data = data;
        this.period = period;
        setValues();
        setLabels();
    }

    private void setValues() {
        for(Transaction transaction : data) {
            values.add(Transaction.getDeserializedMoney(transaction.getSerializedMoney()));//money
        }
    }

    private void setLabels() {
        for(Transaction transaction : data) {
            if (period.equals("today")) {
                labels.add(transaction.getDate().substring(transaction.getDate().lastIndexOf(' ')));
            }
        }
    }

    public ArrayList<Double> getValues(){
        return values;
    }

    public ArrayList<String> getLabels() {
        return labels;
    }

    public String getPeriod() {
        return period;
    }
}
