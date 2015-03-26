import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by X on 26.03.2015.
 */
public class Main {
    public static void main(String[] args) {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String input = br.readLine();
            String[] inpStrings = input.split("\\s");
            double n = Double.parseDouble(inpStrings[0]);
            double m = Double.parseDouble(inpStrings[1]);
            double a = Double.parseDouble(inpStrings[2]);
            long result = (long)(Math.ceil(n / a) * Math.ceil(m / a));
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
