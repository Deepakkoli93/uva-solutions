import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class DataFlow {

    static class MaxFlow {

        private List<List<Integer>> adjList;
        private int[][] capacity;
        public int[][] flow;
        private boolean[] visited;
        private int[] bfsParent;
        private int n;
        private long[] dist;

        public MaxFlow(int n) {
            this.n = n;
            adjList = new ArrayList<>();
            for(int i=0; i<n; i++) adjList.add(new ArrayList<>());
            capacity = new int[n][n];
            flow = new int[n][n];
            visited = new boolean[n];
            bfsParent = new int[n];
            Arrays.fill(bfsParent, -1);
        }

        private int resCapacity(int from, int to) {
            return capacity[from][to] - flow[from][to];
        }

        public void makeEdge(int from, int to, int weight) {
            capacity[from][to] += weight;
//            capacity[to][from] = 0;
            if(capacity[to][from] == 0) {
                if(!adjList.get(from).contains(to)) {
                    adjList.get(from).add(to);
                }
                if(!adjList.get(to).contains(from)) {
                    adjList.get(to).add(from);
                }
            }
        }

        private int[] augmentFlow(int source, int sink, int[][] cost) {

            if(!visited[sink]) return new int[]{0,0};
            int totalCost = 0;
            int minResCap = Integer.MAX_VALUE;
            int curr = sink;
            while(bfsParent[curr] != -1) {
                int parent = bfsParent[curr];
                minResCap = Math.min(minResCap, resCapacity(parent, curr));
                curr = parent;
            }
            curr = sink;
            while(bfsParent[curr] != -1) {
                int parent = bfsParent[curr];
                flow[parent][curr] += minResCap;
                flow[curr][parent] -= minResCap;
                totalCost += (getCost(parent, curr, cost) * minResCap);
//                System.out.println("e1 - " + parent + " e2 - " + curr + " flow = " + minResCap + " cost = " + cost[parent][curr]);
                curr = parent;
            }
//            System.out.println("==============================================");
            return new int[]{minResCap, totalCost};
        }

        private void bfs(int source, int sink) {
            visited = new boolean[n];
            Arrays.fill(bfsParent, -1);
            Queue<Integer> q = new ArrayDeque<>();
            q.add(source);
            visited[source] = true;
            while(!q.isEmpty()) {
                int curr = q.poll();
                if (curr == sink) break;

                for(int nei: adjList.get(curr)) {
                    int resCap = resCapacity(curr, nei);
                    if (resCap > 0 && !visited[nei]) {
                        visited[nei] = true;
                        bfsParent[nei] = curr;
                        q.add(nei);
                    }
                }
            }

        }

        private Set<int[]> getCut(int source, int sink) {
            Set<int[]> cut = new HashSet<>();
            visited = new boolean[n];
            Arrays.fill(bfsParent, -1);
            Queue<Integer> q = new ArrayDeque<>();
            q.add(source);
            visited[source] = true;
            while(!q.isEmpty()) {
                int curr = q.poll();
                if (curr == sink) break;

                for(int nei: adjList.get(curr)) {
                    int resCap = resCapacity(curr, nei);
                    if (resCap > 0 && !visited[nei]) {
                        visited[nei] = true;
                        bfsParent[nei] = curr;
                        q.add(nei);
                    }
                }
            }
            for(int i=0; i<n; i++) {
                if(visited[i]) {
                    for(int j: adjList.get(i)) {
                        if (!visited[j]) {
                            cut.add(new int[]{i, j});
                        }
                    }
                }
            }
            return cut;
        }

        private int getCost(int i, int j, int[][] cost) {
            if(flow[i][j] >= 0) {
                return cost[i][j];
            } else {
                return -cost[j][i];
            }
        }

        private void dijkstra(int source, int sink, int[][] cost) {
            dist = new long[n];
            Arrays.fill(dist, Integer.MAX_VALUE);
            Arrays.fill(bfsParent, -1);
            visited = new boolean[n];
            dist[source] = 0;
            visited[source] = true;
            // weight, node
            Queue<long[]> q = new PriorityQueue<>(Comparator.comparingLong(o -> o[0]));
            q.add(new long[]{0, source});
            while(!q.isEmpty()) {
                long[] front = q.poll();
                visited[(int)front[1]] = true;
//                if (front[1] == sink) break;
                if(front[0] == dist[(int)front[1]]) {
                    for(int nei: adjList.get((int)front[1])) {
                        int resCap = resCapacity((int)front[1], nei);
                        if(resCap > 0 && dist[(int)front[1]] + getCost((int)front[1], nei, cost) < dist[nei]) {
                            dist[nei] = dist[(int)front[1]] + getCost((int)front[1], nei, cost);
                            q.add(new long[]{dist[nei], nei});
                            bfsParent[nei] = (int)front[1];
                        }
                    }
                }
            }
        }

        public void run(int s, int t, int[][] cost, int d) {
            int maxFlow = 0;
            long totalCost = 0;
            while(true) {
//                bfs(s,t);
                dijkstra(s, t, cost);
                int[] res = augmentFlow(s,t, cost);
                if (res[0] == 0) break;
                maxFlow += res[0];
                totalCost += res[0] * dist[t];
            }
//            System.out.println("flow = " + maxFlow);
            if(maxFlow < d) {
                System.out.println("Impossible.");
            } else {
                long tt = 0;
//                for(int i=1; i<n; i++) {
//                    for(int j: adjList.get(i)) {
//                        if(flow[i][j]>0)
//                      tt += flow[i][j] * cost[i][j];
//                    }
//                }
                tt = maxFlow * dist[t];
                System.out.println(totalCost);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String line = reader.readLine();
            if (line==null || line.isEmpty()) break;

            String[] arr = line.split("\\s+");
            int n = Integer.parseInt(arr[0]);
            int m = Integer.parseInt(arr[1]);
            int[][] cost = new int[n+1][n+1];
            for(int[] row:cost) Arrays.fill(row, Integer.MAX_VALUE);
            cost[0][1] = 0;
            cost[1][0] = 0;

            for(int i=0; i<m; i++) {
                arr = reader.readLine().split("\\s+");
                int x = Integer.parseInt(arr[0]);
                int y = Integer.parseInt(arr[1]);
                int c = Integer.parseInt(arr[2]);
                cost[x][y] = c;
                cost[y][x] = c;
            }
            arr = reader.readLine().split("\\s+");
            int d = Integer.parseInt(arr[0]);
            int k = Integer.parseInt(arr[1]);
            MaxFlow mf = new MaxFlow(n+1);
            for(int i=1; i<=n; i++) {
                for(int j=1; j<=n; j++) {
                    if (cost[i][j] != Integer.MAX_VALUE) {
                        mf.makeEdge(i, j, k);
                    }
                }
            }
            mf.makeEdge(0, 1, d);
            mf.makeEdge(1, 0, d);
            mf.run(0, n, cost, d);
        }
    }
}
