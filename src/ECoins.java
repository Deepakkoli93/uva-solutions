import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class ECoins {

    private static long solve(int[] x, int[] y, int m, int s) {
        long[][] dp = new long[m+1][s*s + 1];
        for(long[] row: dp) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        for(int r = 0; r<=m; r++) {
            dp[r][s*s] = 0;
        }

        for (int r = m - 1; r >= 0; r--) {
            for (int c = s*s-1; c >= 0; c--) {
                if (c + x[r] * x[r] + y[r] * y[r] <= s * s) {
                    dp[r][c] = Math.min(1 + dp[r][c + x[r] * x[r] + y[r] * y[r]],
                            dp[r + 1][c]);
                } else {
                    dp[r][c] = dp[r + 1][c];
                }
            }
        }
        for(int i=0; i<=m; i++) {
            StringBuilder str = new StringBuilder();
            for(int j=0;j<=s*s;j++) {
                str.append(dp[i][j] + " ");
            }
            System.out.println(str);
        }
        return dp[0][0];
    }

    private static long solve2(long xSum, long ySum, int s, int m, int[] x, int[] y, int curr, long[][][] dp) {
        if (xSum > 300 || ySum > 300) return Integer.MAX_VALUE;
        if (dp[curr][(int) xSum][(int) ySum] != -1) {
            return dp[curr][(int) xSum][(int) ySum];
        }
        if (xSum*xSum + ySum*ySum > s*s) {
            return Integer.MAX_VALUE;
        }
        if (curr == m) {
            if (xSum*xSum + ySum*ySum == s*s) {
                return 0;
            } else {
                return Integer.MAX_VALUE;
            }
        }

        long ans = Math.min(1 + solve2(xSum + x[curr], ySum + y[curr], s, m, x, y, curr, dp), solve2(xSum, ySum, s, m, x, y, curr+1, dp));
        dp[curr][(int) xSum][(int) ySum] = ans;
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine().trim());
//        reader.readLine();
        while(t-- > 0) {
//            reader.readLine();
            String[] arr = reader.readLine().split("\\s+");
            int m = Integer.parseInt(arr[0].trim());
            int s = Integer.parseInt(arr[1].trim());
            int[] x = new int[m];
            int[] y = new int[m];
            for(int i=0;i<m;i++) {
                arr = reader.readLine().split("\\s+");
                x[i] = Integer.parseInt(arr[0].trim());
                y[i] = Integer.parseInt(arr[1].trim());
//                if (i==0) {
//                    System.out.println("First " + x[0] + ", " + y[0]);
//                }
            }
//            System.out.println(solve(x, y, m, s));
            long[][][] dp = new long[m+1][301][301];
            for(long[][] r1: dp) {
                for(long[] r: r1) {
                    Arrays.fill(r, -1);
                }
            }
            long ans = solve2(0, 0, s, m, x, y, 0, dp);
            if (ans == Integer.MAX_VALUE) {
                System.out.println("not possible");
            } else {
                System.out.println(ans);
            }
            if (t!=0)
            reader.readLine();
        }
    }
}
