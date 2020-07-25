import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InjuredQueen {

    private static int getRowNum(char c) {
        if((int)c >= 49 && (int)c <= 57) {
            return (int)c - 49;
        } else {
            return (int)c - 65 + 9;
        }
    }

    private static int getQueenRowInCol(int[][] board, int c, int n) {
        for(int i=0; i<n; i++) {
            if (board[i][c] == 1) {
                return i;
            }
        }
        return -1;
    }

    private static boolean isClashing(int r1, int r2) {
        return Math.abs(r1 - r2) <= 1;
    }

    private static void solve(int[][] board, int n) {
        long[][] dp = new long[n][n];
        int rowForLastCol = getQueenRowInCol(board, n-1, n);
        if (rowForLastCol > -1) {
            dp[rowForLastCol][n-1] = 1;
        } else {
           for(int i=0; i<n; i++) {
               dp[i][n-1] = 1;
           }
        }
        for(int col=n-2; col>=0; col--) {
            int currRow = getQueenRowInCol(board, col, n);
            int nextRow = getQueenRowInCol(board, col+1, n);
            if(currRow > -1) {
                if(nextRow > -1) {
                    if (isClashing(currRow, nextRow)) {
                        for(int i=0;i<n;i++) {
                            board[i][col] = 0;
                        }
                    } else {
                        dp[currRow][col] = dp[nextRow][col+1];
                    }
                } else {
                    for (int nRow = 0; nRow < n; nRow++) {
                        if (!isClashing(currRow, nRow)) {
                            dp[currRow][col] += dp[nRow][col + 1];
                        }
                    }
                }
            } else {


                for (int row = 0; row < n; row++) {
                    if (nextRow > -1) {
                        if (isClashing(row, nextRow)) {
                            dp[row][col] = 0;
                        } else {
                            dp[row][col] = dp[nextRow][col + 1];
                        }
                    } else {
                        for (int nRow = 0; nRow < n; nRow++) {
                            if (!isClashing(row, nRow)) {
                                dp[row][col] += dp[nRow][col + 1];
                            }
                        }
                    }
                }
            }
        }

        long ans = 0;
        for(int i=0; i<n; i++) {
            ans += dp[i][0];
        }
        System.out.println(ans);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String line = reader.readLine();
            if(line==null) break;
            if(line.trim().isEmpty()) continue;
            int n = line.length();
            int[][] board = new int[n][n];
            for(int i=0; i<n; i++) {
                if(line.charAt(i) != '?') {
                    int r = getRowNum(line.charAt(i));
                    board[r][i] = 1;
                }
            }
            solve(board, n);
        }
    }
}
