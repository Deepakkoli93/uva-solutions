import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class ShoppingTrip {

    private static boolean allDvdsBought(int bitmask, int p) {
        for(int i=0; i<p; i++) {
            if((bitmask & (1 << i)) == 0) {
                return false;
            }
        }
        return true;
    }

    private static long dfs(long[][] dist, int n, int pos, int bitmask, int[] dvdStores, int p, long[] savingAtStore, long[][] dp) {
        if(dp[pos][bitmask] != -1) return dp[pos][bitmask];
        if (allDvdsBought(bitmask, p)) {
            return dist[pos][0];
        }
        long ans = Integer.MAX_VALUE;
        for(int next=0; next<p; next++) {
            int nextStore = dvdStores[next];
            if((bitmask & (1 << next)) == 0) {
                ans = Math.min(ans, dist[pos][nextStore] - savingAtStore[next] + dfs(dist, n, nextStore, bitmask | (1 << next), dvdStores, p, savingAtStore,dp));
            }
        }
        // go home early
        long cost = dist[pos][0];
//        for(int i=0; i<p; i++) {
//            if ((bitmask & (1 << i)) == 0) {
//                cost += savingAtStore[dvdStores[i]];
//            }
//        }
        ans = Math.min(ans, cost);
        dp[pos][bitmask] = ans;
//        if(level==1) System.out.println("ans = " + ans + " pos = " + pos);
        return ans;
    }

    private static long solve(long[][] adjMat, int n, int[] dvdStores, int p, long[] savingAtStore, long[][] dp1) {
        long[][] dist = new long[n+1][n+1];
        for(int i=0; i<=n; i++) {
            if (n + 1 >= 0) System.arraycopy(adjMat[i], 0, dist[i], 0, n + 1);
        }
        for(int k=0; k<=n; k++) {
            for(int i=0; i<=n; i++) {
                for(int j=0; j<=n; j++) {
                    if (dist[i][j] > dist[i][k] + dist[k][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }
//        return dfs(dist, n, 0, 0, dvdStores, p, savingAtStore,dp1);
        int cols = (int)Math.pow(2,p);
        long[][] dp = new long[n+1][cols];
        for(long[] row: dp) Arrays.fill(row, Integer.MAX_VALUE);
        for(int i=0; i<=n; i++) {
            dp[i][cols-1] = dist[i][0];
        }
        for(int j=cols-2; j>=0; j--) {
            for(int i=0; i<=n; i++) {
                for(int next=0; next<p; next++) {
                    if ((j & (1 << next)) == 0) {
                        int nextStore = dvdStores[next];
//                        System.out.println("nextstore = " + nextStore);
                        dp[i][j] = Math.min(dp[i][j], dist[i][nextStore] - savingAtStore[next] + dp[nextStore][j | (1 << next)] ) ;
                    }
                }
                dp[i][j] = Math.min(dp[i][j], dist[i][0]);
            }
        }
//        System.out.println("dp[0][0] = " + dp[0][0]);
//        for(int i=0; i<=n; i++) {
//            StringBuilder str = new StringBuilder();
//            for(int c=0; c<cols;c++ ) {
//                str.append(dp[i][c] + " ");
//            }
//            System.out.println(str);
//        }
        return dp[0][0];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine());
        while(t-- > 0) {
            reader.readLine();
            String[] arr = reader.readLine().split("\\s+");
            int n = Integer.parseInt(arr[0]);
            int m = Integer.parseInt(arr[1]);
            long[][] adjMat = new long[n+1][n+1];
            for(long[] row:adjMat) Arrays.fill(row, Integer.MAX_VALUE);
//            System.out.println("n="+n);
//            System.out.println("m="+m);
            for(int i=0;i<=n;i++) adjMat[i][i]=0;
            for(int i=0;i<m;i++){
                arr = reader.readLine().split("\\s+");
                int v1 = Integer.parseInt(arr[0]);
                int v2 = Integer.parseInt(arr[1]);
                int c = 0;
                String[] arr1 = arr[2].split("\\.");
                c += Integer.parseInt(arr1[0]) * 100;
                c += Integer.parseInt(arr1[1]);
                adjMat[v1][v2] = Math.min(adjMat[v1][v2],c);
                adjMat[v2][v1] = Math.min(adjMat[v2][v1],c);
            }
            int p = Integer.parseInt(reader.readLine());
            long[] savingAtStore = new long[p];
            int[] dvdStores = new int[p];
            for(int i=0; i<p; i++) {
                arr = reader.readLine().split("\\s+");
                String[] arr1 = arr[1].split("\\.");
                int c = 0;
                c += Integer.parseInt(arr1[0]) * 100;
                c += Integer.parseInt(arr1[1]);
                savingAtStore[i] = c;
                dvdStores[i] = Integer.parseInt(arr[0]);
            }

            long[][] dp = new long[n+2][(int)Math.pow(2, p+1)];
            for(long[] row:dp) Arrays.fill(row, -1);
            long ans = solve(adjMat, n, dvdStores, p, savingAtStore, dp);
            if (ans < 0) {
                ans = -ans;
                String rem = "" + (int)ans % 100;
                if(rem.length()<2) rem = "0"+rem;
                System.out.println("Daniel can save $"+(ans/100)+"."+(rem));
            } else {
                System.out.println("Don't leave the house");
            }
//            System.out.println("ans = " + ans);

        }
    }
}
