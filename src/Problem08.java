public class Problem08 {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
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

        int[] queue = new int[numCourses];
        int head = 0;
        int tail = 0;
        for (int course = 0; course < numCourses; course++) {
            if (indegree[course] == 0) {
                queue[tail++] = course;
            }
        }
        while (head < tail) {
            int course = queue[head++];
            for (int next : graph[course]) {
                indegree[next]--;
                if (indegree[next] == 0) {
                    queue[tail++] = next;
                }
            }
        }
        return head == numCourses;
    }
}
