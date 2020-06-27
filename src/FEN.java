import java.util.Scanner;

public class FEN {

    private static char[][] newboard = new char[8][8];

    private static boolean isValid(int row, int col, char[][] board) {
        return row>=0 && row<8 && col>=0 && col<8 && board[row][col] == '.';
    }
    private static void mark(int row, int col, char[][] board) {
        if (row >=0 && col >=0 && row<8 && col<8 && board[row][col] == '.') {
            newboard[row][col] = 'x';
        }
    }

    private static int solve(char[][] board) {
        int ans = 0;

        for(int i=0;i<8;i++) {
            for(int j=0;j<8;j++) {
                if (board[i][j] != '.') {
                    newboard[i][j] = 'x';
                }
            }
        }


        for(int row=0; row<8; row++) {
            for(int col=0; col<8; col++) {
                if (board[row][col] == 'p') {
                    mark(row + 1, col - 1, board);
                    mark(row + 1, col + 1, board);
                }
                if (board[row][col] == 'P') {
                    mark(row - 1, col - 1, board);
                    mark(row - 1, col + 1, board);
                }
                if (board[row][col] == 'N' || board[row][col] == 'n') {
                    mark(row-1, col + 2, board);
                    mark(row+1, col + 2, board);
                    mark(row-1, col - 2, board);
                    mark(row+1, col - 2, board);

                    mark(row-2, col - 1, board);
                    mark(row-2, col + 1, board);
                    mark(row+2, col - 1, board);
                    mark(row+2, col + 1, board);
                }
                if (board[row][col] == 'B' || board[row][col] == 'b' || board[row][col] == 'Q' || board[row][col] == 'q') {
                    for(int i=1; i<8; i++) {
                        if (isValid(row - i, col + i, board)) {
                            mark(row - i, col + i, board);
                        } else {
                            break;
                        }
                    }

                    for(int i=1; i<8; i++) {
                        if (isValid(row + i, col + i, board)) {
                            mark(row + i, col + i, board);
                        } else {
                            break;
                        }
                    }


                    for(int i=1; i<8; i++) {
                        if (isValid(row + i, col - i, board)) {
                            mark(row + i, col - i, board);
                        } else {
                            break;
                        }
                    }


                    for(int i=1; i<8; i++) {
                        if (isValid(row - i, col - i, board)) {
                            mark(row - i, col - i, board);
                        } else {
                            break;
                        }
                    }

                }
                if (board[row][col] == 'R' || board[row][col] == 'r' || board[row][col] == 'Q' || board[row][col] == 'q') {
                    for(int i=1; i<8; i++) {
                        if (isValid(row - i, col, board)) {
                            mark(row - i, col, board);
                        } else {
                            break;
                        }
                    }

                    for(int i=1; i<8; i++) {
                        if (isValid(row, col + i, board)) {
                            mark(row, col + i, board);
                        } else {
                            break;
                        }
                    }


                    for(int i=1; i<8; i++) {
                        if (isValid(row + i, col, board)) {
                            mark(row + i, col, board);
                        } else {
                            break;
                        }
                    }


                    for(int i=1; i<8; i++) {
                        if (isValid(row, col - i, board)) {
                            mark(row, col - i, board);
                        } else {
                            break;
                        }
                    }
                }
                if (board[row][col] == 'K' || board[row][col] == 'k') {
                    mark(row - 1, col, board);
                    mark(row - 1, col+1, board);
                    mark(row, col+1, board);
                    mark(row+1, col+1, board);
                    mark(row+1, col, board);
                    mark(row+1, col-1, board);
                    mark(row, col-1, board);
                    mark(row-1, col-1, board);
                }
            }
        }


        for(int i=0;i<8;i++) {
            for(int j=0;j<8;j++) {
                if (newboard[i][j] == '.') ans++;
            }
        }

        return ans;

    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 8; j++)
                    newboard[i][j] = '.';

            String[] arr = scanner.nextLine().split("/");
            char[][] board = new char[8][8];
            for (int row = 0; row < 8; row++) {
                int col = 0;
                for (char c : arr[row].toCharArray()) {
                    if (Character.isAlphabetic(c)) {
                        board[row][col++] = c;
                    } else {
                        int n = Integer.parseInt(Character.toString(c));
                        while (n-- > 0) {
                            board[row][col++] = '.';
                        }
                    }
                }
            }
            int ans = solve(board);
            System.out.println(ans);
        }
    }


}
