package org.elsys.valiolucho.calculator;

import java.math.BigDecimal;

public class Calculator {
    private BigDecimal val1 = new BigDecimal(0.0);
    private BigDecimal val2 = new BigDecimal(0.0);

    public Calculator(BigDecimal val1, BigDecimal val2) {
        this.val1 = val1.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        this.val2 = val2.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public Calculator(BigDecimal val1) {
        this.val1 = val1;
    }

   /* public String serialize(String str, BigDecimal res) {
        return str + "(" + BigDecimal.toString(res) + ")";
    }*/

    public BigDecimal add() {
        return val1.add(val2);
    }

    public BigDecimal sub() {
        return val1.subtract(val2);
    }

    public BigDecimal mul() {
        return val1.multiply(val2);
    }

    public BigDecimal div() {
        return val1.divide(val2, 2);
    }

    public BigDecimal grad() {
        return val1.pow(val2.intValue());
    }

    /*public BigDecimal sqrt() {
        return Math.sqrt();
    }*/

    public BigDecimal neg() {
        return val1.negate();
    }

    /*public BigDecimal percent() {
        return (val1 * 100/ val2);
    }*/
}
