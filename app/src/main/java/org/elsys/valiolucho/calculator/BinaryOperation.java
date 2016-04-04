package org.elsys.valiolucho.calculator;

public abstract class BinaryOperation extends Operation {

    public BinaryOperation(String name) {
        super(name);
    }

    @Override
    public void eval() {
        if (getContext().size() < 2) {
            throw new IllegalStateException("Not enough arguements");
        }
        double v1 = getContext().pop();
        double v2 = getContext().pop();
        double res = calc(v1, v2);
        System.out.println("R: " + res);
        getContext().push(res);
    }

    public abstract double calc(double v1, double v2);
}