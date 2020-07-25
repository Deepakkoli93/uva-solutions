import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LongestRun {

    private static int getNodeNum(int i, int j, int cols) {
        return i * cols + j;
    }

    private static void topSort(int u, List<List<Integer>> adjList, int[] dfs_num, List<Integer> ans) {
        dfs_num[u] = 1;
        for(int v: adjList.get(u)) {
            if (dfs_num[v] == 0) {
                topSort(v, adjList, dfs_num, ans);
            }
        }
        ans.add(u);
    }

    private static long solve(List<List<Integer>> adjList, List<List<Integer>> inAdjList, int n) {
        int[] dfs_num = new int[n];
        List<Integer> ans = new ArrayList<>();
        for(int i=0;i<n;i++) {
            if(dfs_num[i] == 0) {
                topSort(i, adjList, dfs_num, ans);
            }
        }
        long[] dist = new long[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        for(int i=0;i<n;i++) {
            if(inAdjList.get(i).isEmpty()) {
                dist[i] = -1;
            }
        }

        for(int i=n-1;i>=0;i--) {
            int u = ans.get(i);
            for(int v: adjList.get(u)) {
                dist[v] = Math.min(dist[v], dist[u] - 1);
            }
        }
        long maxCost = 0;
        for(int i=0;i<n;i++) {
            if(adjList.get(i).isEmpty()) {
                maxCost = Math.max(maxCost, -dist[i]);
            }
        }
        return maxCost;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine());
        while(t-- > 0) {
            String[] arr = reader.readLine().split("\\s+");
            String name = arr[0];
            int r = Integer.parseInt(arr[1]);
            int c = Integer.parseInt(arr[2]);
            int n = r*c;
            List<List<Integer>> adjList = new ArrayList<>();
            List<List<Integer>> inAdjList = new ArrayList<>();
            for(int i=0; i<n; i++) {
                adjList.add(new ArrayList<>());
                inAdjList.add(new ArrayList<>());
            }
            int[][] grid = new int[r][c];
            for(int i=0; i<r; i++) {
                arr = reader.readLine().split("\\s+");
                for(int j=0; j<c; j++) {
                    grid[i][j] = Integer.parseInt(arr[j]);
                }
            }
            for(int i=0; i<r; i++) {
                for(int j=0;j<c;j++) {
                    int h = grid[i][j];
                    int curr = getNodeNum(i, j, c);
                    // up
                    if(i-1>=0 && grid[i-1][j] < h) {
                        adjList.get(curr).add(getNodeNum(i-1, j, c));
                        inAdjList.get(getNodeNum(i-1, j, c)).add(curr);
                    }
                    // left
                    if(j-1>=0 && grid[i][j-1] < h) {
                        adjList.get(curr).add(getNodeNum(i, j-1, c));
                        inAdjList.get(getNodeNum(i, j-1, c)).add(curr);
                    }
                    //down
                    if(i+1 < r && grid[i+1][j] < h) {
                        adjList.get(curr).add(getNodeNum(i+1, j, c));
                        inAdjList.get(getNodeNum(i+1, j, c)).add(curr);
                    }
                    // right
                    if(j+1 < c && grid[i][j+1] < h) {
                        adjList.get(curr).add(getNodeNum(i, j+1, c));
                        inAdjList.get(getNodeNum(i, j+1, c)).add(curr);
                    }
                }
            }

            long maxCost = solve(adjList, inAdjList, n);
            System.out.println(name + ": " + maxCost);

        }
    }
}
