import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class CrimeWave {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String[] arr = reader.readLine().split("\\s+");
            int n = Integer.parseInt(arr[0]);
            int m = Integer.parseInt(arr[1]);
            if(n==0 && m==0) break;
            double[][] cost = new double[n+m+2][n+m+2];
            for(double[] row: cost) Arrays.fill(row, Integer.MAX_VALUE);
            // start = n+m
            // end = n+m+1
            for(int i=0; i<)
            for(int i=0; i<n; i++) {
                arr = reader.readLine().split("\\s+");
                for(int j=0; j<m; j++) {

                }
            }
        }
    }
}
