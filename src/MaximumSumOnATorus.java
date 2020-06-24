import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MaximumSumOnATorus {

    private static int get(int[][]dp, int i, int j, int n) {
        if (i<0 || j<0 || i>=n || j>=n) return 0;
        return dp[i][j];
    }
    private static int solveOne(int[][] mat, int n) {
        int[][] dp =new int[2*n][2*n];
        dp[0][0] = mat[0][0];
        for(int i=1;i<2*n;i++) dp[0][i] = mat[0][i] + dp[0][i-1];
        for(int i=1;i<2*n;i++) dp[i][0] = mat[i][0] + dp[i-1][0];
        for(int i=1;i<2*n;i++) {
            for(int j=1;j<2*n;j++) {
                dp[i][j] = dp[i-1][j] + dp[i][j-1] - dp[i-1][j-1] + mat[i][j];
            }
        }
        int ans = Integer.MIN_VALUE;
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                for(int k=i;k<2*n && (k-i)<n;k++) {
                    for(int l=j;l<2*n && (l-j)<n;l++) {
                        ans = Math.max(ans, dp[k][l] - get(dp, i-1, l, 2*n) - get(dp, k, j-1, 2*n) + get(dp, i-1, j-1, 2*n));
                    }
                }
            }
        }

        return ans;
    }

    private static int solve(int[][] mat, int n) {
        // right
        int[][] mat2 = new int[2*n][2*n];
        for(int i=0; i<n; i++) {
            for(int j=0;j<n;j++) {
                mat2[i][j] = mat[i][j];
                mat2[i+n][j] = mat[i][j];
                mat2[i][j+n] = mat[i][j];
                mat2[i+n][j+n] = mat[i][j];
            }
        }
        return solveOne(mat2, n );
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine().trim());
        while(t-- > 0) {
            int n = Integer.parseInt(reader.readLine().trim());
            int[][] mat = new int[n][n];
            for(int i=0; i<n; i++) {
                String[] arr = reader.readLine().split("\\s+");
                for(int j=0; j<n; j++) {
                    mat[i][j] = Integer.parseInt(arr[j]);
                }
            }
            System.out.println(solve(mat, n));


        }
    }
}
