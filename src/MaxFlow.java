import java.util.*;

public class MaxFlow {

    private static List<List<int[]>> adjList;
    private int[][] capacity;
    private int[][] flow;
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
