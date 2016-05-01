package org.elsys.valiolucho.businessmonefy;

public class Transaction {

    private static final int MULTIPLIER = 10;
    String name;
    String description;
    int money;
    String date;
    private int transactionImgRes;
    //String category;

    private int serializeMoney(double mny) {
        return (int)(mny * MULTIPLIER * getCountOfNumsAfterFloationgPoint(mny));
    }

    private int getCountOfNumsAfterFloationgPoint(double mny) {
        String strMoney = String.valueOf(mny);
        strMoney = strMoney.substring(strMoney.indexOf('.'), strMoney.length());
        return strMoney.length();
    }

    public Transaction(String name, String description, int money) {
        this.name = name;
        this.description = description;
        this.money = serializeMoney(money);
    }

    public void setTransactionImgRes(int transactionImgRes) {
        this.transactionImgRes = transactionImgRes;
    }


    public void setDate() {
        this.date = new MyDate().getCurrentDateTime();
    }
    public void setDate(String date) {
        this.date = date;
    }

    public int getTransactionResource() {
        return transactionImgRes;
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

    public String getDate() {
        return date;
    }
}
