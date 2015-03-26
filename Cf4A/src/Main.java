import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.IntStream;

/**
 * Created by X on 26.03.2015.
 */
public class Main {
    public static void main(String[] args) {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int w = Integer.parseInt(br.readLine());
            if (w % 2 != 0) {
                System.out.println("NO");
            } else {
                boolean canBeSplit =
                        IntStream.iterate(2, e -> e + 2).limit(w / 2 - 1).anyMatch(e -> (w - e) % 2 == 0);
                if(canBeSplit) {
                    System.out.println("YES");
                } else {
                    System.out.println("NO");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
