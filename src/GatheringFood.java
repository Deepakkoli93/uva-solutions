import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class GatheringFood {

    private static int p = 20437;

    private static boolean isValid(char[][]grid, int i, int j, int n, char lookingFor) {
        return i>=0 && j>=0 && i<n && j<n && (grid[i][j] == '.' || grid[i][j] == lookingFor);
    }

    private static int[][] moves = new int[][]{
            {-1, 0},
            {0, -1},
            {1, 0},
            {0, 1}
    };

    private static int[] bfs(char[][] grid, int i, int j, int n, char lookingFor, int numLevel, long numPath) {
        Queue<int[]> q = new ArrayDeque<>();
        int[][] level = new int[n][n];
        for(int[]row: level) Arrays.fill(row, -1);
        int[][] paths = new int[n][n];
        q.add(new int[]{i, j, numLevel});
        paths[i][j] = 1;//numPath;
        level[i][j] = numLevel;
        boolean found = false;
        int targetX = -1;
        int targetY = -1;
        while(!q.isEmpty()) {
            int[] curr = q.poll();
            int currentLevel = level[curr[0]][curr[1]];
            if(grid[curr[0]][curr[1]] == lookingFor) {
                grid[curr[0]][curr[1]] = '.';
                found = true;
                targetX = curr[0];
                targetY = curr[1];
//                break;
            } else {

                for (int m = 0; m < 4; m++) {
                    int neiX = curr[0] + moves[m][0];
                    int neiY = curr[1] + moves[m][1];
                    if (isValid(grid, neiX, neiY, n, lookingFor)) {
                        if (level[neiX][neiY] == -1) {
                            level[neiX][neiY] = 1 + currentLevel;
                            q.add(new int[]{neiX, neiY});
                            paths[neiX][neiY] += paths[curr[0]][curr[1]] % p;
                        } else if (level[neiX][neiY] == 1 + currentLevel) {
                            paths[neiX][neiY] += paths[curr[0]][curr[1]] % p;
                        }
                    }
                }
            }
        }
        if(found) {
            return new int[]{1, targetX, targetY, level[targetX][targetY], paths[targetX][targetY]};
        } else {
            return new int[]{-1};
        }
    }

    private static int[] findA(char[][]grid, int n) {
        for(int i=0; i<n;i++) {
            for(int j=0;j<n;j++) {
                if(grid[i][j] == 'A') {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    private static void solve(char[][] grid, int n, int alphaNum, int t) {


        int startX = -1; int startY = -1; long numPath = 1; int numLevel = 0;
        boolean isImpossible = false;
        for(int a=0; a<alphaNum; a++) {

            if(a==0) {
                int[] start = findA(grid, n);
                startX = start[0]; startY = start[1];
            }
            char lookingFor = (char)(a+65);
            int[] result = bfs(grid, startX, startY, n, lookingFor, numLevel, numPath);
            if(result[0] == -1) {
                isImpossible = true;
                break;
            } else {
                startX = result[1];
                startY = result[2];
                numLevel = result[3];
                numPath = (numPath * (result[4] % p)) % p;
            }
        }
        if(isImpossible) {
            System.out.println("Case " + t + ": Impossible");
        } else {
            System.out.println("Case " + t + ": " + numLevel + " " + numPath);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = 0;
        while(true) {
            int n = Integer.parseInt(reader.readLine());
            if(n==0) break;
            t++;
            int alphaNum = 0;
            char[][] grid = new char[n][n];
            for(int i=0;i<n;i++) {
                String line = reader.readLine();
                for(int j=0;j<n;j++) {
                    grid[i][j] = line.charAt(j);
                    if ((int) grid[i][j] >= 65 && (int)grid[i][j] <= 90) {
                        alphaNum++;
                    }
                }
            }
            solve(grid, n, alphaNum, t);
        }
    }
}
