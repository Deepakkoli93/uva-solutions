import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class CallingCircles {

    private static int counter = 0;
    private static int rootChildren = 0;

    private static void dfs(Map<Integer, List<Integer>> adjList, int[] entry, int[] lowest_back, int curr, Stack<Integer> stack,
                            boolean[] onStack, String[] names, List<List<String>> ans) {
        entry[curr] = counter;
        lowest_back[curr] = counter++;
        stack.add(curr);
        onStack[curr] = true;
        for(int x: adjList.get(curr)) {
            if (entry[x] == -1) {
                dfs(adjList, entry, lowest_back, x, stack, onStack, names, ans);
                lowest_back[curr] = Math.min(lowest_back[curr], lowest_back[x]);
            } else if (onStack[x]) {
                lowest_back[curr] = Math.min(lowest_back[curr], entry[x]);
            }
        }
        if(entry[curr] == lowest_back[curr]) {
            List<String> scc = new ArrayList<>();
            while(true) {
                int v = stack.pop();
                onStack[v] = false;
                if(names[v] != null)
                scc.add(names[v]);
                if(v==curr) break;
            }
            ans.add(scc);
        }
    }

    private static void solve(Map<Integer, List<Integer>> adjList, int n,  String[] names, int t) {
        counter=0;
        int[] entry = new int[n];
        Arrays.fill(entry, -1);
        int[] lowest_back = new int[n];
        Arrays.fill(lowest_back, Integer.MAX_VALUE);
        int[] parent = new int[n];
        Arrays.fill(parent, -1);
        List<List<String>> ans  = new ArrayList<>();
        for(int i=0; i<n; i++) {
            if(entry[i] == -1) {
                rootChildren = 0;
                dfs(adjList, entry, lowest_back, i, new Stack<>(), new boolean[n], names, ans);
            }
        }

        if(t!=1)System.out.println();
        System.out.println("Calling circles for data set " + t + ":");
        for(List<String> scc: ans) {
            int len = scc.size();
            StringBuilder str = new StringBuilder();
            for(int i=0; i<len; i++) {
                str.append(scc.get(i));
                if(i != len-1) str.append(", ");
            }
            System.out.println(str);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = 0;
        while(true) {
            String[] arr = reader.readLine().split("\\s+");
            int n = Integer.parseInt(arr[0]);
            int m = Integer.parseInt(arr[1]);
            if(n==0 && m==0) break;
            t++;
            Map<Integer, List<Integer>> adjList = new HashMap<>();
            for (int i = 0; i < n; i++) adjList.put(i, new ArrayList<>());
            String[] names = new String[n];
            Map<String, Integer> nameToNum = new HashMap<>();
            int numCounter = 0;
            for(int i=0; i<m; i++) {
                arr = reader.readLine().split("\\s+");
                int x,y;
                if(nameToNum.containsKey(arr[0])) {
                    x = nameToNum.get(arr[0]);
                } else {
                    names[numCounter] = arr[0];
                    nameToNum.put(arr[0], numCounter);
                    x = numCounter;
                    numCounter++;
                }
                if(nameToNum.containsKey(arr[1])) {
                    y = nameToNum.get(arr[1]);
                } else {
                    names[numCounter] = arr[1];
                    nameToNum.put(arr[1], numCounter);
                    y = numCounter;
                    numCounter++;
                }
                adjList.get(x).add(y);
            }
            solve(adjList, n, names, t);
        }
    }
}
