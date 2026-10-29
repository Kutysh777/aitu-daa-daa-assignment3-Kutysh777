public class Problem07 {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[][] graph = buildGraph(numCourses, prerequisites);
        byte[] state = new byte[numCourses];
        int[] nextNeighbor = new int[numCourses];
        int[] stack = new int[numCourses];

        for (int start = 0; start < numCourses; start++) {
            if (state[start] != 0) {
                continue;
            }
            int top = 0;
            stack[top] = start;
            state[start] = 1;

            while (top >= 0) {
                int course = stack[top];
                if (nextNeighbor[course] == graph[course].length) {
                    state[course] = 2;
                    top--;
                    continue;
                }
                int next = graph[course][nextNeighbor[course]++];
                if (state[next] == 1) {
                    return false;
                }
                if (state[next] == 0) {
                    state[next] = 1;
                    stack[++top] = next;
                }
            }
        }
        return true;
    }

    private int[][] buildGraph(int numCourses, int[][] prerequisites) {
        int[] sizes = new int[numCourses];
        for (int[] pair : prerequisites) {
            sizes[pair[1]]++;
        }
        int[][] graph = new int[numCourses][];
        for (int course = 0; course < numCourses; course++) {
            graph[course] = new int[sizes[course]];
            sizes[course] = 0;
        }
        for (int[] pair : prerequisites) {
            graph[pair[1]][sizes[pair[1]]++] = pair[0];
        }
        return graph;
    }
}
