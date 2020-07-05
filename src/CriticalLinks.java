import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class CriticalLinks {

    private static int counter = 0;
    private static int rootChildren = 0;
    private static List<List<Integer>> bridges = new ArrayList<>();

    private static void dfs(Map<Integer, List<Integer>> adjList, int[] entry, int[] lowest_back, int curr, int[] parent) {
        entry[curr] = counter;
        lowest_back[curr] = counter++;
        for(int x: adjList.get(curr)) {
            if (entry[x] == -1) {
                parent[x] = curr;
//                if(curr == root) rootChildren++;
                dfs(adjList, entry, lowest_back, x, parent);

//                if(parent[curr] == -1) {
//                    if(lowest_back[x] > entry[curr]) {
////                        System.out.println("lowest back of " + (curr+1) + " = " + lowest_back[curr]);
////                    System.out.println("entry of " + x + " = " + entry[curr]);
//                        if(adjList.get(curr).size() > 1)
//                            articulation[curr] = true;
//                    }
//                } else
                if(lowest_back[x] > entry[curr]) {
//                    System.out.println("lowest back of " + (curr+1) + " = " + lowest_back[curr]);
//                    System.out.println("entry of " + x + " = " + entry[curr]);
//                    if(adjList.get(curr).size() > 1)
//                        articulation[curr] = true;
                    if(x<curr) {
                        List<Integer> ans = new ArrayList<>();
                        ans.add(x); ans.add(curr); bridges.add(ans);
                    } else {
                        List<Integer> ans = new ArrayList<>();
                        ans.add(curr); ans.add(x); bridges.add(ans);
                    }
                }

                lowest_back[curr] = Math.min(lowest_back[curr], lowest_back[x]);
            } else if (x != parent[curr]) {
                lowest_back[curr] = Math.min(lowest_back[curr], entry[x]);
            }
        }
    }

    private static void solve(Map<Integer, List<Integer>> adjList, int n) {
        counter=0;
        bridges = new ArrayList<>();
        int[] entry = new int[n];
        Arrays.fill(entry, -1);
        int[] lowest_back = new int[n];
        Arrays.fill(lowest_back, Integer.MAX_VALUE);
        int[] parent = new int[n];
        Arrays.fill(parent, -1);
        boolean[] articulation = new boolean[n];
        for(int i=0; i<n; i++) {
            if(entry[i] == -1) {
//                rootChildren = 0;
                dfs(adjList, entry, lowest_back, i, parent);
//                articulation[i] = rootChildren > 1;
            }
        }
//        int ans = 0;
//        for(int i=0; i<n; i++ ) {
//            if(articulation[i]){
//                ans++;
////                System.out.println("v = " + (i+1));
//            }
//        }
//        for(int i=0; i<n; i++ ) {
//            System.out.println("entry " + (i+1) + " = " + entry[i]);
//        }
        System.out.println(bridges.size() + " critical links");
        bridges.sort((o1, o2) -> {
            if(o1.get(0).equals(o2.get(0))) {
                return Integer.compare(o1.get(1), o2.get(1));
            } else {
                return Integer.compare(o1.get(0), o2.get(0));
            }
        });
        for(List<Integer> x: bridges) {
            System.out.println(x.get(0) + " - " + x.get(1));
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while(true) {
            String line = reader.readLine();
            if(line==null || line.isEmpty()) break;
            int n = Integer.parseInt(line);
            Map<Integer, List<Integer>> adjList = new HashMap<>();
            for(int i=0;i<n;i++) adjList.put(i, new ArrayList<>());
            for(int i=0; i<n; i++) {
                String[] arr = reader.readLine().split("\\s+");
                int x = Integer.parseInt(arr[0]);
                for(int j=2; j<arr.length; j++) {
                    int y = Integer.parseInt(arr[j]);
                    adjList.get(x).add(y);
                    adjList.get(y).add(x);
                }
            }
            reader.readLine();
            solve(adjList, n);
            System.out.println();
        }
    }
}
