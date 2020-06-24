import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StickerCollectorRobot {

    private static int[][] moves = {
            {-1, 0},
            {0, 1},
            {1, 0},
            {0, -1}
    };

    private static boolean isValid (char[][] grid, int n, int m, int x, int y) {
        return x>=0 && y>=0 && x < n && y < m && grid[x][y] != '#';
    }

    private static int solve(char[][] grid, int n, int m, int s, int x, int y, int face, String ins) {
        int ans = 0;
        for(int i=0; i<s; i++) {
            char in = ins.charAt(i);
            if (in == 'D') {
                face = (face + 1) % 4;
            } else if (in == 'E') {
                face = (face + 3) % 4;
            } else {
                int newX = x + moves[face][0];
                int newY = y + moves[face][1];
                if(isValid(grid, n, m, newX, newY)) {
                    if(grid[newX][newY] == '*'){
                        ans++;
                        grid[newX][newY] = '.';
                    }
                    x = newX;
                    y = newY;
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String[] arr = reader.readLine().split("\\s+");
            int n = Integer.parseInt(arr[0]);
            int m = Integer.parseInt(arr[1]);
            int s = Integer.parseInt(arr[2]);
            if (n==0 && m==0 && s==0) break;

            char[][] grid = new char[n][m];
            int x = -1;
            int y = -1;
            int face = -1;
            for(int i=0; i<n; i++) {
                String line = reader.readLine();
                for(int j=0; j<m; j++) {
                    char c = line.charAt(j);
                    if(c == 'N' || c == 'S' || c == 'L' || c == 'O') {
                        x = i;
                        y = j;
                        if (c=='N') face = 0;
                        else if (c=='L') face = 1;
                        else if (c=='S') face = 2;
                        else face = 3;

                        grid[i][j] = '.';
                    } else {
                        grid[i][j] = line.charAt(j);
                    }
                }
            }
            String ins = reader.readLine();
            int ans = solve(grid, n, m, s, x, y, face, ins);
            System.out.println(ans);
        }
    }
}
