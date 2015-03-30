import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Created by X on 30.03.2015.
 */
public class Main {
    public static void main(String[] args) {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String[] fstLine = br.readLine().split("\\s");
            String[] sndLine = br.readLine().split("\\s");
            int nodesNum = Integer.parseInt(fstLine[0]);
            int dstNode = Integer.parseInt(fstLine[1]);
            int[] offsets = Arrays.stream(sndLine).mapToInt(Integer::parseInt).toArray();
            int[] preGraph = IntStream.range(0, offsets.length).map(i -> i + offsets[i]).toArray();
            int[] graph = new int[nodesNum];
            System.arraycopy(preGraph, 0, graph, 0, preGraph.length);
            graph[nodesNum - 1] = -1; // -1 == null

            int curNodeIndex = 0;
            boolean[] visited = new boolean[nodesNum];
            while (curNodeIndex >= 0) {
                visited[curNodeIndex] = true;
                curNodeIndex = graph[curNodeIndex];
            }
            if(visited[dstNode - 1]) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
