import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class OptimalBinarySearchTree {

    private static long solve(int[] freq, int beg, int end, int cost, long[][] dp) {
//        System.out.println("beg = " + beg + " end = " + end);
        if (dp[beg][end] != -1) {
            long ans = dp[beg][end];
            for(int i=beg; i<end; i++) {
                ans += freq[i]*cost;
            }
            return ans;
        }
        if (beg == end) return 0;
        if (end == beg+1) return cost * freq[beg];
        long ans = Integer.MAX_VALUE;
        for(int i=beg; i<end; i++) {
            ans = Math.min(ans, cost*freq[i] + solve(freq, beg, i, cost+1, dp) + solve(freq, i+1, end, cost+1, dp));
        }

        long costZeroAns = ans;
        for(int i=beg; i<end; i++) {
            costZeroAns -= freq[i]*cost;
        }
        dp[beg][end] = costZeroAns;

        return ans;
    }

    private static long solve2(int[] freq, int n) {
        long[] pref = new long[n+1];
        pref[0]=0;
        for(int i=0; i<n; i++) {
            pref[i+1] = pref[i] + freq[i];
        }
        long[][] dp = new long[n+1][n+1];
        for (long[] r1 : dp) {
            Arrays.fill(r1, Integer.MAX_VALUE);
        }
        for(int i=0; i<=n; i++) {
            dp[i][i] = 0;
        }
        for(int len=1; len<=n; len++) {
            for(int i=0; i<=n; i++) {
                int j = i+len;
                if (j <= n) {
                    for(int k=i; k<j; k++) {
                        long val = dp[i][k] + dp[k+1][j] + pref[j] - pref[i] - freq[k];
//                        for(int z=i; z<k; z++) val += freq[z];
//                        for(int z=k+1;z<j;z++) val += freq[z];
                        dp[i][j] = Math.min(dp[i][j], val);
                    }
                }
            }
        }
        return dp[0][n];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder str = new StringBuilder();
        while(true) {
            String line = reader.readLine();
            if (line == null || line.isEmpty()) break;
            line = line.trim();
            String[] arr = line.split("\\s+");
            int n = Integer.parseInt(arr[0].trim());
            int[] freq = new int[n];
            for(int i=0; i<n; i++) {
                freq[i] = Integer.parseInt(arr[i+1].trim());
            }
//            long[][] dp = new long[n+1][n+1];
//            for (long[] r1 : dp) {
//                Arrays.fill(r1, -1);
//            }
//            long[][] dp = solve2(freq, n);
//            System.out.println(solve(freq, 0, n, 0, dp));
//            System.out.println(solve2(freq, n));
            long ans = solve2(freq, n);
            str.append(ans);
            str.append('\n');
        }
        System.out.print(str);
    }
}
