import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class TransportationSystem {

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

    private static void solve(int n, double[][] squareDistance, List<List<Integer>> edgeList, int r, int t) {

        edgeList.sort(Comparator.comparingDouble(o -> squareDistance[o.get(0)][o.get(1)]));

        UF uf = new UF(n);
        UF uf2 = new UF(n);
        double roads = 0;
        double rails = 0;
        for(List<Integer> edge: edgeList) {
            if(uf.find(edge.get(0)) != uf.find(edge.get(1))) {
                if(squareDistance[edge.get(0)][edge.get(1)] <= r*r) {
                    roads += Math.sqrt(squareDistance[edge.get(0)][edge.get(1)]);
                } else {
                    rails += Math.sqrt(squareDistance[edge.get(0)][edge.get(1)]);
                }
                uf.union(edge.get(0), edge.get(1));
            }

            if(uf2.find(edge.get(0)) != uf2.find(edge.get(1))) {
                if(squareDistance[edge.get(0)][edge.get(1)] <= r*r) {
                    uf2.union(edge.get(0), edge.get(1));
                }
            }
        }

        int cities = uf2.count;
        System.out.println("Case #" + t +": "+cities + " " + Math.round(roads) + " " + Math.round(rails));
    }
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int tt = Integer.parseInt(reader.readLine());
        for(int t=1; t<=tt; t++) {
            String[] arr = reader.readLine().split("\\s+");
            int n = Integer.parseInt(arr[0]);
            int r = Integer.parseInt(arr[1]);
            List<List<Integer>> edgeList = new ArrayList<>();
            int[][] nodes = new int[n][2];
            for(int i=0; i<n; i++) {
                arr = reader.readLine().split("\\s+");
                nodes[i][0] = Integer.parseInt(arr[0]);
                nodes[i][1] = Integer.parseInt(arr[1]);
            }
            double[][] squareDistance = new double[n][n];
            for(int i=0; i<n; i++) {
                for (int j = i + 1; j < n; j++) {
                    squareDistance[i][j] = Math.pow(Math.abs(nodes[i][0] - nodes[j][0]), 2) + Math.pow(Math.abs(nodes[i][1] - nodes[j][1]), 2);
                    List<Integer> l = new ArrayList<>();
                    l.add(i);
                    l.add(j);
                    edgeList.add(l);
                }
            }
            solve(n, squareDistance, edgeList, r, t);
        }
    }
}
