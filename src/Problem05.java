import java.util.Arrays;

public class Problem05 {
    public int countDependencyGroups(int n, int[][] dependencies) {
        int[] outDegree = new int[n];
        int[] inDegree = new int[n];
        for (int[] edge : dependencies) {
            outDegree[edge[0]]++;
            inDegree[edge[1]]++;
        }

        int[][] graph = new int[n][];
        int[][] reversed = new int[n][];
        for (int node = 0; node < n; node++) {
            graph[node] = new int[outDegree[node]];
            reversed[node] = new int[inDegree[node]];
            outDegree[node] = 0;
            inDegree[node] = 0;
        }
        for (int[] edge : dependencies) {
            graph[edge[0]][outDegree[edge[0]]++] = edge[1];
            reversed[edge[1]][inDegree[edge[1]]++] = edge[0];
        }

        boolean[] visited = new boolean[n];
        int[] nextEdge = new int[n];
        int[] stack = new int[n];
        int[] order = new int[n];
        int orderSize = 0;

        for (int start = 0; start < n; start++) {
            if (visited[start]) {
                continue;
            }
            int stackSize = 0;
            stack[stackSize++] = start;
            visited[start] = true;

            while (stackSize > 0) {
                int node = stack[stackSize - 1];
                if (nextEdge[node] == graph[node].length) {
                    order[orderSize++] = node;
                    stackSize--;
                } else {
                    int neighbor = graph[node][nextEdge[node]++];
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        stack[stackSize++] = neighbor;
                    }
                }
            }
        }

        Arrays.fill(visited, false);
        int groups = 0;
        for (int i = orderSize - 1; i >= 0; i--) {
            int start = order[i];
            if (visited[start]) {
                continue;
            }
            groups++;
            int stackSize = 0;
            stack[stackSize++] = start;
            visited[start] = true;

            while (stackSize > 0) {
                int node = stack[--stackSize];
                for (int neighbor : reversed[node]) {
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        stack[stackSize++] = neighbor;
                    }
                }
            }
        }
        return groups;
    }
}
