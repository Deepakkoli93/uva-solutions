import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Beverages {

    private static void enqZero(Map<String, List<String>> adjList, Queue<String> q) {
        Map<String, Boolean> indegree = new HashMap<>();
        for(Map.Entry<String, List<String>> entry: adjList.entrySet()) {
            indegree.put(entry.getKey(), false);
        }
        for(Map.Entry<String, List<String>> entry: adjList.entrySet()) {
            for(String s: entry.getValue()) {
                indegree.put(s, true);
            }
        }
        for(Map.Entry<String, Boolean> entry: indegree.entrySet()) {
            if(!entry.getValue() && adjList.containsKey(entry.getKey()) && !q.contains(entry.getKey())) {
                q.add(entry.getKey());
            }
        }
    }

    private static void solve(Map<String, List<String>> adjList, Map<String, Integer> inputOrder, int t) {
        List<String> ans = new ArrayList<>();
        Queue<String> q = new PriorityQueue<>(Comparator.comparingInt(inputOrder::get));
        enqZero(adjList, q);
        while(!q.isEmpty()) {
            String node = q.poll();
            ans.add(node);
            try {
                adjList.remove(node);
            }catch(Exception e){}
            enqZero(adjList, q);
        }
        StringBuilder str = new StringBuilder();
        for(int i=0; i<ans.size(); i++) {
            str.append(ans.get(i));
            if(i!=ans.size()-1) str.append(" ");
        }
        System.out.println("Case #"+t+": Dilbert should drink beverages in this order: " + str + ".");
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = 0;
        while(true) {

            String line = reader.readLine();
            if(line==null || line.isEmpty()) break;
            t++;
            int n = Integer.parseInt(line);
            Map<String, List<String>> adjList = new HashMap<>();
            Map<String, Integer> inputOrder= new HashMap<>();
            for(int i=0;i<n;i++) {
                String node = reader.readLine().trim();
                adjList.put(node, new ArrayList<>());
                inputOrder.put(node, i);
            }
            int m = Integer.parseInt(reader.readLine().trim());
            for(int i=0; i<m; i++) {
                String[] arr = reader.readLine().trim().split("\\s+");
                try {
                    adjList.get(arr[0]).add(arr[1]);
                }
                catch(Exception e){}
            }
            try {
                solve(adjList, inputOrder, t);
            }catch(Exception e){}

            line = reader.readLine();
            if(line==null) break;
        }
    }
}
