import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Checkers {

    private static long p = 1000007;

    private static boolean isValid(char[][] board, int i, int j, int n) {
        return i>=0 && j>=0 && i<n && j<n && board[i][j] == '.';
    }

    private static long solve(char[][] board, int startRow, int startCol, int n) {
        long[][] dp = new long[n][n];
        dp[startRow][startCol] = 1;


        for(int row=n-1; row>=0; row--) {
            for(int col=0; col<n; col++) {
                int r = row-1;
                int c = col-1;
                if(isValid(board, r, c, n)) {
                    dp[r][c] += dp[row][col] % p;
                } else {
                    r = row-2;
                    c = col-2;
                    if(isValid(board, r, c, n)) {
                        dp[r][c] += dp[row][col] % p;
                    }
                }
                r = row-1;
                c = col+1;
                if(isValid(board, r, c, n)) {
                    dp[r][c] += dp[row][col] % p;
                } else {
                    r = row-2;
                    c = col+2;
                    if(isValid(board, r, c, n)) {
                        dp[r][c] += dp[row][col] % p;
                    }
                }
            }
        }
        long ans = 0;
        for(int i=0;i<n;i++) {
            ans = (ans + dp[0][i]) % p;
        }
//        System.out.println(ans);
//        System.out.println(ans%1000007);
        return ans % p;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int tt = Integer.parseInt(reader.readLine());
        for(int t=1; t<=tt; t++) {
            int n = Integer.parseInt(reader.readLine());
            char[][] board = new char[n][n];
            int startRow = -1;
            int startCol = -1;
            for(int i=0; i<n; i++) {
                String line = reader.readLine();
                for(int j=0; j<n; j++) {
                    board[i][j] = line.charAt(j);
                    if(board[i][j] == 'W') {
                        startRow = i;
                        startCol = j;
                    }
                }
            }
            long ans = solve(board, startRow, startCol, n);
            System.out.println("Case " + t + ": " + ans);
        }
    }
}
