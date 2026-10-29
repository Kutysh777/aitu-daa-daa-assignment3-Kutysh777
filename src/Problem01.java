public class Problem01 {
    public int findCircleNum(int[][] isConnected) {
        boolean[] visited = new boolean[isConnected.length];
        int provinces = 0;

        for (int city = 0; city < isConnected.length; city++) {
            if (!visited[city]) {
                visit(city, isConnected, visited);
                provinces++;
            }
        }
        return provinces;
    }

    private void visit(int city, int[][] isConnected, boolean[] visited) {
        visited[city] = true;
        for (int next = 0; next < isConnected.length; next++) {
            if (isConnected[city][next] == 1 && !visited[next]) {
                visit(next, isConnected, visited);
            }
        }
    }
}
