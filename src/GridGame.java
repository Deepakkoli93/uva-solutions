import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GridGame {

    private static boolean nextPerm(int[] arr) {
        int n = arr.length;
        int i = n-1;
        while(i>0 && arr[i] <= arr[i-1]) i--;
        if (i==0) return false;

        int nextBigger = n-1;
        while(arr[nextBigger] <= arr[i-1]) nextBigger--;

        int temp = arr[nextBigger];
        arr[nextBigger] = arr[i-1];
        arr[i-1] = temp;

        int j = n-1;
        while(i < j) {
            temp = arr[i];
            arr[i]=arr[j];
            arr[j]=temp;
            i++;
            j--;
        }

        return true;
    }
    private static int solve(int[][] grid, int n) {
        int[] cols = new int[n];
        for(int i=0;i<n;i++) {
            cols[i]=i;
        }
        int ans = Integer.MAX_VALUE;
        do {
            int total = 0;
            for(int row=0; row<n; row++) {
                total += grid[row][cols[row]];
            }
            ans = Math.min(ans, total);
        }while(nextPerm(cols));
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine());
        while (t-- > 0) {
            int n = Integer.parseInt(reader.readLine());
            int[][] grid = new int[n][n];
            for (int i = 0; i < n; i++) {
                String[] row = reader.readLine().split("\\s+");
                for (int j = 0; j < n; j++) {
                    grid[i][j] = Integer.parseInt(row[j]);
                }
            }
            System.out.println(solve(grid, n));
        }
    }
}
