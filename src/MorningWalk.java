import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class MorningWalk {

    private static boolean bfs(List<List<Integer>> adjList, int n, int[] degree) {
        boolean[] visited = new boolean[n];
        for(int i=0;i<n;i++) {
            if(degree[i] == 0) {
                visited[i] = true;
            }
        }
        int start = -1;
        for(int i=0;i<n;i++) {
            if(degree[i]>0) {
                start=i;
                break;
            }
        }
        Queue<Integer> q = new ArrayDeque<>();
        q.add(start);
        visited[start] = true;
        while(!q.isEmpty()) {
            int u= q.poll();
            for(int v: adjList.get(u)) {
                if(!visited[v]) {
                    visited[v] = true;
                    q.add(v);
                }
            }
        }

        for(int i=0;i<n;i++) {
            if(!visited[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String line = reader.readLine();
            if(line==null || line.isEmpty()) break;
            String[] arr = line.split("\\s+");
            int n = Integer.parseInt(arr[0]);
            int r = Integer.parseInt(arr[1]);
            int[] degree = new int[n];
            List<List<Integer>> adjList = new ArrayList<>();
            for(int i=0;i<n;i++) adjList.add(new ArrayList<>());
            for(int i=0; i<r; i++) {
                arr = reader.readLine().split("\\s+");
                int u = Integer.parseInt(arr[0]);
                int v = Integer.parseInt(arr[1]);
                adjList.get(u).add(v);
                adjList.get(v).add(u);
                degree[u]++;
                degree[v]++;
            }
            boolean ans = true;
            for(int i=0;i<n;i++) {
                if(degree[i] > 0 && degree[i] %2==1) {
                    ans = false;
                    break;
                }
            }
            if(!ans || r==0) {
                System.out.println("Not Possible");
            } else {
                if(bfs(adjList, n, degree)) {
                    System.out.println("Possible");
                } else {
                    System.out.println("Not Possible");
                }
            }
        }
    }
}
