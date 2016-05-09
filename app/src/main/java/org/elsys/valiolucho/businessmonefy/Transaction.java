package org.elsys.valiolucho.businessmonefy;

public class Transaction {

    private static final int MULTIPLIER = 100;
    private String name;
    private String description;
    private double money;
    private String date;
    //String category;

    public int getSerializedMoney() {
        return (int)(money * MULTIPLIER);
    }

    public static double getDeserializedMoney(int mny) {
        return mny / MULTIPLIER;
    }


    public Transaction(String name, String description, double money) {
        this.name = name;
        this.description = description;
        this.money = money;
    }


    public void setDate() {
        this.date = new MyDate().getCurrentDateTime();
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getMoney() {
        return money;
    }

    public String getDate() {
        return date;
    }
}
