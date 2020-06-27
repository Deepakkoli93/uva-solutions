import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ClawDecomposition {

    private static boolean bfs(Map<Integer, List<Integer>> adjList, int n) {
        Queue<Integer> q = new ArrayDeque<>();
        int[] color = new int[n];
        Arrays.fill(color, -1);
        for(int start=0; start<n; start++) {
            if(color[start] == -1) {
                q.add(start);
                color[start] = 0;
                while (!q.isEmpty()) {
                    int node = q.poll();
                    for (int x : adjList.get(node)) {
                        if (color[x] == -1) {
                            color[x] = 1 - color[node];
                            q.add(x);
                        } else {
                            if (color[node] == color[x]) {
                                return false;
                            }
                        }
                    }
                }

            }
        }
        return true;

    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            int n = Integer.parseInt(reader.readLine());
            if(n==0) break;
            Map<Integer, List<Integer>> adjList = new HashMap<>();
            for(int i=0; i<n; i++) adjList.put(i, new ArrayList<>());
            while(true) {
                String[] arr = reader.readLine().split("\\s+");
                int x = Integer.parseInt(arr[0]);
                int y = Integer.parseInt(arr[1]);
                if(x==0&&y==0) break;
                adjList.get(x-1).add(y-1);
                adjList.get(y-1).add(x-1);
            }
            boolean ans = bfs(adjList, n);
            if(ans) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }

        }
    }
}
