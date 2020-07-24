import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class AngryProgrammer {


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

        public void makeEdge(int from, int to, int cap) {
            capacity[from][to] = cap;
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

         public void run(int s, int t) {
            long maxFlow = 0;
            while(true) {
                bfs(s,t);
//                dijkstra(s, t, cost);
                int inc = augmentFlow(s,t);
                if (inc == 0) break;
                maxFlow += inc;
            }
//            Set<int[]> cut = getCut(s, t);
//            for(int[] x: cut) {
//                System.out.println((x[0]+1) + " " + (x[1]+1));
//            }
            System.out.println(maxFlow);
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String[] arr = reader.readLine().split("\\s+");
            int m = Integer.parseInt(arr[0]);
            int w = Integer.parseInt(arr[1]);
            if(m==0 && w==0) break;

            MaxFlow mf = new MaxFlow(2*m);
            for(int i=0; i<m-2; i++) {
                arr = reader.readLine().split("\\s+");
                int ind = Integer.parseInt(arr[0]) - 1;
                int cap = Integer.parseInt(arr[1]);
                mf.makeEdge(ind, ind + m, cap);
//                mf.makeEdge(ind + m, ind, cap);
            }
            for(int i=0; i<w; i++) {
                arr = reader.readLine().split("\\s+");
                int ind1In = Integer.parseInt(arr[0]) - 1;
                int ind1Out = ind1In;
                if(ind1In != 0 && ind1In != m-1) {
                    ind1Out = ind1In + m;
                }

                int ind2In = Integer.parseInt(arr[1]) - 1;
                int ind2Out = ind2In;
                if(ind2In != 0 && ind2In != m-1) {
                    ind2Out = ind2In + m;
                }
                int cap = Integer.parseInt(arr[2]);
                mf.makeEdge(ind1Out, ind2In, cap);
//                mf.makeEdge(ind2In,ind1Out, cap);

                mf.makeEdge(ind2Out, ind1In, cap);
//                mf.makeEdge( ind1In, ind2Out, cap);
            }
            mf.run(0, m-1);
        }
    }
}
