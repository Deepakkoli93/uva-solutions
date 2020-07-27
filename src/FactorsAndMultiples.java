import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FactorsAndMultiples {

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
            int n = Integer.parseInt(arr[0]);
            int[] A = new int[n];
            for(int i=0; i<n; i++) {
                A[i] = Integer.parseInt(arr[i+1]);
            }

            arr = reader.readLine().split("\\s+");
            int m = Integer.parseInt(arr[0]);
            int[] B = new int[m];
            for(int i=0; i<m; i++) {
                B[i] = Integer.parseInt(arr[i+1]);
            }

            List<List<Integer>> adjList = new ArrayList<>();
            for(int i=0; i<n; i++) adjList.add(new ArrayList<>());

            for(int i=0; i<n; i++) {
                for(int j=0; j<m; j++) {
                    if(B[j] == 0 || (A[i] > 0 && B[j] % A[i] == 0)) {
                        adjList.get(i).add(n+j);
                    }
                }
            }

            boolean[] visited = new boolean[n+m];
            int[] owner = new int[n+m];
            int card = 0;
            Arrays.fill(owner, -1);
            for(int i=0; i<n; i++) {
                Arrays.fill(visited, false);
                card += mcbm(i, visited, adjList, owner);
            }
            System.out.println("Case " + t + ": " + card);
        }
    }
}
