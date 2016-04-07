package org.elsys.valiolucho.calculator;

public class Calculator {
    private double val1 = 0.0;
    private double val2 = 0.0;

    public Calculator(double val1, double val2) {
        this.val1 = val1;
        this.val2 = val2;
    }

    public Calculator(double val1) {
        this.val1 = val1;
    }

   /* public String serialize(String str, double res) {
        return str + "(" + Double.toString(res) + ")";
    }*/

    public double add() {
        return val1 + val2;
    }

    public double sub() {
        return val1 - val2;
    }

    public double mul() {
        return val1 * val2;
    }

    public double div() {
        /*if (val2 == 0) {
            return
        }*/

        return val1 / val2;
    }

    public double grad() {
        return Math.pow(val1, val2);
    }

    public double sqrt() {
        return Math.sqrt(val1);
    }

    public double neg() {
        return -val1;
    }

    public double percent() {
        return (val1 * 100/ val2);
    }
}
