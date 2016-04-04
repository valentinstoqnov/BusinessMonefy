package org.elsys.valiolucho.calculator;

public class Plus extends BinaryOperation {

    public Plus() {
        super("+");
    }

    @Override
    public double calc(double v1, double v2) {
        return v1 + v2;
    }
}
