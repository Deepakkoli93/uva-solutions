import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Commandos {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine());
        for(int tt=1; tt<=t; tt++) {
            int n = Integer.parseInt(reader.readLine());
            int r = Integer.parseInt(reader.readLine());
            boolean[][] adjMat = new boolean[n][n];
            for(int x=0; x<r; x++) {
                String[] arr = reader.readLine().split("\\s+");
                int i = Integer.parseInt(arr[0]);
                int j = Integer.parseInt(arr[1]);
                adjMat[i][j] = true;
                adjMat[j][i] = true;
            }
            String[] arr = reader.readLine().split("\\s+");
            int s = Integer.parseInt(arr[0]);
            int d = Integer.parseInt(arr[1]);
            long[][] dist = new long[n][n];
            for(long[] row: dist) Arrays.fill(row, Integer.MAX_VALUE);
            for(int i=0; i<n;i++) {
                for(int j=0;j<n;j++) {
                    if(i==j) {
                        dist[i][j] = 0;
                    } else if (adjMat[i][j]) {
                        dist[i][j] = 1;
                    }
                }
            }
            for(int k=0; k<n; k++) {
                for(int i=0; i<n; i++) {
                    for(int j=0; j<n; j++) {
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }
            long ans = Integer.MIN_VALUE;
            for(int i=0; i<n; i++) {
                ans = Math.max(ans, dist[s][i] + dist[i][d]);
            }
            System.out.println(String.format("Case %d: %d", tt, ans));
        }
    }
}
