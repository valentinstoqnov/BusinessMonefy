package org.elsys.valiolucho.calculator;

public class Minus extends BinaryOperation {

    public Minus() {
        super("-");
    }

    @Override
    public double calc(double v1, double v2) {
        return v2 - v1;
    }
}