public class Problem02 {
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        DisjointSet sets = new DisjointSet(n);
        int provinces = n;

        for (int city = 0; city < n; city++) {
            for (int next = city + 1; next < n; next++) {
                if (isConnected[city][next] == 1 && sets.union(city, next)) {
                    provinces--;
                }
            }
        }
        return provinces;
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
