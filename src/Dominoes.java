import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Dominoes {

    private static boolean dfs(int i, int[]l, int[] r, int[] x, int[] y, int m, int n, int[] last, boolean[] selected) {
        if (i == n) {
            return last[1] == r[0];
        } else {
            int[] prevLast = new int[2];
            prevLast[0] = last[0];
            prevLast[1] = last[1];
            for(int j=0; j<m; j++) {
                if (!selected[j] && x[j] == last[1]) {
                    selected[j] = true;
                    int[] newLast = new int[2];
                    newLast[0] = x[j];
                    newLast[1] = y[j];
                    boolean ans = dfs(i+1, l, r, x, y, m, n, newLast, selected);
                    if (ans) return true;

                    selected[j] = false;
                    last[0] = prevLast[0];
                    last[1] = prevLast[1];
                } else if (!selected[j] && y[j] == last[1]) {
                    selected[j] = true;
                    int[] newLast = new int[2];
                    newLast[0] = y[j];
                    newLast[1] = x[j];
                    boolean ans = dfs(i+1, l, r, x, y, m, n, newLast, selected);
                    if (ans) return true;

                    selected[j] = false;
                    last[0] = prevLast[0];
                    last[1] = prevLast[1];
                }
            }
            return false;
        }
    }

    private static boolean solve(int[]l, int[] r, int[] x, int[] y, int m, int n) {
        boolean[] selected = new boolean[m];

        boolean ans = dfs(0, l, r, x, y, m, n, l, selected);
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            int n = Integer.parseInt(reader.readLine());
            if (n==0) break;
            int m = Integer.parseInt(reader.readLine());
            int[] l = new int[2];
            String[] arr = reader.readLine().split("\\s+");
            l[0] = Integer.parseInt(arr[0]);
            l[1] = Integer.parseInt(arr[1]);
            arr = reader.readLine().split("\\s+");
            int[] r = new int[2];
            r[0] = Integer.parseInt(arr[0]);
            r[1] = Integer.parseInt(arr[1]);
            int[] x = new int[m];
            int[] y = new int[m];
            for(int i=0;i<m;i++) {
                arr = reader.readLine().split("\\s+");
                x[i] = Integer.parseInt(arr[0]);
                y[i] = Integer.parseInt(arr[1]);
            }
            boolean ans = solve(l, r, x, y, m, n);
            if (ans)
            System.out.println("YES");
            else
                System.out.println("NO");
        }
    }
}
