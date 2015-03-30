import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created by X on 30.03.2015.
 */
public class Main {
    public static void main(String[] args) {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int drinks = Integer.parseInt(br.readLine());
            double numerator = Arrays.stream(br.readLine().split("\\s")).mapToInt(Integer::parseInt).sum();
            int denomimator = drinks;
            System.out.println(numerator / denomimator);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
