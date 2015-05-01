import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        solve();
    }

    public static void solve() {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String[] line = br.readLine().split("\\s");
            int n = Integer.parseInt(line[0]);
            int x = Integer.parseInt(line[1]);

            List<Candy> candies0 = new ArrayList<>();
            List<Candy> candies1 = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                line = br.readLine().split("\\s");
                int t = Integer.parseInt(line[0]);
                int h = Integer.parseInt(line[1]);
                int w = Integer.parseInt(line[2]);
                if(t == 0) {
                    candies0.add(new Candy(t, h, w));
                } else {
                    candies1.add(new Candy(t, h, w));
                }
            }
            Comparator<? super Candy> comparator = (o1, o2) -> {
                if(o1.height >= o2.height) {
                    if (o1.height > o2.height) {
                        return 1;
                    }
                    return 0;
                }
                return -1;
            };
            // Eat greedy beginning with 0-type candy
            Node root0 = buildRmqTree(candies0, comparator);
            Node root1 = buildRmqTree(candies1, comparator);
            int eaten01 = eatGreedy(x, root0, root1);

            // Eat greedy beginning with 1-type candy
            root0 = buildRmqTree(candies0, comparator);
            root1 = buildRmqTree(candies1, comparator);
            int eaten10 = eatGreedy(x, root1, root0);

            System.out.print(eaten01 > eaten10 ? eaten01 : eaten10);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Node buildRmqTree(List<Candy> candies,  Comparator<? super Candy> comparator) {
        if(candies.size() == 0) {
            return null;
        }
        List<Node> initialNodes = candies.stream()
                .sorted(comparator)
                .map(c -> new Node(c, null, null, c.height, c.height))
                .collect(Collectors.toList());
        Queue<Node> queue = new LinkedList<>(initialNodes);
        while(queue.size() != 1) {
            Node fst = queue.poll();
            Node snd = queue.poll();
            Candy maxWeightCandy = fst.candy.weight > snd.candy.weight ? fst.candy : snd.candy;
            Node merged = new Node(maxWeightCandy, fst, snd, fst.heightBeg, snd.heightEnd);
            queue.add(merged);
            if(queue.size() == 2 && merged.heightBeg < queue.element().heightEnd) {
                fst = queue.poll();
                queue.add(fst);
            }
        }
        return queue.remove();
    }

    private static Node buildRmqTree2(List<Candy> candies,  Comparator<? super Candy> comparator) {
        if(candies.size() == 0) {
            return null;
        }
        List<Node> initialNodes = candies.stream()
                .sorted(comparator)
                .map(c -> new Node(c, null, null, c.height, c.height))
                .collect(Collectors.toList());
        double log2OfLength = Math.log(initialNodes.size()) * 1.0 / Math.log(2);
        int nearestPow2 = (int)Math.pow(2, Math.ceil(log2OfLength));
        int dummyNodesNum = nearestPow2 - initialNodes.size();
        Supplier<Node> makeNull = () -> null;
        List<Node> dummies = Stream.generate(makeNull).limit(dummyNodesNum).collect(Collectors.toList());
        initialNodes.addAll(dummies);

        Queue<Node> queue = new LinkedList<>(initialNodes);
        while(queue.size() != 1) {
            Node fst = queue.poll();
            Node snd = queue.poll();
            if(fst == null && snd == null) {
                queue.add(null);
            } else if(fst == null) {
                queue.add(snd);
            } else if(snd == null) {
                queue.add(fst);
            } else {
                Candy maxWeightCandy = fst.candy.weight > snd.candy.weight ? fst.candy : snd.candy;
                Node merged = new Node(maxWeightCandy, fst, snd, fst.heightBeg, snd.heightEnd);
                queue.add(merged);
            }
        }
        return queue.remove();
    }

    private static Candy maxWeightCandyQuery (int maxHeight, Node root) {
        if(root == null) {
            return null;
        }
        if (root.isLeaf()) {
            if (maxHeight < root.heightBeg) {
                return null;
            }
            Candy maxWeightCandy = root.candy;
            root.candy = null;
            return maxWeightCandy;
        } else {
            return traverse(maxHeight, root);
        }
    }

    private static Candy traverse(int maxHeight, Node node) {
        if(node.isLeaf()) {
            return node.candy;
        }
        Candy maxWeightCandy = null;
        if (node.right.heightBeg <= maxHeight) {
            Candy rightMaxCandy = traverse(maxHeight, node.right);
            Candy leftMaxCandy = node.candy;
            maxWeightCandy = leftMaxCandy.weight > rightMaxCandy.weight ? leftMaxCandy : rightMaxCandy;
        } else if (node.left.heightBeg <= maxHeight) {
            maxWeightCandy = traverse(maxHeight, node.left);
        }
        if(node.left.isLeaf() && node.left.candy == maxWeightCandy) {
            node.left = null;
        } else if (node.right.isLeaf() && node.right.candy == maxWeightCandy) {
            node.right = null;
        }
        node.recountParameters();
        return maxWeightCandy;
    }

    private static int eatGreedy(int initialHeight, Node root0, Node root1) {
        int curHeight = initialHeight;
        Queue<Node> roots = new LinkedList<>();
        roots.add(root0);
        roots.add(root1);
        int eaten = 0;
        while(true) {
            Node curRoot = roots.remove();
            Candy curCandy = maxWeightCandyQuery(curHeight, curRoot);
            if(curCandy == null) {
                break;
            }
            eaten += 1;
            curHeight += curCandy.weight;
            roots.add(curRoot);
        }
        return eaten;
    }

    private static class Node {
        public Candy candy;

        public Node left;
        public Node right;

        public int heightBeg;
        public int heightEnd;

        private Node(Candy candy, Node left, Node right, int heightBeg, int heightEnd) {
            this.candy = candy;
            this.left = left;
            this.right = right;
            this.heightBeg = heightBeg;
            this.heightEnd = heightEnd;
        }

        public boolean isLeaf() { return left == null && right == null; }

        public void recountParameters() {
            if(!isLeaf()) {
                if (left == null) {
                    candy = right.candy;
                    heightBeg = right.heightBeg;
                    heightEnd = right.heightEnd;
                    left = right.left;
                    right = right.right;
                } else if (right == null) {
                    candy = left.candy;
                    heightBeg = left.heightBeg;
                    heightEnd = left.heightEnd;
                    right = left.right;
                    left = left.left;
                } else {
                    candy = left.candy.weight > right.candy.weight ? left.candy : right.candy;
                    heightBeg = left.heightBeg;
                    heightEnd = right.heightEnd;
                }
            }
        }
    }

    private static class Candy {
        public int type;
        public int height;
        public int weight;

        public Candy(int type, int height, int weight) {
            this.type = type;
            this.height = height;
            this.weight = weight;
        }
    }
}
