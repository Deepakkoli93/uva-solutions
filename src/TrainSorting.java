import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TrainSorting {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t= Integer.parseInt(reader.readLine());
        while(t-- > 0) {
            int n = Integer.parseInt(reader.readLine());
            int[] arr = new int[n];
            for(int i=0;i<n;i++) {
                arr[i] = Integer.parseInt(reader.readLine());
            }
            int[] lis = new int[n];
            int[] lds = new int[n];

            for(int i=n-1; i>=0; i--) {
                lis[i] = 1;
                for(int j=i+1; j<n; j++) {
                    if(arr[j] > arr[i]) {
                        lis[i] = Math.max(lis[i], lis[j] + 1);
                    }
                }
            }

            for(int i=n-1; i>=0; i--) {
                lds[i] = 1;
                for(int j=i+1; j<n; j++) {
                    if (arr[j] < arr[i]) {
                        lds[i] = Math.max(lds[i], lds[j] + 1);
                    }
                }
            }

            int ans = 0;
            for(int i=0;i<n;i++) {
                ans = Math.max(ans, lis[i]+lds[i]-1);
            }
            System.out.println(ans);
        }
    }
}
