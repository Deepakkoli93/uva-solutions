import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class FlightPlanner {

    private static long solve(int[][] winds, int X) {
        long[][] dp = new long[10][X/100];
        for(long[] row:dp) Arrays.fill(row, Integer.MAX_VALUE);

        dp[9][X/100 -1] = 30 - winds[9][X/100 -1];
        dp[8][X/100 -1] = 20 - winds[8][X/100 -1];
        for(int j=X/100-2; j>=0 ;j--) {
            for(int i=0; i<10; i++) {
                if (i-1>=0)
                dp[i][j] = Math.min(dp[i][j], 60 - winds[i][j] + dp[i-1][j+1]);

                dp[i][j] = Math.min(dp[i][j], 30 - winds[i][j] + dp[i][j+1]);

                if (i+1 < 10)
                dp[i][j] = Math.min(dp[i][j], 20 - winds[i][j] + dp[i+1][j+1]);
            }
        }
        return dp[9][0];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine().trim());
        while(t-- > 0) {
            reader.readLine();
            int X = Integer.parseInt(reader.readLine().trim());
            int[][] winds = new int[10][X/100];
            for(int i=0;i<10;i++) {
                String[] arr = reader.readLine().trim().split("\\s+");
                for(int j=0;j<X/100;j++) {
                    winds[i][j] = Integer.parseInt(arr[j].trim());
                }
            }
            System.out.println(solve(winds, X));
            System.out.println();
        }
    }
}
