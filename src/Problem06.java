public class Problem06 {
    public int countDependencyGroups(int n, int[][] dependencies) {
        int[] degree = new int[n];
        for (int[] edge : dependencies) {
            degree[edge[0]]++;
        }
        int[][] graph = new int[n][];
        for (int node = 0; node < n; node++) {
            graph[node] = new int[degree[node]];
            degree[node] = 0;
        }
        for (int[] edge : dependencies) {
            graph[edge[0]][degree[edge[0]]++] = edge[1];
        }

        int[] discovery = new int[n];
        int[] low = new int[n];
        int[] nextEdge = new int[n];
        int[] dfsStack = new int[n];
        int[] componentStack = new int[n];
        boolean[] active = new boolean[n];
        int componentSize = 0;
        int time = 0;
        int groups = 0;

        for (int start = 0; start < n; start++) {
            if (discovery[start] != 0) {
                continue;
            }
            int dfsSize = 0;
            dfsStack[dfsSize++] = start;
            componentStack[componentSize++] = start;
            discovery[start] = low[start] = ++time;
            active[start] = true;

            while (dfsSize > 0) {
                int node = dfsStack[dfsSize - 1];
                if (nextEdge[node] < graph[node].length) {
                    int neighbor = graph[node][nextEdge[node]++];
                    if (discovery[neighbor] == 0) {
                        discovery[neighbor] = low[neighbor] = ++time;
                        active[neighbor] = true;
                        dfsStack[dfsSize++] = neighbor;
                        componentStack[componentSize++] = neighbor;
                    } else if (active[neighbor]) {
                        low[node] = Math.min(low[node], discovery[neighbor]);
                    }
                } else {
                    dfsSize--;
                    if (dfsSize > 0) {
                        int parent = dfsStack[dfsSize - 1];
                        low[parent] = Math.min(low[parent], low[node]);
                    }

                    if (low[node] == discovery[node]) {
                        groups++;
                        int member;
                        do {
                            member = componentStack[--componentSize];
                            active[member] = false;
                        } while (member != node);
                    }
                }
            }
        }
        return groups;
    }
}
