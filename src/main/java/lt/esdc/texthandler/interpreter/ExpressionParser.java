package lt.esdc.texthandler.interpreter;

import lt.esdc.texthandler.interpreter.impl.*;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionParser {
    private static final Pattern EXPRESSION_PATTERN = Pattern.compile(
            "\\(([^()]*(?:\\([^()]*\\)[^()]*)*)\\)|" +
                    "(?<!\\w)([+-]?\\d*\\.?\\d+(?:\\s*[+\\-*/]\\s*[+-]?\\d*\\.?\\d+)+)(?!\\w)"
    );

    public static String parseAndEvaluate(String text) {
        String result = text;
        boolean hasChanges = true;

        while (hasChanges) {
            String newResult = processExpressions(result);
            hasChanges = !newResult.equals(result);
            result = newResult;
        }

        return result;
    }

    private static String processExpressions(String text) {
        Matcher matcher = EXPRESSION_PATTERN.matcher(text);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            try {
                String expression;
                if (matcher.group(1) != null) {
                    expression = matcher.group(1).trim();
                } else {
                    expression = matcher.group(2).trim();
                }

                double calculatedValue = evaluateExpression(expression);
                String replacement = formatResult(calculatedValue);
                matcher.appendReplacement(result, replacement);
            } catch (Exception e) {
                matcher.appendReplacement(result, matcher.group(0));
            }
        }
        matcher.appendTail(result);
        return result.toString();
    }

    private static double evaluateExpression(String expression) {
        expression = expression.replaceAll("[ \\t\\x0B\\f\\r]+", "");

        String postfix = infixToPostfix(expression);

        return evaluatePostfix(postfix);
    }

    private static String infixToPostfix(String infix) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                StringBuilder number = new StringBuilder();
                while (i < infix.length() && (Character.isDigit(infix.charAt(i)) || infix.charAt(i) == '.')) {
                    number.append(infix.charAt(i));
                    i++;
                }
                i--;
                result.append(number).append(" ");
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop()).append(" ");
                }
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else if (isOperator(c)) {
                if ((c == '+' || c == '-') && (i == 0 || infix.charAt(i-1) == '(' || isOperator(infix.charAt(i-1)))) {
                    if (c == '-') {
                        result.append("0 ");
                    }
                    if (c == '-') {
                        while (!stack.isEmpty() && stack.peek() != '(' && getPrecedence(stack.peek()) >= getPrecedence(c)) {
                            result.append(stack.pop()).append(" ");
                        }
                        stack.push(c);
                    }
                } else {
                    while (!stack.isEmpty() && stack.peek() != '(' && getPrecedence(stack.peek()) >= getPrecedence(c)) {
                        result.append(stack.pop()).append(" ");
                    }
                    stack.push(c);
                }
            }
        }

        while (!stack.isEmpty()) {
            result.append(stack.pop()).append(" ");
        }

        return result.toString().trim();
    }

    private static double evaluatePostfix(String postfix) {
        Stack<Double> stack = new Stack<>();
        String[] tokens = postfix.split("\\s+");

        for (String token : tokens) {
            if (token.isEmpty()) continue;

            if (isNumeric(token)) {
                stack.push(Double.parseDouble(token));
            } else if (isOperator(token.charAt(0))) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Invalid expression");
                }

                double right = stack.pop();
                double left = stack.pop();

                ExpressionInterpreter leftExpr = new NumberExpression(left);
                ExpressionInterpreter rightExpr = new NumberExpression(right);
                ExpressionInterpreter expression = createExpression(leftExpr, rightExpr, token);

                stack.push(expression.interpret());
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid expression");
        }

        return stack.pop();
    }

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static int getPrecedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
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
        if (value == Math.floor(value) && !Double.isInfinite(value)) {
            return String.valueOf((long) value);
        }
        return String.valueOf(value);
    }
}