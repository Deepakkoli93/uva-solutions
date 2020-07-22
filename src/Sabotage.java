import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Sabotage {

    static class MaxFlow {

        private List<List<Integer>> adjList;
        private int[][] capacity;
        public int[][] flow;
        private boolean[] visited;
        private int[] bfsParent;
        private int n;


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
            if(capacity[to][from] == 0) {
                if(!adjList.get(from).contains(to)) {
                    adjList.get(from).add(to);
                }
                if(!adjList.get(to).contains(from)) {
                    adjList.get(to).add(from);
                }
            }
        }

        private int augmentFlow(int source, int sink) {
            if(!visited[sink]) return 0;

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
                curr = parent;
            }
            return minResCap;
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

        private void dijkstra(int source, int sink, int[][] cost) {
            long[] dist = new long[n];
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
                if (front[1] == sink) break;
                if(front[0] == dist[(int)front[1]]) {
                    for(int nei: adjList.get((int)front[1])) {
                        if(dist[(int)front[1]] + cost[(int)front[1]][nei] < dist[nei]) {
                            dist[nei] = dist[(int)front[1]] + cost[(int)front[1]][nei];
                            q.add(new long[]{dist[nei], nei});
                            bfsParent[nei] = (int)front[1];
                        }
                    }
                }
            }
        }

        public void run(int s, int t, int[][] cost) {
            int maxFlow = 0;
            while(true) {
                bfs(s,t);
//                dijkstra(s, t, cost);
                int inc = augmentFlow(s,t);
                if (inc == 0) break;
                maxFlow += inc;
            }
            Set<int[]> cut = getCut(s, t);
            for(int[] x: cut) {
                System.out.println((x[0]+1) + " " + (x[1]+1));
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String line = reader.readLine();
            if(line==null || line.isEmpty()) continue;
            String[] arr = line.split("\\s+");
            int n = Integer.parseInt(arr[0]);
            int m = Integer.parseInt(arr[1]);
            if(n==0 && m==0) break;
            MaxFlow mf = new MaxFlow(n);
            int[][] cost = new int[n][n];
            for(int i=0; i<m; i++) {
                arr = reader.readLine().split("\\s+");
                int x = Integer.parseInt(arr[0]) - 1;
                int y = Integer.parseInt(arr[1]) - 1;
                int c = Integer.parseInt(arr[2]);
                cost[x][y] = c;
                mf.makeEdge(x, y, c);
                mf.makeEdge(y, x, c);
            }
            mf.run(0, 1, cost);
        }
    }
}
