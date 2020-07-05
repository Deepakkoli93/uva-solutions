import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ComeAndGo {

    private static int counter = 0;

    private static boolean dfs(Map<Integer, List<Integer>> adjList, int[] entry, int[] lowest_back, int curr, Stack<Integer> stack,
                            boolean[] onStack, int root) {
        entry[curr] = counter;
        lowest_back[curr] = counter++;
        stack.add(curr);
        onStack[curr] = true;
        for(int x: adjList.get(curr)) {
            if (entry[x] == -1) {
                boolean ans = dfs(adjList, entry, lowest_back, x, stack, onStack, root);
                if(!ans) return false;
                lowest_back[curr] = Math.min(lowest_back[curr], lowest_back[x]);
            } else if (onStack[x]) {
                lowest_back[curr] = Math.min(lowest_back[curr], entry[x]);
            }
        }
        if(entry[curr] == lowest_back[curr] && curr != root) {
            return false;
        }
        return true;
    }

    private static boolean solve(Map<Integer, List<Integer>> adjList, int n) {
        counter=0;
        int[] entry = new int[n];
        Arrays.fill(entry, -1);
        int[] lowest_back = new int[n];
        Arrays.fill(lowest_back, Integer.MAX_VALUE);
        boolean ans = dfs(adjList, entry, lowest_back, 0, new Stack<>(), new boolean[n], 0);
        if(!ans) return ans;
        for(int i=0; i<n; i++) {
            if(entry[i] == -1) return false;
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String[] arr = reader.readLine().split("\\s+");
            int n = Integer.parseInt(arr[0]);
            int m = Integer.parseInt(arr[1]);
            if(n==0 && m==0) break;
            Map<Integer, List<Integer>> adjList = new HashMap<>();
            for (int i = 0; i < n; i++) adjList.put(i, new ArrayList<>());
            for(int i=0; i<m; i++) {
                arr = reader.readLine().split("\\s+");
                int x = Integer.parseInt(arr[0])-1;
                int y = Integer.parseInt(arr[1])-1;
                int p = Integer.parseInt(arr[2]);
                adjList.get(x).add(y);
                if(p==2) adjList.get(y).add(x);
            }
            boolean ans = solve(adjList, n);
            if(ans) {
                System.out.println("1");
            } else {
                System.out.println("0");
            }
        }
    }
}
