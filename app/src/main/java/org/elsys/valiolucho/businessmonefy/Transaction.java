package org.elsys.valiolucho.businessmonefy;

import java.math.BigDecimal;

public class Transaction {

    private static final BigDecimal TO_LOWEST_UNIT = new BigDecimal(100).setScale(2, BigDecimal.ROUND_HALF_EVEN);;
    private String name;
    private String description;
    private BigDecimal money;
    private String date;



    public Transaction(String name, String description, BigDecimal money) {
        this.name = name;
        this.description = description;
        this.money = money.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public static BigDecimal getMoneyAsInt(BigDecimal money){
        return (money.multiply(TO_LOWEST_UNIT)).setScale(2, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros();
    }

    public static BigDecimal getMoneyWithDecPoint(int mny) {
        BigDecimal money = new BigDecimal(mny).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return (money.divide(TO_LOWEST_UNIT)).setScale(2, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros();
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

    public BigDecimal getMoney() {
        return money.stripTrailingZeros();
    }

    public String getDate() {
        return date;
    }
}
