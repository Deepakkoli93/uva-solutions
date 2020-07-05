import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderingTasks {

    private static void dfs(Map<Integer, List<Integer>> adjList, int n, int[] colors, int curr, List<Integer> ans) {
        colors[curr] = 2;
        for(int j=0; j<adjList.get(curr).size(); j++) {
            if(colors[adjList.get(curr).get(j)] == 0) {
                dfs(adjList, n, colors,adjList.get(curr).get(j), ans );
            }

        }
        ans.add(curr);
    }

    private static void solve(Map<Integer, List<Integer>> adjList, int n) {
        // 0 - white, 1-grey, 2-black
        int[] colors = new int[n];
        List<Integer> ans = new ArrayList<>();
        for(int i=0; i<n; i++) {
            if(colors[i] == 0) {
                dfs(adjList, n, colors, i, ans);
            }
        }
        StringBuilder str = new StringBuilder();
        for(int i=ans.size()-1; i>=0; i--) {
            str.append(ans.get(i)+1);
            if(i!=0) str.append(" ");
        }
        System.out.println(str);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String[] arr = reader.readLine().split("\\s+");
            int n = Integer.parseInt(arr[0]);
            int m = Integer.parseInt(arr[1]);
            if (n == 0 && m == 0) break;

            Map<Integer, List<Integer>> adjList = new HashMap<>();
            for(int i=0; i<n; i++) adjList.put(i, new ArrayList<>());
            for(int i=0; i<m; i++) {
                arr = reader.readLine().split("\\s+");
                adjList.get(Integer.parseInt(arr[0]) - 1).add(Integer.parseInt(arr[1]) - 1);
            }
            solve(adjList, n);
        }
    }
}
