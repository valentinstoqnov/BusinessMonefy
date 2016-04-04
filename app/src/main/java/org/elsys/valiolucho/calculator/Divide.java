package org.elsys.valiolucho.calculator;

public class Divide extends BinaryOperation {

    public Divide() {
        super("/");
    }

    @Override
    public double calc(double v1, double v2) {
        return v2 / v1;
    }
}