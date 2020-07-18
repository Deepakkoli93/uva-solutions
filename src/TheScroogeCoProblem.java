import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TheScroogeCoProblem {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine());
        while(t-- > 0) {
            int n = Integer.parseInt(reader.readLine());
            String[] names = new String[n];
            Map<String, Integer> nameToInd = new HashMap<>();
            String[] arr = reader.readLine().split("\\s+");
            for(int i=0;i<n;i++) {
                names[i] = arr[i];
                nameToInd.put(arr[i], i);
            }
            long[][] dist = new long[n][n];
            for(long[] row: dist) Arrays.fill(row, Integer.MAX_VALUE);
            int[][] path = new int[n][n];
            for(int[] row: path) Arrays.fill(row, -1);
            for(int i=0; i<n ;i++) path[i][i] = i;
            for(int i=0; i<n; i++) {
                arr = reader.readLine().split("\\s+");
                for(int j=0; j<n; j++) {
                    int cost = Integer.parseInt(arr[j]);
                    if (cost != -1) {
                        dist[i][j] = cost;
                        path[i][j] = j;
                    }
                }
            }

            for(int k=0; k<n; k++) {
                for(int i=0; i<n; i++) {
                    for(int j=0; j<n; j++) {
                        if (dist[i][j] > dist[i][k] + dist[k][j]) {
                            dist[i][j] = dist[i][k] + dist[k][j];
                            path[i][j] = path[i][k];
                        }
                    }
                }
            }


            int r = Integer.parseInt(reader.readLine());
            while(r-- > 0) {
                arr = reader.readLine().split("\\s+");
                int x = nameToInd.get(arr[1]);
                int y = nameToInd.get(arr[2]);
                if (dist[x][y] != Integer.MAX_VALUE) {
                    System.out.println("Mr " + arr[0] + " to go from " + arr[1] + " to " + arr[2] + ", you will receive " + dist[x][y] + " euros");
                    StringBuilder str = new StringBuilder(names[x]).append(" ");
                    while(true) {
                        x = path[x][y];
                        str.append(names[x]).append(" ");
                        if(x == y) break;
                    }
//                    str.append(names[y]);
                    System.out.println("Path:" + str.toString().trim());
                } else {
                    System.out.println("Sorry Mr " + arr[0] + " you can not go from " + arr[1] + " to " + arr[2]);
                }
            }

        }
    }
}
