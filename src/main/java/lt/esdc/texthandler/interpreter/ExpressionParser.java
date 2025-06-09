package main.java.lt.esdc.texthandler.interpreter;

import main.java.lt.esdc.texthandler.interpreter.impl.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionParser {
    private static final Pattern EXPRESSION_PATTERN = Pattern.compile("([0-9]*\\.?[0-9]+)\\s*([+\\-*/])\\s*([0-9]*\\.?[0-9]+)");

    public static String parseAndEvaluate(String text) {
        Matcher matcher = EXPRESSION_PATTERN.matcher(text);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            try {
                double leftValue = Double.parseDouble(matcher.group(1));
                String operator = matcher.group(2);
                double rightValue = Double.parseDouble(matcher.group(3));

                ExpressionInterpreter leftExpression = new NumberExpression(leftValue);
                ExpressionInterpreter rightExpression = new NumberExpression(rightValue);
                ExpressionInterpreter expression = createExpression(leftExpression, rightExpression, operator);

                double calculatedValue = expression.interpret();
                String replacement = formatResult(calculatedValue);
                matcher.appendReplacement(result, replacement);
            } catch (Exception e) {
                matcher.appendReplacement(result, matcher.group(0));
            }
        }
        matcher.appendTail(result);
        return result.toString();
    }

    private static ExpressionInterpreter createExpression(ExpressionInterpreter left, ExpressionInterpreter right, String operator) {
        switch (operator) {
            case "+":
                return new AddExpression(left, right);
            case "-":
                return new SubtractExpression(left, right);
            case "*":
                return new MultiplyExpression(left, right);
            case "/":
                return new DivideExpression(left, right);
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }

    private static String formatResult(double value) {
        if (value == Math.floor(value)) {
            return String.valueOf((int) value);
        }
        return String.valueOf(value);
    }
}
