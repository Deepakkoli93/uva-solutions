import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NetworkConnections {
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

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine());
        reader.readLine();
        while(t-- > 0) {
            int n  = Integer.parseInt(reader.readLine());
            UF uf = new UF(n);
            int succ = 0;
            int fail = 0;
            while(true) {
                String line = reader.readLine();
                if (line == null || line.isEmpty()) break;
                String[] arr = line.split("\\s+");
                int i = Integer.parseInt(arr[1]);
                int j = Integer.parseInt(arr[2]);
                if (arr[0].equalsIgnoreCase("c")) {
                    uf.union(i-1, j-1);
                } else if (arr[0].equalsIgnoreCase("q")) {
                    if (uf.find(i-1) == uf.find(j-1)) {
                        succ++;
                    } else {
                        fail++;
                    }
                }
            }
            System.out.println(succ+","+fail);
            if (t!=0) {
                System.out.println();
            }
        }
    }
}
