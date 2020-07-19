import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class InternetBandwidth {

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

        public int run(int s, int t) {
            int maxFlow = 0;
            while(true) {
                bfs(s,t);
                int inc = augmentFlow(s,t);
                if (inc == 0) break;
                maxFlow += inc;
            }
            return maxFlow;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int tt = 0;
        while(true) {
            int n = Integer.parseInt(reader.readLine());
            if (n == 0) break;
            tt++;
            String[] arr = reader.readLine().split("\\s+");
            int s = Integer.parseInt(arr[0])-1;
            int t = Integer.parseInt(arr[1])-1;
            int m = Integer.parseInt(arr[2]);
            MaxFlow mf = new MaxFlow(n);
            while(m-- > 0) {
                arr = reader.readLine().split("\\s+");
                int x = Integer.parseInt(arr[0])-1;
                int y = Integer.parseInt(arr[1])-1;
                int c = Integer.parseInt(arr[2]);
                mf.makeEdge(x, y, c);
                mf.makeEdge(y, x, c);
            }
            int maxFlow = mf.run(s, t);
            System.out.println("Network " + tt);
            System.out.println("The bandwidth is " + maxFlow + ".");
            System.out.println();
        }

    }
}
