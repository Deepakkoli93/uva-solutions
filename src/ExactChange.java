import org.omg.PortableInterceptor.INACTIVE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class ExactChange {

    private static int[] solve(int[] coins, int n, int s, int currSum, int currCoins, int curr, int[][] dp1, int[][] dp2) {
        if (dp1[curr][currSum] != -1) {
            int[] ans = new int[2];
            ans[0] = dp1[curr][currSum];
            ans[1] = dp2[curr][currSum];
        }
        if (currSum >= s || curr == n) {
            int extra = Integer.MAX_VALUE;
            if (currSum >= s) {
                extra = currSum - s;
            }
            int[] ans = new int[]{extra, currCoins};
            return ans;
        }
        int[] a1 = solve(coins, n, s, currSum + coins[curr], currCoins + 1, curr + 1, dp1, dp2);
        int[] a2 = solve(coins, n, s, currSum, currCoins, curr+1, dp1, dp2);
        int[] ans = a1;
        if (a1[0] < a2[0]) {
            ans = a1;
        } else if (a1[0] == a2[0]) {
            if (a1[1] < a2[1]) {
                ans = a1;
            } else {
                ans = a2;
            }
        } else {
            ans = a2;
        }
        dp1[curr][currSum] = ans[0];
        dp2[curr][currSum] = ans[1];
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine());
        while(t-- > 0) {
            int s = Integer.parseInt(reader.readLine().trim());
            int n = Integer.parseInt(reader.readLine().trim());
            int[] coins = new int[n];
            for(int i=0;i<n;i++) {
                coins[i] = Integer.parseInt(reader.readLine());
            }
            int[][] dp1 = new int[n+1][1000001];
            int[][] dp2 = new int[n+1][1000001];
            for(int[] row:dp1) {
                Arrays.fill(row, -1);
            }
            for(int[] row:dp2) {
                Arrays.fill(row, -1);
            }
            int[] ans = solve(coins, n, s, 0, 0, 0, dp1, dp2);
            System.out.println((s + ans[0]) + " " + ans[1]);
        }
    }
}
