package lt.esdc.texthandler.interpreter;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import static org.testng.Assert.*;

public class ExpressionParserTest {

    @DataProvider(name = "expressions")
    public Object[][] expressionProvider() {
        return new Object[][] {
                {"2+3", "5"},
                {"10-5", "5"},
                {"4*3", "12"},
                {"15/3", "5"},
                {"(2+3)*4", "20"},
                {"2+(3*4)-5", "9"},
                {"3.5+2.5", "6"},
                {"((2+3)*4)-5", "15"},
                {"2+-3", "-1"},
                {"(2+-3)*4", "-4"},
                {"10 + 5 - 3", "12"},
                {"-5+8", "3"},
                {"(1+2)*(3+4)", "21"},
                {"(10/(5-3)) + 7", "12"}
        };
    }

    @Test(dataProvider = "expressions")
    public void testParseAndEvaluate_ExpressionsOnly(String input, String expected) {
        String result = ExpressionParser.parseAndEvaluate(input);
        assertEquals(result, expected);
    }
}
