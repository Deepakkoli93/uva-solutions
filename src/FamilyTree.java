import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FamilyTree {

    private static int getLevel(int n) {
        int level = 0;
        while(n > 0) {
            n = n/2;
            level++;
        }
        return level;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine());
        while(t-->0) {
            String[] arr = reader.readLine().split("\\s+");
            int n = Integer.parseInt(arr[0]);
            int a = Integer.parseInt(arr[1]);
            int b = Integer.parseInt(arr[2]);
            long total = (long) Math.pow(2, n) - 1;
            int big = Math.max(a, b);
            int level = getLevel(big) - 1;
            int height = n - level;
            long ans = total - ((long) Math.pow(2, height) - 2);
            System.out.println(ans);
        }
    }
}
