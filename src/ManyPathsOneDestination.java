import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static void topSort(int u, List<List<Integer>> adjList, int[] dfs_num, List<Integer> ans) {
        dfs_num[u] = 1;
        for(int v: adjList.get(u)) {
            if(dfs_num[v] == 0) {
                topSort(v, adjList, dfs_num, ans);
            }
        }
        ans.add(u);
    }

    private static void solve(List<List<Integer>> adjList, int n) {
        List<Integer> ans = new ArrayList<>();
        int[] dfs_num = new int[n];
        for(int i=0;i<n;i++) {
            if(dfs_num[i] == 0) {
                topSort(i, adjList, dfs_num, ans);
            }
        }
        StringBuilder str = new StringBuilder();
        for(int i=n-1; i>=0; i--) {
            str.append(ans.get(i)).append(" ");
        }
//        System.out.println(str);
        long[] dist = new long[n];
        dist[0] = 1;
        for(int i=n-1; i>=0; i--) {
            int u = ans.get(i);
            for(int v: adjList.get(u)) {
                dist[v] += dist[u];
            }
        }
        System.out.println(dist[n-1]);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int count = 0;
        while(true) {
            String line = reader.readLine();
            if(line==null || line.isEmpty()) break;
            count++;
            if(count !=1) System.out.println();
            int n = Integer.parseInt(line);
            List<List<Integer>> adjList = new ArrayList<>();
            for(int i=0;i <=n;i++) adjList.add(new ArrayList<>());
            for(int i=0;i<n;i++) {
                String [] arr = reader.readLine().split("\\s+");
                int len = Integer.parseInt(arr[0]);
                if(len==0) {
                    adjList.get(i).add(n);
                } else {
                    for (int j = 1; j <= len; j++) {
                        int v = Integer.parseInt(arr[j]);
                        adjList.get(i).add(v);
                    }
                }
            }
            solve(adjList, n+1);
            reader.readLine();
        }
    }
}
