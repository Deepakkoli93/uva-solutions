import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class WalkingOnAGrid {

    // came from from 0 - > top;  1 -> left;  2 -> right


    private static boolean isValid(int i, int j, int n) {
        return i>=0 && j>=0 && i<n && j<n;
    }

    private static long max3(long i, long j, long k) {
        return Math.max(i, Math.max(j, k));
    }

    private static long dfs(int[][] grid, int i, int j, int n, int k, int negSelected, int from, long sum, long[][][][] memo) {
        long ans = 0;
        int originalNegSelected = negSelected;
        if (memo[i][j][from][negSelected] != -1) {
//            System.out.println(i + " " + j + " " + from + " " + negSelected + " " + memo[i][j][from][negSelected]);
            return memo[i][j][from][negSelected];
        } else if (grid[i][j] < 0 && negSelected >= k) {
            ans = Integer.MIN_VALUE;
        } else if (i==n-1 && j==n-1){
            ans = grid[i][j];
        } else if (from == 0){
            if(grid[i][j] < 0) {
                negSelected = negSelected+1;
            }
            long ans1 = Integer.MIN_VALUE;
            long ans2 = Integer.MIN_VALUE;
            long ans3 = Integer.MIN_VALUE;
            if (isValid(i+1, j, n)) {
                ans1 = dfs(grid, i+1, j, n, k, negSelected, 0, sum + grid[i][j], memo);
            }
            if (isValid(i, j+1, n)) {
                ans2 =  dfs(grid, i, j+1, n, k, negSelected, 1, sum + grid[i][j], memo);
            }
            if (isValid(i, j-1, n)) {
                ans3 = dfs(grid, i, j-1, n, k, negSelected, 2, sum + grid[i][j], memo);
            }
            ans = max3(ans1, ans2, ans3);
            if(ans != Integer.MIN_VALUE) {
                ans += grid[i][j];
            }
        } else if (from == 1) {
            if(grid[i][j] < 0) {
                negSelected = negSelected+1;
            }
            long ans1 = Integer.MIN_VALUE;
            long ans2 = Integer.MIN_VALUE;
            if (isValid(i+1, j, n)) {
                ans1 = dfs(grid, i+1, j, n, k, negSelected, 0, sum + grid[i][j], memo);
            }
            if (isValid(i, j+1, n)) {
                ans2 = dfs(grid, i, j+1, n, k, negSelected, 1, sum + grid[i][j], memo);
            }
            ans = Math.max(ans1, ans2);
            if(ans != Integer.MIN_VALUE) {
                ans += grid[i][j];
            }
        } else {
            if(grid[i][j] < 0) {
                negSelected = negSelected+1;
            }
            long ans1 = Integer.MIN_VALUE;
            long ans2 = Integer.MIN_VALUE;
            if (isValid(i+1, j, n)) {
                ans1 = dfs(grid, i+1, j, n, k, negSelected, 0, sum + grid[i][j], memo);
            }
            if (isValid(i, j-1, n)) {
                ans2 = dfs(grid, i, j-1, n, k, negSelected, 2, sum + grid[i][j], memo);
            }
            ans = Math.max(ans1, ans2);
            if(ans != Integer.MIN_VALUE) {
                ans += grid[i][j];
            }
        }
        memo[i][j][from][originalNegSelected] = ans;
        return ans;
    }

    private static void solve(int[][] grid, int k, int n, int t) {
        long[][][][] memo = new long[n][n][3][6];
        for(long[][][] r1: memo) {
            for(long[][] r2: r1) {
                for(long[] r3: r2) {
                    Arrays.fill(r3, -1);
                }
            }
        }
        long ans = dfs(grid, 0, 0, n, k, 0, 1, 0, memo);
        if(ans == Integer.MIN_VALUE) {
            System.out.println("Case " + t + ": impossible");
        } else {
            System.out.println("Case " + t + ": " + ans);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = 0;
        while(true) {
            String[] arr = reader.readLine().split("\\s+");
            int n = Integer.parseInt(arr[0]);
            int k = Integer.parseInt(arr[1]);
            if(n==0 && k==0) break;
            t++;
            int[][] grid = new int[n][n];
            for(int i=0;i<n;i++) {
                arr = reader.readLine().split("\\s+");
                for(int j=0;j<n;j++) {
                    grid[i][j] = Integer.parseInt(arr[j]);
                }
            }
            solve(grid, k, n, t);
        }
    }
}
