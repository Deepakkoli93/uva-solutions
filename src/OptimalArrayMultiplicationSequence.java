import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class OptimalArrayMultiplicationSequence {

    private static String construct(int[][] dp_kValues, int beg, int end) {
        if (end == beg + 1) {
           return "A" + (beg+1);
        }
        int k = dp_kValues[beg][end];
        String x = construct(dp_kValues, beg, k);
        String y = construct(dp_kValues, k, end);
        return "(" + x + " x " + y + ")";
    }

    private static String solve(int[] rows, int[] cols, int n) {
        long[][] dp = new long[n+1][n+1];
        for(long[] row:dp) Arrays.fill(row, Integer.MAX_VALUE);
        for(int i=0;i<=n;i++) dp[i][i] = 0;
        for(int i=0; i<n; i++) dp[i][i+1] = 0;


        int[][] dp_kValue = new int[n+1][n+1];
        for(int[] row: dp_kValue) Arrays.fill(row, -1);


        for(int len=2; len<=n; len++) {
            for(int i=0; i<=n; i++) {
                int j = i + len;
                if(j <= n) {
                    for(int k=i+1; k<j; k++) {
                        long val = dp[i][k] + dp[k][j] + rows[i] * cols[k-1] * cols[j-1];
                        if  (val < dp[i][j]) {
                            dp[i][j] = val;
                            dp_kValue[i][j] = k;
                        }
                    }
                }
            }
        }
//        System.out.println(dp[0][n]);
//        for(int i=0;i<=n;i++) {
//            StringBuilder str = new StringBuilder();
//            for(int j=0;j<=n;j++) {
//                str.append(dp_kValue[i][j] + " " );
//            }
//            System.out.println(str);
//        }
        return construct(dp_kValue, 0, n);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int count = 0;
        while(true) {
            int n = Integer.parseInt(reader.readLine().trim());
            if(n==0) break;
            count++;
            int[] rows = new int[n];
            int[] cols = new int[n];
            for(int i=0;i<n;i++) {
                String[] arr = reader.readLine().split("\\s+");
                rows[i] = Integer.parseInt(arr[0]);
                cols[i] = Integer.parseInt(arr[1]);
            }
            System.out.println("Case " + count +": " + solve(rows, cols, n));
        }
    }
}
