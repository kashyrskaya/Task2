package lt.esdc.texthandler.interpreter.impl;

import lt.esdc.texthandler.interpreter.ExpressionInterpreter;

public class NumberExpression implements ExpressionInterpreter {
    private final double number;

    public NumberExpression(double number) {
        this.number = number;
    }

    @Override
    public double interpret() {
        return number;
    }
}
