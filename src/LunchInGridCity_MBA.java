import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LunchInGridCity_MBA {
    private static int fixRow(int f, int r, int c) {
        if (f < 1) return 1;
        if (f > r) return r;
        return f;
    }

    private static int fixCol(int f, int r, int c) {
        if (f < 1) return 1;
        if (f > c) return c;
        return f;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine());
        while (t-- > 0) {
            String[] arr = reader.readLine().split("\\s+");
            int r = Integer.parseInt(arr[0]);
            int c = Integer.parseInt(arr[1]);
            int f = Integer.parseInt(arr[2]);

            List<Integer> x = new ArrayList<>();
            List<Integer> y = new ArrayList<>();
            for (int i = 0; i < f; i++) {
                String[] arr1 = reader.readLine().split("\\s+");
                x.add(Integer.parseInt(arr1[0]));
                y.add(Integer.parseInt(arr1[1]));
            }
            x.sort(Integer::compareTo);
            y.sort(Integer::compareTo);
            if (f % 2 == 1) {
                System.out.println("(Street: " + fixRow(x.get(f / 2), r, c) + ", Avenue: " + fixCol(y.get(f / 2), r, c) + ")");
            } else {
                System.out.println("(Street: " + fixRow(x.get(Math.max(0, f / 2 - 1)), r, c) + ", Avenue: " + fixCol(y.get(Math.max(0, f / 2 - 1)), r, c) + ")");
            }
        }
    }
}