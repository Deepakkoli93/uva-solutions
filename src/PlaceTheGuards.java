import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class PlaceTheGuards {

    private static int bfs(Map<Integer, List<Integer>> adjList, int n) {
        int ans = 0;
        Queue<Integer> q = new ArrayDeque<>();
        int[] color = new int[n];
        Arrays.fill(color, -1);
        for(int start=0; start<n; start++) {
            if(color[start] == -1) {
                int ones = 0;
                int zeroes = 1;
                q.add(start);
                color[start] = 0;
                while (!q.isEmpty()) {
                    int node = q.poll();
                    for (int x : adjList.get(node)) {
                        if (color[x] == -1) {
                            color[x] = 1 - color[node];
                            if(color[x]==0) zeroes++;
                            else if(color[x]==1) ones++;
                            q.add(x);
                        } else {
                            if (color[node] == color[x]) {
                                return -1;
                            }
                        }
                    }
                }

                if(ones==0 && zeroes==0) ans+= 0;
                else if (ones==0) ans += zeroes;
                else if (zeroes==0) ans += ones;
                else ans += Math.min(ones, zeroes);

            }
        }
        return ans;


    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine());
        for(int tt=1; tt<=t;tt++) {
            String[] arr = reader.readLine().split("\\s+");
            int v = Integer.parseInt(arr[0]);
            int e = Integer.parseInt(arr[1]);
//            if(tt==8) System.out.println("v= "+v + " e = " + e);
            Map<Integer, List<Integer>> adjList = new HashMap<>();
            for(int i=0; i<v; i++) adjList.put(i, new ArrayList<>());
            for(int i=0;i<e;i++) {
                arr = reader.readLine().split("\\s+");
                int x = Integer.parseInt(arr[0]);
                int y = Integer.parseInt(arr[1]);
                adjList.get(x).add(y);
                adjList.get(y).add(x);
            }
            int ans = bfs(adjList, v);
            System.out.println(ans);
        }
    }
}
