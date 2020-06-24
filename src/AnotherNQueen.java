import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AnotherNQueen {

    private static boolean isSafe(int col, int row, boolean[][] board, int n) {
        // row attack
        int i = col-1;
        while(i >= 0) {
            if (board[row][i]) {
                return false;
            }
            i--;
        }

        // diagonal attack;
        // upper left
        int r = row-1;
        int c = col-1;
        while(r >= 0 && c >= 0) {
            if (board[r][c]) return false;
            r--;
            c--;
        }

        // bootom left
        r = row+1;
        c = col-1;
        while(r < n && c >= 0) {
            if (board[r][c]) return false;
            r++;
            c--;
        }

        return true;
    }

    private static void place(int i, int j, int n, int[][] placed) {
        placed[i][j]++;
        for (int k = i + 1; k < n; k++) {
            placed[k][j]++;
        }

        for(int k=i-1; k>=0; k--) {
            placed[k][j]++;
        }

        // cols
        for(int k=j+1; k<n; k++) {
            placed[i][k]++;
        }
        for(int k=j-1; k>=0; k--) {
            placed[i][k]++;
        }

        //upper left
        int r = i-1;
        int c = j-1;
        while(r>=0 && c>=0) {
            placed[r][c]++;
            r--;
            c--;
        }
        // upper right
        r = i-1;
        c = j+1;
        while(r>=0 && c<n) {
            placed[r][c]++;
            r--;
            c++;
        }

        //lower right
        r = i+1;
        c = j+1;
        while(r<n && c<n) {
            placed[r][c]++;
            r++;
            c++;
        }

        // lower left
        r = i+1;
        c = j-1;
        while(r<n && c>=0) {
            placed[r][c]++;
            r++;
            c--;
        }
    }


    private static void unPlace(int i, int j, int n, int[][] placed) {
        placed[i][j]--;
        for(int k=i+1; k<n; k++) {
            placed[k][j]--;
        }
        for(int k=i-1; k>=0; k--) {
            placed[k][j]--;
        }

        // cols
        for(int k=j+1; k<n; k++) {
            placed[i][k]--;
        }
        for(int k=j-1; k>=0; k--) {
            placed[i][k]--;
        }

        //upper left
        int r = i-1;
        int c = j-1;
        while(r>=0 && c>=0) {
            placed[r][c]--;
            r--;
            c--;
        }
        // upper right
        r = i-1;
        c = j+1;
        while(r>=0 && c<n) {
            placed[r][c]--;
            r--;
            c++;
        }

        //lower right
        r = i+1;
        c = j+1;
        while(r<n && c<n) {
            placed[r][c]--;
            r++;
            c++;
        }

        // lower left
        r = i+1;
        c = j-1;
        while(r<n && c>=0) {
            placed[r][c]--;
            r++;
            c--;
        }
    }


    private static int dfs(int currCol, boolean[][] board, int n, boolean[][] blocked, int rowsTaken, int dDownTaken, int dUpTaken) {
        if (currCol == n) {
            return 1;
        } else {
            int ans = 0;
            for(int r=0; r<n; r++) {
                if (!blocked[r][currCol] && ((rowsTaken & (1 << r))==0) && ((dDownTaken & (1 << (r + n-currCol-1))) == 0) && ((dUpTaken & (1 << (r+currCol))) == 0)  ) {
                    ans += dfs(currCol+1, board, n, blocked, (rowsTaken | (1 << r)), (dDownTaken | (1 << (r + n-currCol-1))), (dUpTaken | (1 << (r+currCol))));
                }
            }
            return ans;
        }
    }

    private static int nq(int n, boolean[][] blocked) {
        boolean[][] board = new boolean[n][n];
        return dfs(0, board, n, blocked, 0, 0, 0);
    }

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int count = 0;
        StringBuilder str = new StringBuilder();
        while(true) {
            int n = Integer.parseInt(reader.readLine());
            if(n==0) break;
            count++;
            boolean[][] blocked = new boolean[n][n];
            for(int i=0;i<n;i++) {
                String line = reader.readLine();
                for(int j=0;j<n;j++) {
                    if (line.charAt(j) == '*') {
                        blocked[i][j] = true;
                    }
                }
            }
            int ans = nq(n, blocked);
            str.append("Case " + count + ": " + ans + '\n');
        }
        System.out.print(str);
    }
}