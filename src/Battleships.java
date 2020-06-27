import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Battleships {

    private static int[][] moves = new int[][] {
            {-1, 0},
            {0, 1},
            {1, 0},
            {0, -1}
    };

    private static boolean isValid(int i, int j, int n) {
        return i>=0 && j>=0 && i<n && j<n;
    }

    private static void dfs(char[][] grid, int n, int currX, int currY) {
        for(int z=0; z<4; z++) {
            int newX = currX + moves[z][0];
            int newY = currY + moves[z][1];
            if(isValid(newX, newY, n) && grid[newX][newY] != '.') {
                grid[newX][newY] = '.';
                dfs(grid, n, newX, newY);
            }
        }
    }

    private static int solve(char[][] grid, int n) {
        int ans = 0;
        for(int i=0; i<n; i++) {
            for(int j=0;j<n;j++) {
                if(grid[i][j] == 'x') {
                    ans++;
                    grid[i][j] = '.';
                    dfs(grid, n, i, j);
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int tt = Integer.parseInt(reader.readLine());
        for(int t=1; t<=tt; t++) {
            int n = Integer.parseInt(reader.readLine());
            char[][] grid = new char[n][n];
            for(int i=0; i<n;i++) {
                String line = reader.readLine();
                for(int j=0; j<n; j++) {
                    grid[i][j] = line.charAt(j);
                }
            }
            int ans = solve(grid, n);
            System.out.println("Case " + t + ": " + ans);
        }
    }
}
