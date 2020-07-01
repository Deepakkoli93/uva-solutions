import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GridGame_MBA {

    private static long getHash(Set<Integer> crossedRows, Set<Integer> crossedCols, int n) {
        int[][] grid = new int[n][n];
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                grid[i][j] = 1;
            }
        }
        for(int c: crossedRows) {
            for(int j=0;j<n;j++) {
                grid[c][j] = 0;
            }
        }
        for(int c: crossedCols) {
            for(int i=0;i<n;i++) {
                grid[i][c] = 0;
            }
        }
        long hash = 0;
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                hash = hash << 1;
                hash = hash | grid[i][j];
            }
        }
        return hash;
    }

    private static int dfs(int [][] grid, int n, Set<Integer> crossedRows, Set<Integer> crossedCols, boolean alice, int aliceRow, Map<Long, Integer> mem) {
//        System.out.println(aliceRow);
        if (alice) {
            long hash = getHash(crossedRows, crossedCols, n);
//            System.out.println(hash);
            if (mem.containsKey(hash)) {
                return mem.get(hash);
            }
            if (crossedRows.size() >= n) {
                return 0;
            }
            Set<Integer> cands = new HashSet<>();
            for(int i=0; i<n; i++) {
                if (!crossedRows.contains(i)) {
                    cands.add(i);
                }
            }
            int ans = Integer.MIN_VALUE;
            for(int cand: cands) {
                crossedRows.add(cand);
                ans = Math.max(ans, dfs(grid, n, crossedRows, crossedCols, false, cand, mem));
                crossedRows.remove(cand);
            }

            mem.put(hash, ans);
            return ans;
        } else {
            Set<Integer> cands = new HashSet<>();
            for(int i=0; i<n; i++) {
                if (!crossedCols.contains(i)) {
                    cands.add(i);
                }
            }
            int ans = Integer.MAX_VALUE;
            for(int cand: cands) {
                crossedCols.add(cand);
                ans = Math.min(ans, grid[aliceRow][cand] + dfs(grid, n, crossedRows, crossedCols, true, -1, mem));
                crossedCols.remove(cand);
            }
            return ans;
        }
    }

    private static int solve(int[][] grid, int n) {
        return dfs(grid, n, new HashSet<>(), new HashSet<>(), true, -1, new HashMap<>());
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine());
        while(t-- > 0) {
            int n = Integer.parseInt(reader.readLine());
            int[][] grid = new int[n][n];
            for(int i=0; i<n; i++) {
                String[] arr = reader.readLine().split("\\s+");
                for(int j=0;j<n;j++) {
                    grid[i][j] = Integer.parseInt(arr[j]);
                }
            }
            System.out.println(solve(grid, n));
        }
    }
}
