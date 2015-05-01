import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by X on 30.04.2015.
 */
public class Main {
    public static void sovled(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String sizeStr = br.readLine();
            int n = Integer.parseInt(sizeStr);

            List<String> from = Arrays.asList(br.readLine().split(""));
            List<String> to = Arrays.asList(br.readLine().split(""));

            long rots = 0;
            for (int i = 0; i < n; i++) {
                int fromNum = Integer.parseInt(from.get(i));
                int toNum = Integer.parseInt(to.get(i));
                int try1 = Math.abs(fromNum - toNum);
                int try2 = 0;
                if (fromNum > toNum) {
                    try2 = Math.abs(fromNum - (toNum + 10));
                } else {
                    try2 = Math.abs(toNum - (fromNum + 10));
                }
                rots += Math.min(try1, try2);
            }
            System.out.println(rots);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
