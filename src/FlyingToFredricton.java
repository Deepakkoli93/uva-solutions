import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class FlyingToFredricton {

    private static void solve(List<List<int[]>> adjList, Map<String, Integer> cityToInd, int n, int[] queries, int t) {
        long[][] dist = new long[n][n];
        int s = cityToInd.get("Calgary");
        int e = cityToInd.get("Fredericton");
        for(long[] row: dist) Arrays.fill(row, Integer.MAX_VALUE);
        for(int i=0; i<n; i++) dist[i][s] = 0;
        for(int i=1; i<n; i++) {
            for(int u=0; u<n; u++) {
                for(int[] p: adjList.get(u)) {
                    int v = p[0];
                    int weight = p[1];
                    if(weight + dist[i-1][u] < dist[i][v]) {
                        dist[i][v] = weight + dist[i-1][u];
                    }
                }
            }
        }
        if(t!=1) System.out.println();
        System.out.println("Scenario #" + t);

        for(int q: queries) {
            if(dist[Math.min(q+1, n-1)][e] == Integer.MAX_VALUE) {
                System.out.println("No satisfactory flights");
            } else {
                System.out.println("Total cost of flight(s) is $" + dist[Math.min(q+1, n-1)][e]);
            }
        }

    }
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine());
        for(int tt=1; tt<=t; tt++) {
            reader.readLine();
            int n = Integer.parseInt(reader.readLine());
            Map<String, Integer> cityToInd = new HashMap<>();
            List<List<int[]>> adjList = new ArrayList<>();
            for(int i=0; i<n; i++) {
                String city = reader.readLine();
                cityToInd.put(city, i);
                adjList.add(new ArrayList<>());
            }
            int m = Integer.parseInt(reader.readLine());
            for(int i=0; i<m; i++) {
                String[] arr = reader.readLine().split("\\s+");
                int x = cityToInd.get(arr[0]);
                int y = cityToInd.get(arr[1]);
                int w = Integer.parseInt(arr[2]);
                adjList.get(x).add(new int[]{y, w});
            }
            String[] queries = reader.readLine().split("\\s+");
            int numOfQueries = Integer.parseInt(queries[0]);
            int[] q = new int[numOfQueries];
            for(int i=0; i<numOfQueries; i++) {
                q[i] = Integer.parseInt(queries[i+1]);
            }
            solve(adjList, cityToInd, n, q, tt);
        }
    }
}
