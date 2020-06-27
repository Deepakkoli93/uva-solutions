import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Continents {


    private static int[][] moves = new int[][] {
            {-1, 0},
            {0, 1},
            {1, 0},
            {0, -1}
    };

    private static boolean isValid(int i, int j, int m, int n) {
        return i>=0 && j>=0 && i<m && j<n;
    }

    private static int dfs(char[][] grid, int m, int n, int x, int y, int currX, int currY, char landChar) {
        int ans = 1;
        if(x == currX && y == currY) ans = Integer.MIN_VALUE;
        for(int z=0; z<4; z++) {
            int newX = currX + moves[z][0];
            int newY = (currY + moves[z][1] + n)%n;
            if(isValid(newX, newY, m, n) && grid[newX][newY] == landChar) {
                grid[newX][newY] = '1';
//                System.out.println(newX + " " + newY);
                ans += dfs(grid, m, n, x, y, newX, newY, landChar);
            }
        }
        return ans;
    }

    private static int solve(char[][] grid, int m, int n, int x, int y) {
        char landChar = grid[x][y];
        int ans = 0;
        for(int i=0; i<m; i++) {
            for(int j=0; j<n; j++) {
                if(grid[i][j] == landChar) {
                    grid[i][j] = '1';
                    ans = Math.max(ans, dfs(grid, m, n, x, y, i, j, landChar));
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String line = reader.readLine();
            if(line==null || line.isEmpty()) break;
            String[] arr = line.split("\\s+");
            int m = Integer.parseInt(arr[0]);
            int n = Integer.parseInt(arr[1]);
            char[][] grid = new char[m][n];
            for(int i=0; i<m; i++) {
                String line1 = reader.readLine();
                for(int j=0; j<n; j++) {
                    grid[i][j] = line1.charAt(j);
                }
            }
            arr = reader.readLine().split("\\s+");
            int x = Integer.parseInt(arr[0]);
            int y = Integer.parseInt(arr[1]);
            int ans = solve(grid, m, n, x, y);
            System.out.println(ans);
            reader.readLine();
        }
    }
}
