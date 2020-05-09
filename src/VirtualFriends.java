import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class VirtualFriends {

    static class UF {

        private int[] parent;  // parent[i] = parent of i
        private int[] rank;   // rank[i] = rank of subtree rooted at i
        private int count;     // number of components
        private int[] size;

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

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine().trim());
        while(t-- > 0) {
            int n = Integer.parseInt(reader.readLine());
            UF uf = new UF(2*n);
            Map<String, Integer> frInd = new HashMap<>();
            int ind = 0;
            while(n-- > 0) {
                String[] arr = reader.readLine().split("\\s+");
                int i = 0;
                int j = 0;
                if (frInd.containsKey(arr[0])) {
                    i = frInd.get(arr[0]);
                } else {
                    i = ind++;
                    frInd.put(arr[0], i);
                }

                if (frInd.containsKey(arr[1])) {
                    j = frInd.get(arr[1]);
                } else {
                    j = ind++;
                    frInd.put(arr[1], j);
                }
                uf.union(i, j);
                System.out.println(uf.size[uf.find(i)]);
            }
        }
    }
}
