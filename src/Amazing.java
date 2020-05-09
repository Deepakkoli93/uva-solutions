import java.util.Scanner;

public class Amazing {
// 1 - close. 0 - open
    private static String[] turns = {"up", "right", "down", "left"};

    private static String pad(int i) {
        if (i>=100) return String.valueOf(i);
        if (i>=10) return " "+i;
        return "  "+i;
    }
    private static boolean canMoveRight(int[][] board, int rows, int col, int i, int j, int face) {
        if (face == 0) {
            j++;
            return j>=0 && j<col && board[i][j] == 0;
        }
        else if (face == 1) {
            i++;
            return i>=0 && i<rows && board[i][j] == 0;
        }
        else if (face == 2) {
            j--;
            return j>=0 && j<col && board[i][j] == 0;
        }
        else if (face == 3) {
            i--;
            return i>=0 && i<rows && board[i][j] == 0;
        }
        return false;
    }


    private static int[] moveRight(int i, int j, int face) {
        if (face == 0) {
            j++;
        }
        else if (face == 1) {
            i++;
        }
        else if (face == 2) {
            j--;
        }
        else if (face == 3) {
            i--;
        }
        int[] ans = new int[]{i, j};
        return ans;
    }

    private static void solve(int[][] board, int rows, int cols) {
        int [][] marks = new int[rows][cols];
        for(int i=0;i<rows;i++) {
            for(int j=0;j<cols;j++) {
                marks[i][j] = 0;
            }
        }
        int i = rows-1;
        int j = 0;
        int face = 1;
        while(true) {
            marks[i][j]++;
            if (canMoveRight(board, rows, cols, i, j, face)) {
                int[] ans = moveRight(i, j, face);
                i = ans[0];
                j = ans[1];
                face = (face+1) %4;
            } else if (canMoveRight(board, rows, cols, i, j, (face+3) % 4)) { //move straight
                int[] ans = moveRight(i, j, (face+3) % 4);
                i = ans[0];
                j = ans[1];
            } else if (canMoveRight(board, rows, cols, i, j, (face+2) % 4)) { //move left
                int[] ans = moveRight(i, j, (face+2) % 4);
                i = ans[0];
                j = ans[1];
                face = (face + 3) % 4;
            } else if (canMoveRight(board, rows, cols, i, j, (face+1) % 4)) { //move back
                int[] ans = moveRight(i, j, (face+1) % 4);
                i = ans[0];
                j = ans[1];
                face = (face + 2) % 4;
            } else {
                break;
            }

            if (i==rows-1 && j==0) break;

        }

        int zero=0;
        int one=0;
        int two=0;
        int three=0;
        int four = 0;
        for(i=0;i<rows;i++) {
            for(j=0;j<cols;j++) {
                if (board[i][j] == 0) {
                    int num = marks[i][j];
                    if (num == 0) zero++;
                    else if (num == 1) one++;
                    else if (num == 2) two++;
                    else if (num == 3) three++;
                    else if (num == 4) four++;
                }
            }
        }
        System.out.println(pad(zero) + pad(one) + pad(two) + pad(three) + pad(four));

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String[] arr = scanner.nextLine().split("\\s+");
            int rows = Integer.parseInt(arr[0]);
            int cols = Integer.parseInt(arr[1]);
            if (rows == 0 && cols == 0)break;
            int[][] board = new int[rows][cols];
            for(int i=0; i<rows; i++) {
                String row = scanner.nextLine();
                for(int j=0; j<cols; j++) {
                    board[i][j] = Integer.parseInt(row.substring(j, j+1));
                }
            }
            solve(board, rows, cols);
        }
    }
}
