import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class FullTank {

    private static int solve(List<List<int[]>> adjList, int[] prices, int n, int s, int e, int c) {
        // weight, city, fuel
        int[][] dist = new int[n][c+1];
        for(int[] row: dist) Arrays.fill(row, Integer.MAX_VALUE);
        dist[s][0] = 0;
        Queue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
        q.add(new int[]{0, s, 0});
        while(!q.isEmpty()) {
            int[] front = q.poll();
            if (front[1] == e) break;
            if(front[0] == dist[front[1]][front[2]]) {
                // fill fuel
                if (front[2] < c) {
                    int weight = prices[front[1]];
                    if (front[0] + weight < dist[front[1]][front[2] + 1]) {
                        dist[front[1]][front[2] + 1] = front[0] + weight;
                        q.add(new int[]{dist[front[1]][front[2] + 1], front[1], front[2] + 1});
                    }
                }

                // move to other city
                for(int[] cw: adjList.get(front[1])) {
                    int weight = 0;
                    if (front[2] >= cw[1]) {
                        if (front[0] + weight < dist[cw[0]][front[2] - cw[1]]) {
                            dist[cw[0]][front[2] - cw[1]] = front[0] + weight;
                            q.add(new int[]{dist[cw[0]][front[2] - cw[1]], cw[0], front[2] - cw[1]});
                        }
                    }
                }
            }
        }
        int ans = Integer.MAX_VALUE;
        for(int i: dist[e]) {
            ans = Math.min(ans, i);
        }
        return ans;

    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] arr = reader.readLine().split("\\s+");

        int n = Integer.parseInt(arr[0]);
        int m = Integer.parseInt(arr[1]);
        int[] prices = new int[n];
        arr = reader.readLine().split("\\s+");
        for(int i=0; i<n;i++) {
            prices[i] = Integer.parseInt(arr[i]);
        }
        List<List<int[]>> adjList = new ArrayList<>();
        for(int i=0; i<n; i++) adjList.add(new ArrayList<>());
        for(int i=0; i<m; i++) {
            arr = reader.readLine().split("\\s+");
            int x = Integer.parseInt(arr[0]);
            int y = Integer.parseInt(arr[1]);
            int w = Integer.parseInt(arr[2]);
            adjList.get(x).add(new int[]{y, w});
            adjList.get(y).add(new int[]{x, w});
        }
        int q = Integer.parseInt(reader.readLine());
        for(int i=0; i<q; i++) {
            arr = reader.readLine().split("\\s+");
            int c = Integer.parseInt(arr[0]);
            int s = Integer.parseInt(arr[1]);
            int e = Integer.parseInt(arr[2]);

            int ans = solve(adjList, prices, n, s, e, c);
            if (ans == Integer.MAX_VALUE) {
                System.out.println("impossible");
            } else {
                System.out.println(ans);
            }

        }

    }
}
