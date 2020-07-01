import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class AnotherNQueen_MBA {

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


    private static boolean isBlocked(int i, int j) {
        return false;
    }
    private static void dfs(int currCol, boolean[][] board, Set<boolean[][]> boards, int n) {
        if (currCol == n) {
            boolean[][] boardCopy = new boolean[n][n];
            for(int i=0; i<n; i++) {
                for(int j=0;j<n;j++) {
                    boardCopy[i][j] = board[i][j];
                }
            }
            boards.add(boardCopy);
        } else {
            for(int r=0; r<n; r++) {
                if (isBlocked(r, currCol) && isSafe(currCol, r, board, n)) {
                    board[r][currCol] = true;
                    dfs(currCol+1, board, boards, n);
                    board[r][currCol] = false;
                }
            }
        }
    }

    private static Set<boolean[][]> nq(int n) {
        boolean[][] board = new boolean[n][n];
        Set<boolean[][]> s = new HashSet<>();
        dfs(0, board, s, n);
//        System.out.println(s.size());
        return s;
    }

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            int n = Integer.parseInt(reader.readLine());
            for(int i=0;i<n;i++) {
                String line = reader.readLine();
            }
        }
    }
}
