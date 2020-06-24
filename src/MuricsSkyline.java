import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MuricsSkyline {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int tt = Integer.parseInt(reader.readLine());
        for(int t=1; t<=tt;t++) {
            int n = Integer.parseInt(reader.readLine());
            int[] h = new int[n];
            int[] w = new int[n];
            String[] arr = reader.readLine().split("\\s+");
            String[] arr1 = reader.readLine().split("\\s+");
            for(int i=0;i<n;i++) {
                h[i] = Integer.parseInt(arr[i]);
                w[i] = Integer.parseInt(arr1[i]);
            }

            int[] lis = new int[n];
            int[] lds = new int[n];

            for(int i=n-1; i>=0; i--) {
                lis[i] = w[i];
                for(int j=i+1; j<n; j++) {
                    if(h[j] > h[i]) {
                        lis[i] = Math.max(lis[i], lis[j] + w[i]);
                    }
                }
            }
            int maxi = lis[0];
            for(int i=0;i<n;i++) maxi = Math.max(maxi, lis[i]);
//            System.out.println(maxi);

            for(int i=n-1; i>=0; i--) {
                lds[i] = w[i];
                for(int j=i+1; j<n; j++) {
                    if (h[j] < h[i]) {
                        lds[i] = Math.max(lds[i], lds[j] + w[i]);
                    }
                }
            }
            int maxd =  lds[0];
            for(int i=0;i<n;i++) maxd = Math.max(maxd, lds[i]);

            if (maxi >= maxd) {
                System.out.println("Case " + t + ". Increasing (" + maxi + "). Decreasing (" + maxd + ").");
            } else {
                System.out.println("Case " + t + ". Decreasing (" + maxd + "). Increasing (" + maxi + ").");
            }

        }
    }
}
