import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NutsAndBolts {


    private static int mcbm(int left, boolean[] visited, List<List<Integer>> adjList, int[] owner) {
        if(visited[left]) return 0;
        visited[left] = true;
        for(int right: adjList.get(left)) {
            if(owner[right] == -1 || mcbm(owner[right], visited, adjList, owner) > 0) {
                owner[right] = left;
                return 1;
            }
        }
        return 0;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int tt = Integer.parseInt(reader.readLine());
        for(int t=1; t<=tt; t++) {
            String[] arr = reader.readLine().split("\\s+");
            int r = Integer.parseInt(arr[0]);
            int c = Integer.parseInt(arr[1]);
            int n = r + c;
            List<List<Integer>> adjList = new ArrayList<>();
            for(int i=0;i<n;i++) adjList.add(new ArrayList<>());
            boolean[] left = new boolean[n];
            for(int i=0; i<r; i++) {
                left[i] = true;
                arr = reader.readLine().split("\\s+");
                for(int j=0;j<c;j++) {
                    if(Integer.parseInt(arr[j]) == 1) {
                        adjList.get(i).add(r + j);
                        adjList.get(r + j).add(i);
                    }
                }
            }

            boolean[] visited = new boolean[n];
            int[] owner = new int[n];
            int card = 0;
            Arrays.fill(owner, -1);
            for(int i=0; i<n; i++) {
                if(left[i]) {
                    Arrays.fill(visited, false);
                    card += mcbm(i, visited, adjList, owner);
                }
            }

            System.out.println("Case " + t + ": a maximum of " + card + " nuts and bolts can be fitted together");

        }
    }
}
