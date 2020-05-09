import java.util.Scanner;

public class MineSweeper {
    private static void foo(int r, int c, int[][] arr, int n) {
        if (n>1) {
            System.out.println();
        }
        for(int row=0; row < r; row++) {
            for (int col = 0; col < c; col++) {
                if (arr[row][col] == -1) {
                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < 2; j++) {
                            int ii = row + i;
                            int jj = col + j;
                            if (!(ii ==row && jj== col) && ii >=0 && ii < r && jj>=0 && jj<c && arr[ii][jj] != -1) {
                                arr[ii][jj]++;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Field #" + n + ":");
//        try {
//            output.write("Field #" + n + ":" + '\n');
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        for(int row=0; row < r; row++) {
            StringBuilder s = new StringBuilder();
            for (int col = 0; col < c; col++) {
                if (arr[row][col] == -1) {
                    s.append('*');
                } else {
                    s.append(arr[row][col]);
                }

            }
//            try {
//                output.write(s.toString()+'\n');
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            System.out.println(s);
        }
//        System.out.println();
//        try {
//            output.write('\n');
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    public static void main(String[] args) {
//        BufferedWriter output = null;
//        try {
//            File file = new File("example.txt");
//            output = new BufferedWriter(new FileWriter(file));
//        } catch ( IOException e ) {
//            e.printStackTrace();
//        } finally {
//
//        }
        Scanner scanner = new Scanner(System.in);
        int n = 0;
        while(true) {
            String ss = scanner.nextLine();
            String[] arr = ss.split(" ");
            int r=Integer.parseInt(arr[0]);
            int c = Integer.parseInt(arr[1]);
            if (r==0 && c==0) {
                break;
            }
            n++;
            int[][] mat = new int[r][c];
            for(int i=0; i< r; i++) {
                char[] row = scanner.nextLine().toCharArray();
                for(int j=0; j<c; j++) {
                    if (row[j] == '*') {
                        mat[i][j] = -1;
                    } else {
                        mat[i][j] = 0;
                    }
                }
            }
            foo(r, c, mat, n);
//            System.out.println();
        }
//        try {
//            output.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
