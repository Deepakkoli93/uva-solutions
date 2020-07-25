import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class LiftlessEME {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String name = reader.readLine();
            if(name==null || name.isEmpty()) break;
            name = name.trim();
            String[] arr = reader.readLine().split("\\s+");
            int n = Integer.parseInt(arr[0]);
            int m = Integer.parseInt(arr[1]);
            int[][] grid = new int[n][m];
            long[] curr = new long[m];
            for(int i=0; i<n-1; i++) {
                long[] next = new long[m];
                Arrays.fill(next, Integer.MAX_VALUE);
                // reading m holes at a time
                for(int j=0; j<m; j++) {
                    arr = reader.readLine().split("\\s+");
                    for(int k=0; k<m; k++) {
                        int w = Integer.parseInt(arr[k]);
                        next[k] = Math.min(next[k], curr[j] + w + 2);
                    }
                }
                curr = next;
            }
            System.out.println(name);
            long ans = curr[0];
            for(int i=0;i<m;i++) {
                ans = Math.min(ans, curr[i]);
            }
            System.out.println(ans);
        }
    }
}
