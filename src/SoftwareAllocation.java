import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class SoftwareAllocation {

    static class MaxFlow {

        private List<List<int[]>> adjList;
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
            capacity[from][to] = weight;
            if(capacity[to][from] == 0) {
                adjList.get(from).add(new int[]{to, 0});
                adjList.get(to).add(new int[]{from, 0});
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

                for(int[] nei: adjList.get(curr)) {
                    int resCap = resCapacity(curr, nei[0]);
                    if (resCap > 0 && !visited[nei[0]]) {
                        visited[nei[0]] = true;
                        bfsParent[nei[0]] = curr;
                        q.add(nei[0]);
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

    private static String solve(int[][] flow, int maxFlow, int numApps) {
        if (numApps != maxFlow) {
            return "!";
        } else {
            char[] ans = new char[10];
            Arrays.fill(ans, '_');
            for(int app=1; app<=26; app++) {
                for(int comp=27; comp<37; comp++) {
                    if (flow[app][comp] > 0) {
                        ans[comp-27] = (char) (app + 'A' - 1);
                    }
                }
            }
            StringBuilder str = new StringBuilder();
            for(int i=0;i<10;i++) {
                str.append(ans[i]);
            }
            return str.toString();
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String line = reader.readLine();
            if (line==null || line.isEmpty()) break;

            MaxFlow mf = new MaxFlow(38);
            int numApps = 0;
            while(true) {

                String[] arr = line.split("\\s+");
                int node = arr[0].charAt(0) - 'A' + 1;
                int capacity = Integer.parseInt(arr[0].substring(1));
                numApps += capacity;
                mf.makeEdge(0, node, capacity);

                for(int i=0; i<arr[1].length()-1; i++) {
                    int comp = Integer.parseInt(arr[1].substring(i, i+1)) + 27;
                    mf.makeEdge(node, comp, 1);
                    mf.makeEdge(comp, 37, 1);
                }
                line = reader.readLine();
                if (line== null || line.isEmpty()) break;
            }
            int maxFlow = mf.run(0, 37);
            System.out.println(solve(mf.flow, maxFlow, numApps));
        }
    }
}
