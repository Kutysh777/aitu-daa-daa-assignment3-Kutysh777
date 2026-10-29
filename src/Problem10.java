public class Problem10 {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] sizes = new int[numCourses];
        int[] indegree = new int[numCourses];
        for (int[] pair : prerequisites) {
            sizes[pair[1]]++;
            indegree[pair[0]]++;
        }
        int[][] graph = new int[numCourses][];
        for (int course = 0; course < numCourses; course++) {
            graph[course] = new int[sizes[course]];
            sizes[course] = 0;
        }
        for (int[] pair : prerequisites) {
            graph[pair[1]][sizes[pair[1]]++] = pair[0];
        }

        int[] order = new int[numCourses];
        int head = 0;
        int tail = 0;
        for (int course = 0; course < numCourses; course++) {
            if (indegree[course] == 0) {
                order[tail++] = course;
            }
        }
        while (head < tail) {
            int course = order[head++];
            for (int next : graph[course]) {
                indegree[next]--;
                if (indegree[next] == 0) {
                    order[tail++] = next;
                }
            }
        }
        return tail == numCourses ? order : new int[0];
    }
}
