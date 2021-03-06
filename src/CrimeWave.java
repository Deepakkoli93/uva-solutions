import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class CrimeWave {

    static class Pair {
        int node;
        double weight;
        Pair(int n, double w) {
            this.node = n;
            this.weight = w;
        }
    }

    static class MaxFlow {



        private List<List<Integer>> adjList;
        private int[][] capacity;
        public int[][] flow;
        private boolean[] visited;
        private int[] bfsParent;
        private int n;
        private double[] dist;

        private final static double EPSILON = 1e-9;
        public static boolean isEqual(double a, double b){
            return Math.abs(a - b) < EPSILON;
        }

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

        private double[] augmentFlow(int source, int sink, double[][] cost) {

            if(!visited[sink]) return new double[]{0,0};
            double totalCost = 0;
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
                totalCost += (getCost(parent, curr, cost) * minResCap);
                flow[parent][curr] += minResCap;
                flow[curr][parent] -= minResCap;

//                System.out.println("e1 - " + parent + " e2 - " + curr + " flow = " + minResCap + " cost = " + getCost(parent, curr, cost));
                curr = parent;
            }
//            System.out.println("==============================================");
//            System.out.println(minResCap);
            return new double[]{minResCap, totalCost};
        }

        private double getCost(int i, int j, double[][] cost) {
            if(flow[i][j] >= 0) {
                return cost[i][j];
            } else {
                return -cost[j][i];
            }
        }

        private void dijkstra(int source, int sink, double[][] cost) {
            dist = new double[n];
            Arrays.fill(dist, Integer.MAX_VALUE);
            Arrays.fill(bfsParent, -1);
            visited = new boolean[n];
            dist[source] = 0.0;
            visited[source] = true;
            // weight, node
            Queue<Pair> q = new PriorityQueue<>(Comparator.comparingDouble(o -> o.weight));
            q.add(new Pair(source, 0.0));
            while(!q.isEmpty()) {
                Pair front = q.poll();
//                double[] front = q.poll();
                visited[front.node] = true;

                if(isEqual(front.weight, dist[front.node])) {
                    if (front.node == sink) break;
                    for(int nei: adjList.get(front.node)) {
                        int resCap = resCapacity(front.node, nei);

                        double x = dist[front.node];
                        double costOfEdge = getCost(front.node, nei, cost);
                        double y = dist[nei];
                        if(resCap > 0 &&  x + costOfEdge  < y) {
                            dist[nei] = dist[front.node] + getCost(front.node, nei, cost);
                            q.add(new Pair(nei, dist[nei]));
                            bfsParent[nei] = front.node;
                        }
                    }
                }
            }
        }

        public void run(int s, int t, double[][] cost, int num) {
            int maxFlow = 0;
            double totalCost = 0;
            while(true) {
                dijkstra(s, t, cost);
                double[] res = augmentFlow(s,t, cost);
                if (isEqual(res[0] - 0, 0.0)) break;
                maxFlow += res[0];
                totalCost += res[1];//res[0] * dist[t];
            }
//            System.out.println("flow = " + maxFlow);
            totalCost = totalCost/num;
            totalCost = Math.floor(totalCost * 100 + 0.5 + EPSILON)/100.0;
//            double avg = totalCost/num;
            System.out.println(String.format("%.2f", totalCost));

        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String[] arr = reader.readLine().split("\\s+");
            int n = Integer.parseInt(arr[0]);
            int m = Integer.parseInt(arr[1]);
            if(n==0 && m==0) break;
            double[][] cost = new double[n+m+2][n+m+2];
            for(double[] row: cost) Arrays.fill(row, 1<<30);
            MaxFlow mf = new MaxFlow(m+n+2);
            // start = 0
            // end = n+m+1
            for(int i=1; i<=m; i++) {
                cost[0][i] = 0;
                cost[i][0] = 0;
                mf.makeEdge(0, i, 1);
//                mf.makeEdge(i, 0, 1);
            }
            for(int i=m+1; i<=m+n; i++) {
                cost[i][m+n+1] = 0;
                cost[m+n+1][i] = 0;
                mf.makeEdge(i, m+n+1, 1);
//                mf.makeEdge(m+n+1, i, 1);
            }
            for(int i=0; i<n; i++) {
                arr = reader.readLine().split("\\s+");
                for(int j=0; j<m; j++) {
                    int cruiserInd = j+1;
                    int bankInd = i + m + 1;
                    double c = Double.parseDouble(arr[j]);
                    cost[cruiserInd][bankInd] = c;
                    cost[bankInd][cruiserInd] = c;
                    mf.makeEdge(cruiserInd, bankInd, 1);
//                    mf.makeEdge(bankInd, cruiserInd, 1);
                }
            }
            mf.run(0, m+n+1, cost, n);
        }
    }
}
