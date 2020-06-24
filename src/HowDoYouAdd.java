import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class HowDoYouAdd {

    private static long mod = 1000000;

    private static long solve(int n, int k, int chosen, int sum, long[][] dp) {
        if (sum > n) return 0;
        if (dp[chosen][sum] != -1) return dp[chosen][sum];
        if (chosen == k) {
            if (sum == n) return 1;
            else return 0;
        }
        long ans = 0;
        for (int i = 0; i <= n; i++) {
            ans = (ans + solve(n, k, chosen + 1, sum + i, dp) % mod) % mod;
        }
        ans = ans % mod;
        dp[chosen][sum] = ans;
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String[] arr = reader.readLine().split("\\s+");
            int n = Integer.parseInt(arr[0].trim());
            int k = Integer.parseInt(arr[1].trim());
            if (n==0 && k==0) break;
            long[][] dp = new long[k+1][n+1];
            for( long[] row:dp)Arrays.fill(row, -1);
            System.out.println(solve(n, k, 0, 0, dp));
        }
    }
}
