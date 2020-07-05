import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ACMContestAndBlackout {

    static class UF {

        private int[] parent;  // parent[i] = parent of i
        private int[] rank;   // rank[i] = rank of subtree rooted at i (never more than 31)
        private int count;     // number of components
        private int[] size;

        /**
         * Initializes an empty union-find data structure with
         * {@code n} elements {@code 0} through {@code n-1}.
         * Initially, each elements is in its own set.
         *
         * @param  n the number of elements
         * @throws IllegalArgumentException if {@code n < 0}
         */
        public UF(int n) {
            if (n < 0) throw new IllegalArgumentException();
            count = n;
            parent = new int[n];
            rank = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
                size[i] = 1;
            }
        }

        /**
         * Returns the canonical element of the set containing element {@code p}.
         *
         * @param  p an element
         * @return the canonical element of the set containing {@code p}
         * @throws IllegalArgumentException unless {@code 0 <= p < n}
         */
        public int find(int p) {
            validate(p);
            while (p != parent[p]) {
                parent[p] = parent[parent[p]];    // path compression by halving
                p = parent[p];
            }
            return p;
        }

        /**
         * Returns the number of sets.
         *
         * @return the number of sets (between {@code 1} and {@code n})
         */
        public int count() {
            return count;
        }

        /**
         * Returns true if the two elements are in the same set.
         *
         * @param  p one element
         * @param  q the other element
         * @return {@code true} if {@code p} and {@code q} are in the same set;
         *         {@code false} otherwise
         * @throws IllegalArgumentException unless
         *         both {@code 0 <= p < n} and {@code 0 <= q < n}
         * @deprecated Replace with two calls to {@link #find(int)}.
         */
        @Deprecated
        public boolean connected(int p, int q) {
            return find(p) == find(q);
        }

        /**
         * Merges the set containing element {@code p} with the
         * the set containing element {@code q}.
         *
         * @param  p one element
         * @param  q the other element
         * @throws IllegalArgumentException unless
         *         both {@code 0 <= p < n} and {@code 0 <= q < n}
         */
        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP == rootQ) return;

            // make root of smaller rank point to root of larger rank
            if      (rank[rootP] < rank[rootQ]) {
                parent[rootP] = rootQ;
                size[rootQ] += size[rootP];
            }
            else if (rank[rootP] > rank[rootQ]){
                parent[rootQ] = rootP;
                size[rootP] += size[rootQ];
            }
            else {
                parent[rootQ] = rootP;
                size[rootP] += size[rootQ];
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

    private static void solve(int n, List<List<Integer>> edgeList) {

        edgeList.sort(Comparator.comparingDouble(o -> o.get(2)));

        UF uf = new UF(n);
        int m = edgeList.size();
        int indCounter = 0;
        int[] indices = new int[n-1];
        int minCost = 0;
        for(int i=0; i<m; i++) {
            List<Integer> edge = edgeList.get(i);
            int u = edge.get(0);
            int v = edge.get(1);
            if(uf.find(u) != uf.find(v)) {
                minCost += edge.get(2);
                indices[indCounter++] = i;
                uf.union(u, v);
            }
        }
        int secondCost = Integer.MAX_VALUE;
        for(int j=0; j<n-1; j++) {
            int edgeInd = indices[j];
            uf = new UF(n);
            int cost = 0;
            for(int i=0; i<m; i++) {
                if(i != edgeInd) {
                    List<Integer> edge = edgeList.get(i);
                    int u = edge.get(0);
                    int v = edge.get(1);
                    if (uf.find(u) != uf.find(v)) {
                        cost += edge.get(2);
                        uf.union(u, v);
                    }
                }
            }
            if(uf.count == 1)
            secondCost = Math.min(secondCost, cost);
        }
        System.out.println(minCost + " " + secondCost);

    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine());
        while (t-- > 0) {
            String[] arr = reader.readLine().split("\\s+");
            int n = Integer.parseInt(arr[0]);
            int m = Integer.parseInt(arr[1]);
            List<List<Integer>> edgeList = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                List<Integer> l = new ArrayList<>();
                arr = reader.readLine().split("\\s+");
                l.add(Integer.parseInt(arr[0]) - 1);
                l.add(Integer.parseInt(arr[1]) - 1);
                l.add(Integer.parseInt(arr[2]));
                edgeList.add(l);
            }
            solve(n, edgeList);
        }
    }
}

