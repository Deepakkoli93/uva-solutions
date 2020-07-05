import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Dominos2 {

    private static int counter = 0;
    private static int sccCounter = 0;
    private static List<List<Integer>> adjList = new ArrayList<>();
    private static final int[] entry = new int[100000];
    private static final int[] lowest_back = new int[100000];
    private static final boolean[] onStack = new boolean[100000];
    private static final int[] parent = new int[100000];
    private static final int[] sccNo = new int[100000];
    private static Stack<Integer> stack = new Stack<>();
    private static final boolean[] sccHasIn = new boolean[100000];

    private static int dfsTest(int level) {
        if(level==0) {
            return 1;
        }
        return 1 + dfsTest(level-1);
    }
    private static void dfs2(int curr) {
        Stack<Integer> recStack = new Stack<>();
        recStack.push(curr);
        while(!recStack.isEmpty()) {
            curr = recStack.peek();
            entry[curr] = counter;
            lowest_back[curr] = counter++;
            stack.add(curr);
            onStack[curr] = true;
            boolean recurse = false;
            for (int x : adjList.get(curr)) {
                if (entry[x] == -1) {
                    parent[x] = curr;
                    recStack.push(x);
                    recurse = true;
                    break;
//                    dfs(x);
//                    lowest_back[curr] = Math.min(lowest_back[curr], lowest_back[x]);
                } else if (onStack[x]) {
                    lowest_back[curr] = Math.min(lowest_back[curr], entry[x]);
                }
                if (!onStack[x]) {
                    sccHasIn[sccNo[x]] = true;
                }
            }
            if(recurse) continue;
            curr = recStack.pop();
            if(parent[curr] != -1)
            lowest_back[parent[curr]] = Math.min(lowest_back[parent[curr]], lowest_back[curr]);
            if (entry[curr] == lowest_back[curr]) {
                while (true) {
                    int v = stack.pop();
                    onStack[v] = false;
                    sccNo[v] = sccCounter;
                    sccHasIn[sccCounter] = false;
                    if (v == curr) break;
                }
                sccCounter++;
            }
        }
    }
    private static void dfs(int curr) {
        entry[curr] = counter;
        lowest_back[curr] = counter++;
        stack.add(curr);
        onStack[curr] = true;
        for(int x: adjList.get(curr)) {
            if (entry[x] == -1) {
                dfs(x);
                lowest_back[curr] = Math.min(lowest_back[curr], lowest_back[x]);
            } else if (onStack[x]) {
                lowest_back[curr] = Math.min(lowest_back[curr], entry[x]);
            }
            if (!onStack[x]) {
                sccHasIn[sccNo[x]] = true;
            }
        }
        if(entry[curr] == lowest_back[curr]) {
            while(true) {
                int v = stack.pop();
                onStack[v] = false;
                sccNo[v] = sccCounter;
                sccHasIn[sccCounter]= false;
                if(v==curr) break;
            }
            sccCounter++;
        }
    }

    private static void solve(int n) {
        counter=0;
        sccCounter = 0;
        Arrays.fill(entry, -1);
        Arrays.fill(lowest_back, Integer.MAX_VALUE);
        Arrays.fill(onStack, false);
        Arrays.fill(sccNo, -1);
        stack = new Stack<>();
        Arrays.fill(sccHasIn, true);
        Arrays.fill(parent, -1);

        for(int i=0; i<n; i++) {
            if(entry[i] == -1) {
                dfs2(i);
            }
        }
        int ans = 0;
        for(int i=0; i<100000; i++) {
            if(!sccHasIn[i]) {
                ans++;
            }
        }
        System.out.println(ans);
    }

    public static void main(String[] args) throws IOException {
        dfsTest(10000);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine().trim());
        while(t-- > 0) {
            String[] arr = reader.readLine().trim().split("\\s+");
            int n = Integer.parseInt(arr[0].trim());
            int m = Integer.parseInt(arr[1].trim());

            adjList = new ArrayList<>();
            for(int i=0; i<100000; i++) {
                adjList.add(new ArrayList<>());
            }
//            for (int i = 0; i < n; i++) adjList.put(i, new ArrayList<>());
            for(int i=0; i<m; i++) {
                arr = reader.readLine().trim().split("\\s+");
                int x = Integer.parseInt(arr[0].trim());
                int y = Integer.parseInt(arr[1].trim());
                adjList.get(x-1).add(y-1);
            }
            solve(n);
        }
    }
}
