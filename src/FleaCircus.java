import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class FleaCircus {

    private static void bfs(List<List<Integer>> adjList, int n, int[] parent) {
        boolean[] visited = new boolean[n];
        Arrays.fill(parent, -1);
        Queue<Integer> q = new ArrayDeque<>();
        q.add(0);
        visited[0] = true;
        while(!q.isEmpty()) {
            int u = q.poll();
            for(int v: adjList.get(u)) {
                if(!visited[v]) {
                    visited[v] = true;
                    parent[v] = u;
                    q.add(v);
                }
            }
        }
    }

    private static List<Integer> getPath(int[] parent, int u) {
        List<Integer> ans = new ArrayList<>();
        ans.add(u);
        while(parent[u] != -1) {
            ans.add(parent[u]);
            u = parent[u];
        }
        Collections.reverse(ans);
        return ans;
    }

    private static List<Integer> getNewPath(List<Integer> path1, List<Integer> path2) {
        // path is smaller
        int i = 0;
        int l1 = path1.size(); int l2 = path2.size();
        while(i<l1 && i<l2) {
            if(path1.get(i).equals(path2.get(i))) {
                i++;
            } else {
                break;
            }
        }

        // path1 is prefix of path2
        if(i == l1) {
            return path2.subList(i-1, path2.size());
        } else {
            List<Integer> ll1 = path1.subList(i, path1.size());
            Collections.reverse(ll1);
            List<Integer> ll2 = path2.subList(i, path2.size());
            List<Integer> path = new ArrayList<>();
            path.addAll(ll1);
            path.add(path1.get(i-1));
            path.addAll(ll2);
            return path;
        }

    }

    private static void printAns(List<Integer> path) {
        int n = path.size();
        if(n%2==1) {
            int node = path.get((n-1)/2);
            System.out.println("The fleas meet at " + (node+1) + ".");
        } else {
            int x = path.get((n-1)/2);
            int y = path.get((n-1)/2 + 1);
            int small = Math.min(x, y);
            int big = Math.max(x, y);
            System.out.println("The fleas jump forever between " + (small+1) + " and " + (big+1) + ".");
        }
    }

    private static void solve(List<List<Integer>> adjList, int n, List<int[]> queries) {
        int[] parent = new int[n];
        bfs(adjList, n, parent);
        for(int[] q: queries) {
            List<Integer> path1 = getPath(parent, q[0]);
            List<Integer> path2 = getPath(parent, q[1]);
            if(path1.size() < path2.size()) {
                List<Integer> path = getNewPath(path1, path2);
                printAns(path);
            } else {
                List<Integer> path = getNewPath(path2, path1);
                printAns(path);
            }

        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            int n = Integer.parseInt(reader.readLine());
            if(n==0) break;
            List<List<Integer>> adjList = new ArrayList<>();
            for(int i=0;i<n;i++) adjList.add(new ArrayList<>());
            for(int i=0;i<n-1;i++) {
                String[] arr = reader.readLine().split("\\s+");
                int u = Integer.parseInt(arr[0]) - 1;
                int v = Integer.parseInt(arr[1]) - 1;
                adjList.get(u).add(v);
                adjList.get(v).add(u);
            }
            int q = Integer.parseInt(reader.readLine());
            List<int[]> queries = new ArrayList<>();
            while(q-- > 0) {
                String[] arr = reader.readLine().split("\\s+");
                queries.add(new int[]{Integer.parseInt(arr[0])-1, Integer.parseInt(arr[1])-1});
            }
            solve(adjList, n, queries);
        }
    }
}
