import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class MallMania {

    private static int[][] moves = {
            {-1, 0},
            {0, 1},
            {1, 0},
            {0, -1}
    };

    private static boolean isValid(int r, int c) {
        return r>=0 && r<2001 && c>=0 && c<2001;
    }

    private static void bfs(char[][] grid, int[][] start, int n1) {

        Queue<int[]> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[2001][2001];
        for(boolean[] row: visited) Arrays.fill(row, false);
        int[][] levels = new int[2001][2001];
        for(int[] row: levels) Arrays.fill(row, -1);
        for(int i=0; i<n1; i++) {
            q.add(new int[]{start[i][0], start[i][1]});
            visited[start[i][0]][start[i][1]] = true;
            levels[start[i][0]][start[i][1]] = 0;
        }
        int ans = -1;
        while(!q.isEmpty()) {
            int[] curr = q.poll();
            int level = levels[curr[0]][curr[1]];
            if(grid[curr[0]][curr[1]] == '2') {
                ans = level;
                break;
            }

            for(int i=0; i<4; i++) {
                int r = curr[0] + moves[i][0];
                int c = curr[1] + moves[i][1];
                if (isValid(r, c) && !visited[r][c]) {
                    visited[r][c] = true;
                    levels[r][c] = level+1;
                    q.add(new int[]{r, c});
                }
            }
        }

        System.out.println(ans);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            int n1 = Integer.parseInt(reader.readLine());
            if(n1==0) break;
            char[][] grid = new char[2001][2001];
            for(char[] row: grid) Arrays.fill(row, '.');
            int[][] start = new int[n1][2];
            int count = 0;
            int ind = 0;
            while(count < 2 * n1) {
                String[] arr = reader.readLine().split("\\s+");
                count += arr.length;
                for(int i=0; i<arr.length - 1; i+=2) {
                    int x = Integer.parseInt(arr[i]);
                    int y = Integer.parseInt(arr[i+1]);
                    start[ind][0] = x;
                    start[ind][1] = y;
                    ind++;
                    grid[x][y] = '1';
                }
            }

            int n2 = Integer.parseInt(reader.readLine());
            int[][] end = new int[n2][2];
            count = 0;
            ind = 0;
            while(count < 2 * n2) {
                String[] arr = reader.readLine().split("\\s+");
                count += arr.length;
                for(int i=0; i<arr.length-1; i+=2) {
                    int x = Integer.parseInt(arr[i]);
                    int y = Integer.parseInt(arr[i+1]);
                    end[ind][0] = x;
                    end[ind][1] = y;
                    ind++;
                    grid[x][y] = '2';
                }
            }
            bfs(grid, start, n1);

        }
    }
}
