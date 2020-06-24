import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class TroubleOf13Dots {

    private static int solve(int m, int mSpent, int curr, int[] p, int[] f, int n, int[][] dp) {
        if (dp[curr][mSpent] != -1) return dp[curr][mSpent];
//        if (mSpent > m && m < 1800) return Integer.MIN_VALUE;
        if (mSpent > m+200) return Integer.MIN_VALUE;
        if (curr >= n && mSpent <= 2000 && mSpent > m) return Integer.MIN_VALUE;

        if (curr >= n){
            return 0;
        }
        int ans = Math.max(f[curr] + solve(m, mSpent + p[curr], curr + 1, p, f, n, dp),
                    solve(m, mSpent, curr + 1, p, f, n, dp));

        dp[curr][mSpent] = ans;
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String line = reader.readLine();
            if(line==null || line.isEmpty()) break;
            String[] arr = line.split("\\s+");
            int m = Integer.parseInt(arr[0]);
            int n = Integer.parseInt(arr[1]);
            int[] p = new int[n];
            int[] f = new int[n];
            for(int i=0;i<n;i++) {
                arr = reader.readLine().split("\\s+");
                p[i] = Integer.parseInt(arr[0]);
                f[i] = Integer.parseInt(arr[1]);
            }
            int[][] dp = new int[110][15000];
            for(int[] row: dp) {
                Arrays.fill(row, -1);
            }
                System.out.println(solve(m, 0, 0, p, f, n, dp));

        }
    }
}
