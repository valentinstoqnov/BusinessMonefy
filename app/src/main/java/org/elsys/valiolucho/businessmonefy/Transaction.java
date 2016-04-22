package org.elsys.valiolucho.businessmonefy;

import java.util.Calendar;

public class Transaction {

    private static final int MULTIPLIER = 10;
    int id;
    String name;
    String description;
    int money;
    Calendar date;
    //String category;

    private int serializeMoney(double mny) {
        return (int)(mny * MULTIPLIER * getCountOfNumsAfterFloationgPoint(mny));
    }

    private int getCountOfNumsAfterFloationgPoint(double mny) {
        String strMoney = String.valueOf(mny);
        strMoney = strMoney.substring(strMoney.indexOf('.'), strMoney.length());
        return strMoney.length();
    }

    public Transaction(String name, String description, int money, Calendar date) {
        this.name = name;
        this.description = description;
        this.money = serializeMoney(money);
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getMoney() {
        return money;
    }

    public Calendar getDate() {
        return date;
    }
}
