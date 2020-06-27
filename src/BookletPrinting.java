import java.util.Arrays;
import java.util.Scanner;

public class BookletPrinting {
    private static String getVal(int[][] ans, int i, int j, int n) {
        if (ans[i][j] > n) return "Blank";
        else return String.valueOf(ans[i][j]);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            int n = Integer.parseInt(scanner.nextLine());
            if(n==0) break;
            int sheets = (int) Math.ceil(n/4.0);
            int[][] ans = new int[sheets+1][4];  // front left, fonrt right, back left, back right
            for(int i=0;i<sheets+1;i++){
                Arrays.fill(ans[i], 0);
            }
            int page = 1;
            for(int i=1; i<=sheets; i++) {
                ans[i][1] = page++;
                ans[i][2] = page++;
            }
            for(int i=sheets; i>=1; i--) {
                ans[i][3] = page++;
                ans[i][0] = page++;
            }
            System.out.println("Printing order for " + n + " pages:");
            for(int i=1;i<=sheets;i++) {
                if (!(getVal(ans, i, 0, n).equalsIgnoreCase("blank") && getVal(ans, i, 1, n).equalsIgnoreCase("blank")))
                System.out.println("Sheet " + i + ", front: " + getVal(ans, i, 0, n) + ", " + getVal(ans, i, 1, n));


                if (!(getVal(ans, i, 2, n).equalsIgnoreCase("blank") && getVal(ans, i, 3, n).equalsIgnoreCase("blank")))
                System.out.println("Sheet " + i + ", back : " + getVal(ans, i, 2, n) + ", " + getVal(ans, i, 3, n));
            }
        }
    }
}
