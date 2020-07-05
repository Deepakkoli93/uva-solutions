import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class EquiDivisions {

    private static int[][] moves = new int[][] {
            {-1, 0},
            {0, 1},
            {1, 0},
            {0, -1}
    };

    private static boolean isValid(int i, int j, int n, int [][] grid) {
        return i>=0 && j>=0 && i<n && j<n && grid[i][j] != -1;
    }

    private static int dfs(int[][] grid, int n, int currX, int currY) {
        int ans = 1;
        int prevVal = grid[currX][currY];
        grid[currX][currY] = -1;
        for(int z=0; z<4; z++) {
            int newX = currX + moves[z][0];
            int newY = currY + moves[z][1];
            if(isValid(newX, newY, n, grid) && grid[newX][newY] == prevVal) {
                ans += dfs(grid, n, newX, newY);
            }
        }
        return ans;
    }

    private static boolean solve(int[][] grid, int n) {
        boolean[] seen = new boolean[n];
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {




                if(grid[i][j] != -1) {
                    if(seen[grid[i][j]]) {
//                        System.out.println("i="+i+"j="+j);
                        return false;
                    } else {
                        seen[grid[i][j]] = true;
                        int ans = dfs(grid, n, i, j);
                        if(ans != n) return false;
                    }
                }


//                System.out.println("After i = " + i + " j = " + j);
//                for(int ii=0; ii<n; ii++) {
//                    StringBuilder str = new StringBuilder();
//                    for(int jj=0; jj<n; jj++) {
//                        str.append(grid[ii][jj]).append(" ");
//                    }
//                    System.out.println(str);
//                }
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            int n = Integer.parseInt(reader.readLine());
            if (n==0) break;
            int[][] grid = new int[n][n];
            for(int[] row: grid) Arrays.fill(row, n-1);
            for(int i=0; i<n-1; i++) {
                String[] arr = reader.readLine().split("\\s+");
                int j = 0;
                int len = arr.length;
                while(j <= len-2) {
                    grid[Integer.parseInt(arr[j]) - 1][Integer.parseInt(arr[j+1]) - 1] = i;
                    j += 2;
                }
            }
//            for(int i=0; i<n; i++) {
//                StringBuilder str = new StringBuilder();
//                for(int j=0; j<n; j++) {
//                    str.append(grid[i][j]).append(" ");
//                }
//                System.out.println(str);
//            }
            boolean ans = solve(grid, n);
            if(ans) {
                System.out.println("good");
            } else {
                System.out.println("wrong");
            }
        }
    }
}
