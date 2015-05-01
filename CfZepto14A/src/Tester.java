import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;

/**
 * Created by X on 29.04.2015.
 */
public class Tester {
//    public static void main(String[] args) {
//        runTest(1, TEST1, EXPECTED1);
//        runTest(2, TEST2, EXPECTED2);
//        runTest(3, TEST3, EXPECTED3);
//        runTest(4, TEST4, EXPECTED4);
//    }

    private static final String TEST1 =
                    "5 3\n" +
                    "0 2 4\n" +
                    "1 3 1\n" +
                    "0 8 3\n" +
                    "0 20 10\n" +
                    "1 5 5";
    private static final String EXPECTED1 = "4";

    private static final String TEST2 =
                    "2 5\n" +
                    "1 4 0\n" +
                    "1 4 0";
    private static final String EXPECTED2 = "1";

    private static final String TEST3 =
                    "0 5";
    private static final String EXPECTED3 = "0";

    private static final String TEST4 =
                    "5 10\n" +
                    "0 5 4\n" +
                    "1 5 4\n" +
                    "0 5 4\n" +
                    "1 5 4\n" +
                    "0 5 4";
    private static final String EXPECTED4 = "5";

    private static void runTest(int testNum, String input, String expected) {
        final String result = runProgram(input);
        assert result.equals(expected) : "Test" + testNum + ", expected: " + expected + ", actual: " + result;
    }

    private static String runProgram(String testInput) {
        System.setIn(new ByteArrayInputStream(testInput.getBytes()));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        Main.solve();
        return baos.toString();
    }
}
