package lt.esdc.texthandler.interpreter;

import lt.esdc.texthandler.interpreter.impl.*;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionParser {
    private static final Pattern EXPRESSION_PATTERN = Pattern.compile("\\(([^()]*(?:\\([^()]*\\)[^()]*)*)\\)|" + "(?<!\\w)([+-]?\\d*\\.?\\d+(?:\\s*[+\\-*/]\\s*[+-]?\\d*\\.?\\d+)+)(?!\\w)");
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("[ \\t\\x0B\\f\\r]+");
    private static final String TOKEN_SEPARATOR_REGEX = "\\s+";
    private static final String EMPTY_STRING = "";
    private static final char DECIMAL_POINT = '.';
    private static final String SPACE = " ";
    private static final char OPEN_PARENTHESIS = '(';
    private static final char CLOSE_PARENTHESIS = ')';
    private static final char PLUS = '+';
    private static final char MINUS = '-';
    private static final char MULTIPLY = '*';
    private static final char DIVIDE = '/';
    private static final char ZERO = '0';

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
        StringBuilder result = new StringBuilder();

        while (matcher.find()) {
            try {
                String expression;
                if (matcher.group(1) != null) {
                    expression = matcher.group(1).strip();
                } else {
                    expression = matcher.group(2).strip();
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
        expression = WHITESPACE_PATTERN.matcher(expression).replaceAll(EMPTY_STRING);

        String postfix = infixToPostfix(expression);

        return evaluatePostfix(postfix);
    }

    private static String infixToPostfix(String infix) {
        StringBuilder result = new StringBuilder();
        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);

            if (Character.isDigit(c) || c == DECIMAL_POINT) {
                StringBuilder number = new StringBuilder();
                while (i < infix.length() && (Character.isDigit(infix.charAt(i)) || infix.charAt(i) == DECIMAL_POINT)) {
                    number.append(infix.charAt(i));
                    i++;
                }
                i--;
                result.append(number).append(SPACE);
            } else if (c == OPEN_PARENTHESIS) {
                stack.push(c);
            } else if (c == CLOSE_PARENTHESIS) {
                while (!stack.isEmpty() && stack.peek() != OPEN_PARENTHESIS) {
                    result.append(stack.pop()).append(SPACE);
                }
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else if (isOperator(c)) {
                if ((c == PLUS || c == MINUS) && (i == 0 || infix.charAt(i - 1) == OPEN_PARENTHESIS || isOperator(infix.charAt(i - 1)))) {
                    if (c == MINUS) {
                        result.append(ZERO).append(SPACE);
                    }
                    if (c == MINUS) {
                        while (!stack.isEmpty() && stack.peek() != OPEN_PARENTHESIS && getPrecedence(stack.peek()) >= getPrecedence(c)) {
                            result.append(stack.pop()).append(SPACE);
                        }
                        stack.push(c);
                    }
                } else {
                    while (!stack.isEmpty() && stack.peek() != OPEN_PARENTHESIS && getPrecedence(stack.peek()) >= getPrecedence(c)) {
                        result.append(stack.pop()).append(SPACE);
                    }
                    stack.push(c);
                }
            }
        }

        while (!stack.isEmpty()) {
            result.append(stack.pop()).append(SPACE);
        }

        return result.toString().trim();
    }

    private static double evaluatePostfix(String postfix) {
        Deque<Double> stack = new ArrayDeque<>();
        String[] tokens = postfix.split(TOKEN_SEPARATOR_REGEX);

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
                ExpressionInterpreter expression = createExpression(leftExpr, rightExpr, token.charAt(0));

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
        return c == PLUS || c == MINUS || c == MULTIPLY || c == DIVIDE;
    }

    private static int getPrecedence(char operator) {
        return switch (operator) {
            case PLUS, MINUS -> 1;
            case MULTIPLY, DIVIDE -> 2;
            default -> 0;
        };
    }

    private static ExpressionInterpreter createExpression(ExpressionInterpreter left, ExpressionInterpreter right, char operator) {
        return switch (operator) {
            case PLUS -> new AddExpression(left, right);
            case MINUS -> new SubtractExpression(left, right);
            case MULTIPLY -> new MultiplyExpression(left, right);
            case DIVIDE -> new DivideExpression(left, right);
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
    }

    private static String formatResult(double value) {
        if (value == Math.floor(value) && !Double.isInfinite(value)) {
            return String.valueOf((long) value);
        }
        return String.valueOf(value);
    }
}