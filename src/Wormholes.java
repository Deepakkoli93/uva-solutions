import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Wormholes {

    private static void solve(int n, List<List<long[]>> adjList) {
        long[] dist = new long[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[0] = 0;
        for(int i=0; i<n-1; i++) {
            for(int u=0; u<n; u++) {
                for(long[] v: adjList.get(u)) {
                    dist[(int)v[0]] = Math.min(dist[(int)v[0]], dist[u] + v[1]);
                }
            }
        }
        boolean negCycle = false;
        for(int u=0; u<n; u++) {
            for(long[] v: adjList.get(u)) {
                if (dist[(int)v[0]] > dist[u] + v[1]) {
                    negCycle = true;
                    break;
                }
            }
        }
        if(negCycle) {
            System.out.println("possible");
        } else {
            System.out.println("not possible");
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int c = Integer.parseInt(reader.readLine());
        while(c-- > 0) {
            String[] arr = reader.readLine().split("\\s+");
            int n = Integer.parseInt(arr[0]);
            int m = Integer.parseInt(arr[1]);
            List<List<long[]>> adjList = new ArrayList<>();
            for(int i=0; i<n; i++) adjList.add(new ArrayList<>());
            for(int i=0; i<m; i++) {
                arr = reader.readLine().split("\\s+");
                int x = Integer.parseInt(arr[0]);
                int y = Integer.parseInt(arr[1]);
                long w = Long.parseLong(arr[2]);
                adjList.get(x).add(new long[]{y, w});
            }
            solve(n, adjList);
        }
    }
}
