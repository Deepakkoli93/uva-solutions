import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    // 0 - white(unvisited), 1- black
    private static void topSort(int u, List<List<int[]>> adjList, int[] dfs_num, List<Integer> ans) {
        dfs_num[u] = 1;
        for(int[] v: adjList.get(u)) {
            if(dfs_num[v[0]] == 0) {
                topSort(v[0], adjList, dfs_num, ans);
            }
        }
        ans.add(u);
    }

    private static void runDfs(int[] nodes, List<List<int[]>> adjList, List<List<int[]>> inAdjList) {
        List<Integer> ans = new ArrayList<>();
        int[] dfs_num = new int[26];
        for(int i=0; i<26; i++) {
            if(nodes[i] != 1 && dfs_num[i] == 0) {
                topSort(i, adjList, dfs_num, ans);
            }
        }
//        StringBuilder str = new StringBuilder();
//        for(int x: ans) {
//            str.append((char)(x+'A')).append(" ");
//
//        }
//        System.out.println(str);

        long[] dist = new long[26];
        Arrays.fill(dist, Integer.MAX_VALUE);
        for(int i=0; i<26; i++) {
            if(nodes[i] != 1) {
                if (adjList.get(i).isEmpty()) {
                    dist[i] = nodes[i];
                }
            }
        }
        dist[ans.get(0)] = nodes[ans.get(0)];
        for(int i=0; i<ans.size(); i++) {
            int u = ans.get(i);
            for(int[] p: inAdjList.get(u)) {
                int v = p[0];
                dist[v] = Math.min(dist[v], dist[u] + nodes[v]);
            }
        }
        long maxCost = 0;
        for(int i=0; i<26; i++) {
            if(nodes[i] != 1) {
                if(inAdjList.get(i).isEmpty()) {
                    maxCost = Math.max(maxCost, -dist[i]);
                }
            }
        }
        System.out.println(maxCost);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine().trim());
        reader.readLine();
        while(t-- > 0) {

            int[] nodes = new int[26];
            Arrays.fill(nodes, 1);
            List<List<int[]>> adjList = new ArrayList<>();
            for(int i=0; i<26; i++) adjList.add(new ArrayList<>());

            List<List<int[]>> inAdjList = new ArrayList<>();
            for(int i=0; i<26; i++) inAdjList.add(new ArrayList<>());
            while(true) {
                String line = reader.readLine();
                if(line==null || line.isEmpty()) break;
                if(line.trim().isEmpty()) break;
                String[] arr = line.trim().split("\\s+");

                int src = arr[0].charAt(0) - 'A';
                int weight = Integer.parseInt(arr[1]);
                nodes[src] = -weight;
                if(arr.length > 2) {
                    for (char c : arr[2].toCharArray()) {
                        int dest = c - 'A';
                        adjList.get(src).add(new int[]{dest});
                        inAdjList.get(dest).add(new int[]{src});
                    }
                }
            }

            runDfs(nodes, adjList, inAdjList);
            if(t!=0) System.out.println();
        }
    }
}
