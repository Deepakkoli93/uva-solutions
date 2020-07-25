import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FireFireFire {

    private static long dfs(int u, int flag, List<List<Integer>> adjList, boolean[] leaf, long[][] memo) {
        long ans = 0;
        if(memo[u][flag] != -1) {
            return memo[u][flag];
        } else if (leaf[u]){
            ans = flag;
        } else if (flag == 0) {
            ans = 0;
            for(int v: adjList.get(u)) {
                ans += dfs(v, 1, adjList, leaf, memo);
            }
        } else {
            ans = 1;
            for(int v: adjList.get(u)) {
                long ans1 = dfs(v, 1, adjList, leaf, memo);
                long ans2 = dfs(v, 0, adjList, leaf, memo);
//                System.out.println(ans1 + " " + ans2);
                ans += Math.min(ans1, ans2);
            }
        }
        memo[u][flag] = ans;
        return ans;
    }

    private static void dfs1(int u, List<List<Integer>> adjList, List<List<Integer>> newAdjList, int[] dfs_num, List<Integer> topSort) {
        dfs_num[u] = 1;
        for(int v: adjList.get(u)) {
            if(dfs_num[v] == 0) {
                newAdjList.get(u).add(v);
                dfs1(v, adjList, newAdjList, dfs_num, topSort);
            }
        }
        topSort.add(u);
    }

    private static void solve(List<List<Integer>> adjList, List<List<Integer>> inAdjList, int n) {
        if (n==1) {
            System.out.println("1");
            return;
        }
        List<Integer> topSort = new ArrayList<>();
        int[] dfs_num = new int[n];
        List<List<Integer>> newAdjList = new ArrayList<>();
        for(int i=0;i<n;i++) newAdjList.add(new ArrayList<>());
        dfs1(0, adjList, newAdjList, dfs_num, topSort);
        int root = topSort.get(topSort.size()-1);
//        int root = -1;
//        for(int i=0;i<n;i++) {
//            if(inAdjList.get(i).isEmpty()) {
//                root = i;
//                break;
//            }
//        }
        boolean[] leaf = new boolean[n];
        for(int i=0; i<n; i++) {
            if(newAdjList.get(i).isEmpty()) {
                leaf[i] = true;
            }
        }
        long[][] memo = new long[n][2];
        for(long[] row: memo) Arrays.fill(row, -1);
        long ans1 = dfs(root, 0, newAdjList, leaf, memo);
        long ans2 = dfs(root, 1, newAdjList, leaf, memo);
        long ans = Math.min(ans1, ans2);
        System.out.println(ans);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            int n = Integer.parseInt(reader.readLine());
            if(n==0) break;
            List<List<Integer>> adjList = new ArrayList<>();
            List<List<Integer>> inAdjList = new ArrayList<>();
            for(int i=0;i<n;i++) {
                adjList.add(new ArrayList<>());
                inAdjList.add(new ArrayList<>());
            }
            for(int i=0; i<n; i++) {
                String[] arr = reader.readLine().split("\\s+");
                int len = Integer.parseInt(arr[0]);
                for(int j=1; j<=len; j++) {
                    int v = Integer.parseInt(arr[j]) - 1;
//                    if(!adjList.get(v).contains(i)) {
                        adjList.get(i).add(v);
                        inAdjList.get(v).add(i);
//                    adjList.get(v).add(i);
//                    }
                }
            }
            solve(adjList, inAdjList, n);
        }
    }
}
