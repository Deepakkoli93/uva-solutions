import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class TouristGuide {

    private static int counter = 0;
    private static int rootChildren = 0;

    private static void dfs(Map<Integer, List<Integer>> adjList, int[] entry, int[] lowest_back, int curr, int[] parent, boolean[] articulation, int root) {
        entry[curr] = counter;
        lowest_back[curr] = counter++;
        for(int x: adjList.get(curr)) {
            if (entry[x] == -1) {
                parent[x] = curr;
                if(curr == root) rootChildren++;
                dfs(adjList, entry, lowest_back, x, parent, articulation, root);

                if(parent[curr] == -1) {
                    if(lowest_back[x] > entry[curr]) {
//                        System.out.println("lowest back of " + (curr+1) + " = " + lowest_back[curr]);
//                    System.out.println("entry of " + x + " = " + entry[curr]);
                        if(adjList.get(curr).size() > 1)
                            articulation[curr] = true;
                    }
                } else if(lowest_back[x] >= entry[curr]) {
//                    System.out.println("lowest back of " + (curr+1) + " = " + lowest_back[curr]);
//                    System.out.println("entry of " + x + " = " + entry[curr]);
                    if(adjList.get(curr).size() > 1)
                        articulation[curr] = true;
                }

                lowest_back[curr] = Math.min(lowest_back[curr], lowest_back[x]);
            } else if (x != parent[curr]) {
                lowest_back[curr] = Math.min(lowest_back[curr], entry[x]);
            }
        }
    }

    private static void solve(Map<Integer, List<Integer>> adjList, int n, int t, String[] names) {
        counter=0;
        int[] entry = new int[n];
        Arrays.fill(entry, -1);
        int[] lowest_back = new int[n];
        Arrays.fill(lowest_back, Integer.MAX_VALUE);
        int[] parent = new int[n];
        Arrays.fill(parent, -1);
        boolean[] articulation = new boolean[n];
        for(int i=0; i<n; i++) {
            if(entry[i] == -1) {
                rootChildren = 0;
                dfs(adjList, entry, lowest_back, i, parent, articulation, i);
                articulation[i] = rootChildren > 1;
            }
        }
        int ans = 0;
        List<String> allNames = new ArrayList<>();
        for(int i=0; i<n; i++ ) {
            if(articulation[i]){
                ans++;
                allNames.add(names[i]);
//                System.out.println("v = " + (i+1));
            }
        }
//        for(int i=0; i<n; i++ ) {
//            System.out.println("entry " + (i+1) + " = " + entry[i]);
//        }

        allNames.sort(String::compareTo);
        if(t!=1) System.out.println();
        System.out.println("City map #" + t + ": " + ans + " camera(s) found");
        for(String s: allNames) {
            System.out.println(s);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = 0;
        while(true) {
            int n = Integer.parseInt(reader.readLine().trim());
            if (n == 0) break;
            t++;
            Map<Integer, List<Integer>> adjList = new HashMap<>();
            for (int i = 0; i < n; i++) adjList.put(i, new ArrayList<>());
            String[] names = new String[n];
            Map<String, Integer> nameToNum = new HashMap<>();
            for(int i=0; i<n; i++) {
                String name = reader.readLine();
                nameToNum.put(name, i);
                names[i] = name;
            }
            int r = Integer.parseInt(reader.readLine());
            for(int i=0; i<r; i++) {
                String[] arr = reader.readLine().split("\\s+");
                int x = nameToNum.get(arr[0]);
                int y = nameToNum.get(arr[1]);
                adjList.get(x).add(y);
                adjList.get(y).add(x);
            }
            solve(adjList, n, t, names);
        }
    }
}
