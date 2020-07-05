import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class AudioPhobia {


    static class UF {

        private int[] parent;  // parent[i] = parent of i
        private int[] rank;   // rank[i] = rank of subtree rooted at i
        private int count;     // number of components

        public UF(int n) {
            if (n < 0) throw new IllegalArgumentException();
            count = n;
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        public int find(int p) {
            validate(p);
            while (p != parent[p]) {
                parent[p] = parent[parent[p]];    // path compression by halving
                p = parent[p];
            }
            return p;
        }

        public int count() {
            return count;
        }

        @Deprecated
        public boolean connected(int p, int q) {
            return find(p) == find(q);
        }



        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP == rootQ) return;

            // make root of smaller rank point to root of larger rank
            if      (rank[rootP] < rank[rootQ]) parent[rootP] = rootQ;
            else if (rank[rootP] > rank[rootQ]) parent[rootQ] = rootP;
            else {
                parent[rootQ] = rootP;
                rank[rootP]++;
            }
            count--;
        }

        // validate that p is a valid index
        private void validate(int p) {
            int n = parent.length;
            if (p < 0 || p >= n) {
                throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
            }
        }

    }

    private static int dfs(Map<Integer, List<Integer>> adjList, int curr, int dest, int[][] weights, boolean[] visited) {
        if(dest == curr) return 0;
        int ans = -1;
        for(int nei: adjList.get(curr)) {
            if(!visited[nei]) {
                visited[nei] = true;
                int res = dfs(adjList, nei, dest, weights, visited);
                if(res != -1) {
                    ans = Math.max(res, weights[curr][nei]);
                }
            }
        }
        return ans;
    }

    private static void solve(int n, List<List<Integer>> edgeList, int[][] queries, int t) {

        edgeList.sort(Comparator.comparingDouble(o -> o.get(2)));

        UF uf = new UF(n);
        Map<Integer, List<Integer>> adjList = new HashMap<>();
        for(int i=0; i<n; i++) adjList.put(i, new ArrayList<>());
        int[][] weights = new int[n][n];

        for(List<Integer> edge: edgeList) {
            int u = edge.get(0);
            int v = edge.get(1);
            weights[u][v] = edge.get(2);
            weights[v][u] = edge.get(2);
            if(uf.find(u) != uf.find(v)) {
                uf.union(u, v);
                adjList.get(u).add(v);
                adjList.get(v).add(u);
            }
        }
        if(t!=1) System.out.println();
        System.out.println("Case #" + t);
        for(int[] q: queries) {
            int u = q[0];
            int v = q[1];
            int ans = dfs(adjList, u, v, weights, new boolean[n]);
            if(ans == -1) {
                System.out.println("no path");
            } else {
                System.out.println(ans);
            }
        }

    }
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = 0;
        while(true) {
            String[] arr = reader.readLine().split("\\s+");
            int c = Integer.parseInt(arr[0]);
            int s = Integer.parseInt(arr[1]);
            int q = Integer.parseInt(arr[2]);
            if(c==0 && s==0 && q==0) break;
            t++;
            List<List<Integer>> edgeList = new ArrayList<>();
            for(int i=0; i<s; i++) {
                arr = reader.readLine().split("\\s+");
                List<Integer> l = new ArrayList<>();
                l.add(Integer.parseInt(arr[0]) - 1);
                l.add(Integer.parseInt(arr[1]) - 1);
                l.add(Integer.parseInt(arr[2]));
                edgeList.add(l);
            }
            int[][] queries = new int[q][2];
            for(int i=0; i<q; i++) {
                arr = reader.readLine().split("\\s+");
                queries[i][0] = Integer.parseInt(arr[0]) - 1;
                queries[i][1] = Integer.parseInt(arr[1]) - 1;
            }
            solve(c, edgeList, queries, t);
        }
    }
}
