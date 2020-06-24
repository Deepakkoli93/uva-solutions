import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LetMeCountTheWays {

    private static long solve(int v, int[] coins) {
        int n = coins.length;
        long[][] dp = new long[n+1][v+1];
        for(int i=0; i<n; i++) {
            dp[i][0] = 1;
        }
        for(int i=0; i<=v; i++) {
            dp[n][i] = 0;
        }
        for(int i=n-1;i>=0;i--) {
            for(int j=1; j<=v; j++) {
                if (j-coins[i] >= 0) {
                    dp[i][j] = dp[i][j - coins[i]] + dp[i + 1][j];
                } else {
                    dp[i][j] = dp[i+1][j];
                }
            }
        }
        return dp[0][v];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder str = new StringBuilder();
        while(true) {
            String line = reader.readLine();
            if(line==null || line.isEmpty()) break;
            int n = Integer.parseInt(line.trim());
            int[] coins = new int[]{1, 5, 10, 25, 50};
            long ans = solve(n, coins);
            if (ans == 1) {
                str.append("There is only 1 way to produce " + n + " cents change.\n");
            } else {
                str.append("There are " + ans + " ways to produce " + n + " cents change.\n");
            }
        }
        System.out.print(str);
    }
}
