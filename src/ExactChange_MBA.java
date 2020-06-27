import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class ExactChange {

    private static int[] solve2(int[] coins, int n, int s) {
        int[][] dp1 = new int[n+1][20001];
        int[][] dp2 = new int[n+1][20001];
        for(int[] row: dp1) {
            Arrays.fill(row, 500000);
        }
        for(int[] row: dp2) {
            Arrays.fill(row, 0);
        }
        for(int i=0; i<20001; i++) {
            if(i >= s) {
                dp1[n][i] = i;
                dp2[n][i] = 0;
            }
        }
        for(int i=0; i<=n; i++) {
            dp1[i][20000] = 20000;
            dp2[i][20000] = 0;
        }

        for(int r=n-1; r>=0; r--) {
            for(int c=20000-1; c>=0; c--) {
                if (c+coins[r] <= 20000) {
                    if (dp1[r + 1][c + coins[r]] < dp1[r + 1][c]) {
                        dp1[r][c] = dp1[r + 1][c + coins[r]];
                        dp2[r][c] = 1 + dp2[r+1][c+coins[r]];
                    } else if (dp1[r + 1][c + coins[r]] == dp1[r + 1][c]) {
                        dp1[r][c] =  dp1[r + 1][c + coins[r]];
                        if (1 + dp2[r+1][c+coins[r]] < dp2[r+1][c]) {
                            dp2[r][c] = 1 + dp2[r+1][c+coins[r]];
                        } else {
                            dp2[r][c] = dp2[r+1][c];
                        }
                    } else {
                        dp1[r][c] = dp1[r + 1][c];
                        dp2[r][c] = dp2[r+1][c];
                    }

                } else {
                    dp1[r][c] = dp1[r+1][c];
                    dp2[r][c] = dp2[r+1][c];
                }
            }
        }

//        System.out.println("min = " + dp1[0][0]);
//        System.out.println("min = " + dp1[1][500]);
//        System.out.println("min = " + dp1[1][500]);
        int[] ans = new int[2];
        ans[0] = dp1[0][0];
        ans[1] = dp2[0][0];
        return ans;
    }

    private static int[] solve(int[] coins, int n, int s, int currSum, int currCoins, int curr, int[][] dp1, int[][] dp2) {
        if (currSum >= 20001) {
            int[] ans = new int[2];
            ans[0] = currSum - s;
            ans[1] = currCoins;
            return ans;
        }
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
        StringBuilder str = new StringBuilder();
        while(t-- > 0) {
            int s = Integer.parseInt(reader.readLine().trim());
            int n = Integer.parseInt(reader.readLine().trim());
            int[] coins = new int[n];
            for(int i=0;i<n;i++) {
                coins[i] = Integer.parseInt(reader.readLine());
            }
            int[][] dp1 = new int[n+1][20001];
            int[][] dp2 = new int[n+1][20001];
            for(int[] row:dp1) {
                Arrays.fill(row, -1);
            }
            for(int[] row:dp2) {
                Arrays.fill(row, -1);
            }
//            int[] ans = solve(coins, n, s, 0, 0, 0, dp1, dp2);
            int[] ans = solve2(coins, n, s);
            str.append(ans[0] + " " + ans[1] + '\n');
//            System.out.println(ans[0] + " " + ans[1]);
        }
        System.out.print(str);
    }
}