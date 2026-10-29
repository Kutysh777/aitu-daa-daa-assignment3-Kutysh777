import java.util.Arrays;

public class Problem04 {
    public int minCostConnectPoints(int[][] points) {
        int n = points.length;
        if (n == 0) {
            return 0;
        }

        boolean[] inTree = new boolean[n];
        int[] minimumCost = new int[n];
        Arrays.fill(minimumCost, Integer.MAX_VALUE);
        minimumCost[0] = 0;
        int total = 0;

        for (int step = 0; step < n; step++) {
            int current = -1;
            for (int vertex = 0; vertex < n; vertex++) {
                if (!inTree[vertex]
                        && (current == -1 || minimumCost[vertex] < minimumCost[current])) {
                    current = vertex;
                }
            }

            inTree[current] = true;
            total += minimumCost[current];
            for (int next = 0; next < n; next++) {
                if (!inTree[next]) {
                    int distance = Math.abs(points[current][0] - points[next][0])
                            + Math.abs(points[current][1] - points[next][1]);
                    minimumCost[next] = Math.min(minimumCost[next], distance);
                }
            }
        }
        return total;
    }
}
