import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MaximumSum {

    private static int get(int[][]dp, int i, int j, int n) {
        if (i<0 || j<0 || i>=n || j>=n) return 0;
        return dp[i][j];
    }
    private static int solve(int[][] mat, int n) {
        int[][] dp =new int[n][n];
        dp[0][0] = mat[0][0];
        for(int i=1;i<n;i++) dp[0][i] = mat[0][i] + dp[0][i-1];
        for(int i=1;i<n;i++) dp[i][0] = mat[i][0] + dp[i-1][0];
        for(int i=1;i<n;i++) {
            for(int j=1;j<n;j++) {
                dp[i][j] = dp[i-1][j] + dp[i][j-1] - dp[i-1][j-1] + mat[i][j];
            }
        }
        int ans = Integer.MIN_VALUE;
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                for(int k=i+1;k<n;k++) {
                    for(int l=j+1;l<n;l++) {
                        ans = Math.max(ans, dp[k][l] - get(dp, i-1, l, n) - get(dp, k, j-1, n) + get(dp, i-1, j-1, n));
                    }
                }
            }
        }

        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());
        int[][] mat = new int[n][n];
        int[] arr = new int[n*n];
        int i = 0;
        while(true) {
            String[] a = reader.readLine().split("\\s+");
            for(String x:a) {
                arr[i++] = Integer.parseInt(x);
            }
            if(i==n*n) break;
        }
        i=0;
        for(int j=0;j<n;j++) {
            for(int k=0;k<n;k++) {
                mat[j][k] = arr[i++];
            }
        }
        System.out.println(solve(mat, n));
    }
}
