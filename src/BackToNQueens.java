import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BackToNQueens {

    private static boolean isSafe(int col, int row, boolean[][] board) {
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
        while(r < 8 && c >= 0) {
            if (board[r][c]) return false;
            r++;
            c--;
        }

        return true;
    }

    private static void dfs(int currCol, boolean[][] board, Set<boolean[][]> boards) {
        if (currCol == 8) {
            boolean[][] boardCopy = new boolean[8][8];
            for(int i=0; i<8; i++) {
                for(int j=0;j<8;j++) {
                    boardCopy[i][j] = board[i][j];
                }
            }
            boards.add(boardCopy);
        } else {
            for(int r=0; r<8; r++) {
                if (isSafe(currCol, r, board)) {
                    board[r][currCol] = true;
                    dfs(currCol+1, board, boards);
                    board[r][currCol] = false;
                }
            }
        }
    }

    private static Set<boolean[][]> nq() {
        boolean[][] board = new boolean[8][8];
        Set<boolean[][]> s = new HashSet<>();
        dfs(0, board, s);
//        System.out.println(s.size());
        return s;
    }

    private static int getRow(boolean[][] board, int col) {
        for(int i=0;i<8;i++) {
            if (board[i][col]) {
                return i;
            }
        }
        return -1;
    }

    private static int diff(Set<boolean[][]> s, boolean[][] board) {
        int ans = Integer.MAX_VALUE;
        for(boolean[][] b: s) {
            int moves = 0;
            for(int i=0;i<8;i++) {
                if (getRow(b, i) != getRow(board, i)) {
                    moves++;
                }
            }
            ans = Math.min(ans, moves);
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {

        Set<boolean[][]> s = nq();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int count = 0;
        while(true) {
            String line = reader.readLine();
            if (line == null || line.isEmpty()) break;
            count++;
            String[] arr  = line.split("\\s+");
            boolean[][] board = new boolean[8][8];
            for(int i=0; i<8; i++) {
                board[Integer.parseInt(arr[i]) - 1][i] = true;
            }
            int ans = diff(s, board);
            System.out.println("Case " + count + ": " + ans);
        }
    }
}
