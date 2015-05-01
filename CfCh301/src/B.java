import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by X on 30.04.2015.
 */
public class B {
    private static class Mark {
        int val = 0;
        boolean old = true;

        public Mark(int val, boolean old) {
            this.val = val;
            this.old = old;
        }
    }
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String[] input = br.readLine().split("\\s");
            int n = Integer.parseInt(input[0]);
            int k = Integer.parseInt(input[1]);
            int p = Integer.parseInt(input[2]);
            int x = Integer.parseInt(input[3]);
            int y = Integer.parseInt(input[4]);
            List<Integer> marksSoFar;
            if(k != 0) {
                marksSoFar = Arrays.stream(br.readLine().split("\\s"))
                        .map(Integer::parseInt).collect(Collectors.toList());
            } else {
                marksSoFar = new ArrayList<>();
            }
            int medianPos = n / 2;
            int medianNum = medianPos + 1;
            marksSoFar.sort((a, b) -> a > b ? -1 : a.equals(b) ? 0 : 1);
            List<Integer> addedMarks = new LinkedList<>();

            int fstBigEnoughPos = 0;
            while (fstBigEnoughPos < marksSoFar.size() && marksSoFar.get(fstBigEnoughPos) >= y) {
                fstBigEnoughPos += 1;
            }
            fstBigEnoughPos -= 1;
            int elemsToMove = medianPos - fstBigEnoughPos;
            while (marksSoFar.size() < n && elemsToMove > 0) {
                marksSoFar.add(y);
                addedMarks.add(y);
                --elemsToMove;
            }
            while (marksSoFar.size() < n) {
                marksSoFar.add(1);
                addedMarks.add(1);
            }

            marksSoFar.sort((a, b) -> a > b ? -1 : a.equals(b) ? 0 : 1);
            int sum = 0;
            for (Integer m : marksSoFar) {
                sum += m;
            }
            if(sum > x || marksSoFar.get(medianPos) < y) {
                System.out.println("-1");
                return;
            }
            for (int i = 0; i < addedMarks.size() - 1; ++i) {
                System.out.print(addedMarks.get(i) + " ");
            }
            System.out.println(addedMarks.get(addedMarks.size() - 1));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
