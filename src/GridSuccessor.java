import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class GridSuccessor {

    private static int getHash(int[][] grid) {
        int hash = 0;
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                hash = hash << 1;
                hash = hash | grid[i][j];
            }
        }
        return hash;
    }

    private static int getSumMod(int i, int j, int[][] grid) {
        int sum = 0;
        if (i-1 >= 0) {
            sum += grid[i-1][j];
        }
        if (j+1 < 3) {
            sum += grid[i][j+1];
        }
        if (i+1 < 3) {
            sum += grid[i+1][j];
        }
        if (j-1 >= 0) {
            sum += grid[i][j-1];
        }
        return sum % 2;
    }

    private static void printGrid(int[][] grid) {
        System.out.println("#############");
        for(int i=0; i<3; i++) {
            System.out.println(grid[i][0]+" "+grid[i][1]+" "+grid[i][2]);
        }
        System.out.println("#############");
    }

    private static int solve(int[][] grid) {
        boolean[] markers = new boolean[512];
        Arrays.fill(markers, false);
        int count = 0;
        while(true) {
//            printGrid(grid);
            if(markers[getHash(grid)]) {
                return count-2;
            }
            markers[getHash(grid)] = true;

            int[][] newGrid = new int[3][3];
            for(int i=0;i<3;i++) {
                for(int j=0;j<3;j++) {
                    newGrid[i][j] = getSumMod(i, j, grid);
                }
            }
            grid = newGrid;
            count++;

        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());
        while(n-- > 0) {
            reader.readLine();
            int[][] grid = new int[3][3];
            for(int i=0; i< 3; i++) {
                String s = reader.readLine();
                grid[i][0] = Integer.parseInt(String.valueOf(s.charAt(0)));
                grid[i][1] = Integer.parseInt(String.valueOf(s.charAt(1)));
                grid[i][2] = Integer.parseInt(String.valueOf(s.charAt(2)));
            }
            System.out.println(solve(grid));
        }
    }
}
