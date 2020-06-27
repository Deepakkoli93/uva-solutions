import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TurnTheLightsOff {

    private static boolean[] getToggleArr(int n) {
        boolean[] arr = new boolean[10];
        for(int i=0;i<10;i++) {
            int x = (n >> i) & 1;
            if (x == 0) {
                arr[i] = false;
            } else {
                arr[i] = true;
            }
        }
        return arr;
    }

    private static void toggleCell(char[][] mat, int i, int j) {
        if (mat[i][j] == '0') {
            mat[i][j] = '#';
        } else {
            mat[i][j] = '0';
        }
    }

    private static void toggle(char[][] mat, int i, int j) {
        toggleCell(mat, i, j);
        if (i-1 >= 0) toggleCell(mat, i-1, j);
        if (j-1 >= 0) toggleCell(mat, i, j-1);
        if (i+1 < 10) toggleCell(mat, i+1, j);
        if (j+1 < 10) toggleCell(mat, i, j+1);
    }

    private static int solve(char[][] mat) {
        int ans = Integer.MAX_VALUE;
        for(int i=0; i<1024; i++) {
            int moves = 0;
            boolean[] topRow = getToggleArr(i);
            char[][] curr = new char[10][10];
            for(int j=0;j<10;j++) {
                for(int k=0;k<10;k++) {
                    curr[j][k] = mat[j][k];
                }
            }
            for(int j=0;j<10;j++) {
                if (topRow[j]) {
                    moves++;
                    toggle(curr, 0, j);
                }
            }
            for(int r=1;r<10;r++) {
                for(int c=0; c<10; c++) {
                    if (curr[r-1][c] == '0') {
                        moves++;
                        toggle(curr, r, c);
                    }
                }
            }
            for(int c=0;c<10;c++) {
                if (curr[9][c] == '0') {
                    moves = Integer.MAX_VALUE;
                }
            }
            ans = Math.min(ans, moves);
        }
        if (ans > 100) {
            return -1;
        } else {
            return ans;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String line = reader.readLine();
            if (line.equals("end")) break;
            char[][] mat = new char[10][10];
            String name = line;

            for(int i=0;i<10;i++) {
                String row = reader.readLine();
            //    System.out.println(row);
                for(int j=0;j<10;j++) {
                    if (row.charAt(j) == 'O') {
                        mat[i][j] = '0';
                    } else {
                        mat[i][j] = '#';
                    }
                }
            }
            int ans = solve(mat);
            System.out.println(name + " " + ans);
        }
    }
}
