package lt.esdc.texthandler.interpreter.impl;

import lt.esdc.texthandler.interpreter.ExpressionInterpreter;

public class AddExpression implements ExpressionInterpreter {
    private final ExpressionInterpreter left;
    private final ExpressionInterpreter right;

    public AddExpression(ExpressionInterpreter left, ExpressionInterpreter right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public double interpret() {
        return left.interpret() + right.interpret();
    }
}
