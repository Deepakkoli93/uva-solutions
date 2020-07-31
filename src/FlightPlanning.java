import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class FlightPlanning {

    private static int bfs(int start, boolean[][] adjMat, List<List<Integer>> adjList,int n, int[] parent, boolean[] visited) {
        Arrays.fill(visited, false);
        Queue<Integer> q = new ArrayDeque<>();
        q.add(start);
        int farthest = start;
        visited[start] = true;
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v: adjList.get(u)) {
                if (adjMat[u][v] && !visited[v]) {
                    visited[v] = true;
                    q.add(v);
                    parent[v] = u;
                    farthest = v;
                }
            }
        }
        return farthest;
    }

    private static List<Integer> diameter(boolean[][] adjMat, List<List<Integer>> adjList, int start, int n, int[] parent, boolean[] visited) {

        Arrays.fill(parent, -1);
        int farthest = bfs(start, adjMat, adjList,n, parent, visited);
        Arrays.fill(parent, -1);
        farthest = bfs(farthest, adjMat, adjList,n, parent, visited);
        List<Integer> diameterPath = new ArrayList<>();
        diameterPath.add(farthest);
        while(parent[farthest] != -1) {
            diameterPath.add(parent[farthest]);
            farthest = parent[farthest];
        }
        return diameterPath;
    }

    private static void solve(boolean[][] adjMat, List<List<Integer>> adjList, int n) {
        int[] parent = new int[n];
        boolean[] visited = new boolean[n];
        List<Integer> path = diameter(adjMat, adjList,0, n, parent, visited);
//        StringBuilder str = new StringBuilder();
//        for(int p: path) str.append(p+1).append(" ");
//        System.out.println(str);

        int ans = Integer.MAX_VALUE;
        int[] dis = new int[2];
        int[] conn = new int[2];
        // try remove every edge
        for(int i=0; i<path.size()-1; i++) {
            int u = path.get(i);
            int v = path.get(i+1);
            adjMat[u][v] = false;
            adjMat[v][u] = false;

            List<Integer> diameter1 = diameter(adjMat, adjList,u, n, parent, visited);
            List<Integer> diameter2 = diameter(adjMat, adjList,v, n, parent, visited);
            int newd = Math.max((int)Math.ceil((diameter1.size()-1) / 2.0) + (int)Math.ceil((diameter2.size()-1) / 2.0) + 1,
                    Math.max(diameter1.size()-1, diameter2.size()-1));
            if (newd < ans) {
                ans = newd;
                dis[0] = u; dis[1] = v;
                conn[0] = diameter1.get(diameter1.size()/2); conn[1] = diameter2.get(diameter2.size()/2);
            }
            adjMat[u][v] = true;
            adjMat[v][u] = true;
        }
        System.out.println(ans);
        System.out.println((dis[0]+1) + " " + (dis[1]+1));
        System.out.println((conn[0]+1) + " " + (conn[1]+1));

    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine());
        while(t-- > 0) {
            int n = Integer.parseInt(reader.readLine());
            boolean[][] adjMat = new boolean[n][n];
            List<List<Integer>> adjList = new ArrayList<>();
            for(int i=0;i<n;i++) adjList.add(new ArrayList<>());
            for(int i=0; i<n-1; i++) {
                String[] arr = reader.readLine().split("\\s+");
                int u = Integer.parseInt(arr[0]) - 1;
                int v = Integer.parseInt(arr[1]) - 1;
                adjMat[u][v] = true;
                adjMat[v][u] = true;
                adjList.get(u).add(v);
                adjList.get(v).add(u);
            }
            solve(adjMat, adjList,n);
        }
    }
}
