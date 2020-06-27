import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BarCodes {

    private static long solve(int n, int k, int m, long[][][] dp) {
        if (dp[n][k][m] != -1) return dp[n][k][m];
        if(n==0) {
            if (k==0) return 1;
            else return 0;
        }
        if(k==0) {
            if(n==0) return 1;
            else return 0;
        }

        long ans = 0;
        for(int i=1; i<=m ;i++) {
            if (i <= n) {
                ans += solve(n-i, k-1, m, dp);
            }
        }
        dp[n][k][m] = ans;
        return ans;
    }


    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String line = reader.readLine();
            if(line==null || line.isEmpty()) break;
            String[] arr = line.split("\\s+");
            int n = Integer.parseInt(arr[0]);
            int k = Integer.parseInt(arr[1]);
            int m = Integer.parseInt(arr[2]);
            long[][][] dp = new long[51][51][51];
            for(long[][] row: dp) {
                for(long[] ro2: row) {
                    Arrays.fill(ro2, -1);
                }
            }
            System.out.println(solve(n, k, m, dp));
        }
    }
}
