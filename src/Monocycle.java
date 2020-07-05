import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class Monocycle {

    // 5 bit ro, 5 bit col, 3 bit color, 2 bit dir


    private static int getRow(int bitmask) {
        int ans = 0;
        for(int i=0; i<5; i++) {
            if((bitmask & (1 << (i+10))) != 0) {
                ans += Math.pow(2, i);
            }
        }
        return ans;
    }

    private static int getCol(int bitmask) {
        int ans = 0;
        for(int i=0; i<5; i++) {
            if((bitmask & (1 << (i+5))) != 0) {
                ans += Math.pow(2, i);
            }
        }
        return ans;
    }

    private static int getColor(int bitmask) {
        int ans = 0;
        for(int i=0; i<3; i++) {
            if((bitmask & (1 << (i+2))) != 0) {
                ans += Math.pow(2, i);
            }
        }
        return ans;
    }

    private static int getDir(int bitmask) {
        int ans = 0;
        for(int i=0; i<2; i++) {
            if((bitmask & (1 << i)) != 0) {
                ans += Math.pow(2, i);
            }
        }
        return ans;
    }

    private static int getBitmask(int r, int c, int color, int dir) {
        int bitmask = r;
        bitmask = bitmask << 5;

        bitmask = bitmask | c;
        bitmask = bitmask << 3;

        bitmask = bitmask | color;
        bitmask = bitmask << 2;

        bitmask = bitmask | dir;
        return bitmask;
    }

    private static boolean isFinalState(int bitmask, int[] end) {
        return getRow(bitmask) == end[0] && getCol(bitmask) == end[1] && getColor(bitmask) == 0;
    }

    private static boolean isValidMove(int bitmask, char[][] grid, int m, int n) {
        int r = getRow(bitmask);
        int c = getCol(bitmask);
        int color = getColor(bitmask);
        int dir = getDir(bitmask);
        return r >=0 && r<m && c >=0 && c<n && grid[r][c] == '.';
    }

    private static int getFwdBitMask(int bitmask) {
        int r = getRow(bitmask);
        int c = getCol(bitmask);
        int color = getColor(bitmask);
        int dir = getDir(bitmask);
        int newColor = (color+1) % 5;
        if (dir == 0) {
            return getBitmask(r-1, c, newColor, dir);
        } else if (dir == 1) {
            return getBitmask(r, c+1, newColor, dir);
        } else if (dir == 2) {
            return getBitmask(r+1, c, newColor, dir);
        } else if (dir == 3) {
            return getBitmask(r, c-1, newColor, dir);
        }
        return -1;
    }

    // colors
    // green = 0, black = 1, red = 2, blue = 3, white = 4
    // north = 0, east = 1, south = 2, west = 3
    private static void bfs(char[][] grid, int rowLen, int colLen, int[] start, int[] end, int t) {
        Queue<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[100000];
        int[] levels = new int[100000]; Arrays.fill(levels, -1);
        int startBitmask = getBitmask(start[0], start[1], 0, 0);
        q.add(startBitmask);
        visited[startBitmask] = true;
        levels[startBitmask] = 0;
        int ans = -1;
        while(!q.isEmpty()) {
            int bitmask = q.poll();
            visited[bitmask] = true;
            if (isFinalState(bitmask, end)) {
                ans = levels[bitmask];
                break;
            }

            int r = getRow(bitmask);
            int c = getCol(bitmask);
            int color = getColor(bitmask);
            int dir = getDir(bitmask);
            int level = levels[bitmask];
//            System.out.println("r = " + r + ", c = " + c);
            // 1
            int fwdBitMask = getFwdBitMask(bitmask);
            if (isValidMove(fwdBitMask, grid, rowLen, colLen) && !visited[fwdBitMask]) {
                visited[fwdBitMask] = true;
                levels[fwdBitMask] = level + 1;
                q.add(fwdBitMask);
            }

            // 2
            int rightBitmask = getBitmask(r, c, color, (dir+1) % 4);
            if (isValidMove(rightBitmask, grid, rowLen, colLen) && !visited[rightBitmask]) {
                visited[rightBitmask] = true;
                levels[rightBitmask] = level + 1;
                q.add(rightBitmask);
            }

            // 3
            int leftBitmask = getBitmask(r, c, color, (dir + 3) % 4);
            if (isValidMove(leftBitmask, grid, rowLen, colLen) && !visited[leftBitmask]) {
                visited[leftBitmask] = true;
                levels[leftBitmask] = level + 1;
                q.add(leftBitmask);
            }
        }

        if (t != 1) System.out.println();
        System.out.println("Case #" + t);
        if (ans == -1) {
            System.out.println("destination not reachable");
        } else {
            System.out.println("minimum time = " + ans + " sec");
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = 0;
        while(true) {
            String[] arr = reader.readLine().split("\\s+");
            int m = Integer.parseInt(arr[0]);
            int n = Integer.parseInt(arr[1]);
            if(m==0 && n==0) break;
            t++;
            char[][] grid = new char[m][n];
            int[] start = new int[]{-1, -1};
            int[] end = new int[]{-1, -1};
            for(int i=0; i<m; i++) {
                String row = reader.readLine();
                for(int j=0; j<n; j++) {
                    grid[i][j] = row.charAt(j);
                    if(grid[i][j] == 'S') {
                        start[0] = i;
                        start[1] = j;
                        grid[i][j] = '.';
                    } else if (grid[i][j] == 'T') {
                        end[0] = i;
                        end[1] = j;
                        grid[i][j] = '.';
                    }

                }
            }
            bfs(grid, m, n, start, end, t);
        }
    }
}
