import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Xyzzy {

    private static boolean reachable(List<List<Integer>> adjList, int n, int s, int e) {
        Queue<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[n];
        q.add(s);
        visited[s] = true;
        while(!q.isEmpty()) {
            int curr = q.poll();
            visited[curr] = true;
            for(int v: adjList.get(curr)) {
                if (!visited[v]) {
                    q.add(v);
                    visited[v] = true;
                    if(v==e) return true;
                }
            }
        }
        return visited[e];
    }

    private static boolean solve(int n, int[] energy, List<List<Integer>> adjList) {
        long[] dist = new long[n];
        Arrays.fill(dist, Integer.MIN_VALUE);
        dist[0] = 100;
        for(int i=0; i<n-1;i++) {
            for(int u=0;u<n;u++) {
                for(int v: adjList.get(u)) {
                    if (dist[u] + energy[v] > 0) {
                        dist[v] = Math.max(dist[v], dist[u] + energy[v]);
                    }
                }
            }
        }
        if(dist[n-1] > 0) {
            return true;
        }

        for(int u=0;u<n;u++) {
            for(int v: adjList.get(u)) {
                if (dist[u] + energy[v] > 0 && dist[u] + energy[v] > dist[v]) {
                    dist[v] = Math.max(dist[v], dist[u] + energy[v]);

                    // u -> v;
                    boolean isR = reachable(adjList, n, v, n-1);
                    if(isR) return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Scanner sc = new Scanner(System.in);
        while (true) {

//            String line = reader.readLine();
//            if (line == null || line.isEmpty()) break;
            int n =  sc.nextInt();//Integer.parseInt(line.trim());

            if (n == -1) break;
            List<List<Integer>> adjList = new ArrayList<>();
            int[] energy = new int[n];
            for (int i = 0; i < n; i++) {
//                String[] arr = reader.readLine().trim().split("\\s+");
                adjList.add(new ArrayList<>());
                energy[i] = sc.nextInt();// Integer.parseInt(arr[0].trim());
                int len = sc.nextInt();;//Integer.parseInt(arr[1].trim());
                for (int j = 0; j < len; j++) {
                    int rno = sc.nextInt() - 1;
                    if (rno < n)
                        adjList.get(i).add(rno);
                }
            }
            boolean ans = solve(n, energy, adjList);
            if (ans) {
                System.out.println("winnable");
            } else {
                System.out.println("hopeless");
            }

        }
    }
}
