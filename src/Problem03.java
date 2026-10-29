import java.util.Arrays;

public class Problem03 {
    public int minCostConnectPoints(int[][] points) {
        int n = points.length;
        if (n < 2) {
            return 0;
        }

        Edge[] edges = new Edge[n * (n - 1) / 2];
        int index = 0;
        for (int first = 0; first < n; first++) {
            for (int second = first + 1; second < n; second++) {
                int distance = Math.abs(points[first][0] - points[second][0])
                        + Math.abs(points[first][1] - points[second][1]);
                edges[index++] = new Edge(first, second, distance);
            }
        }
        Arrays.sort(edges, (first, second) -> Integer.compare(first.cost, second.cost));

        DisjointSet sets = new DisjointSet(n);
        int total = 0;
        int usedEdges = 0;
        for (Edge edge : edges) {
            if (sets.union(edge.first, edge.second)) {
                total += edge.cost;
                usedEdges++;
                if (usedEdges == n - 1) {
                    break;
                }
            }
        }
        return total;
    }

    private static class Edge {
        final int first;
        final int second;
        final int cost;

        Edge(int first, int second, int cost) {
            this.first = first;
            this.second = second;
            this.cost = cost;
        }
    }

    private static class DisjointSet {
        private final int[] parent;
        private final int[] size;

        DisjointSet(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        int find(int vertex) {
            if (parent[vertex] != vertex) {
                parent[vertex] = find(parent[vertex]);
            }
            return parent[vertex];
        }

        boolean union(int first, int second) {
            int rootFirst = find(first);
            int rootSecond = find(second);
            if (rootFirst == rootSecond) {
                return false;
            }
            if (size[rootFirst] < size[rootSecond]) {
                int temporary = rootFirst;
                rootFirst = rootSecond;
                rootSecond = temporary;
            }
            parent[rootSecond] = rootFirst;
            size[rootFirst] += size[rootSecond];
            return true;
        }
    }
}
