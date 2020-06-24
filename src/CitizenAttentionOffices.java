import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class CitizenAttentionOffices {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine());
        while (t-- > 0) {
            int[][] arr = new int[5][5];
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    arr[i][j] = 0;
                }
            }
            int n = Integer.parseInt(reader.readLine());
            while (n-- > 0) {
                String[] arr1 = reader.readLine().split("\\s+");
                int i = Integer.parseInt(arr1[0]);
                int j = Integer.parseInt(arr1[1]);
                arr[i][j] = Integer.parseInt(arr1[2]);
            }
            int minDist = Integer.MAX_VALUE;
            int[] ans = new int[]{-1, -1, -1, -1, -1};
            for (int i = 0; i < 25; i++) {
                for (int j = i + 1; j < 25; j++) {
                    for (int k = j + 1; k < 25; k++) {
                        for (int l = k + 1; l < 25; l++) {
                            for (int m = l + 1; m < 25; m++) {
                                int total = 0;
                                for (int x = 0; x < 5; x++) {
                                    for (int y = 0; y < 5; y++) {
                                        List<Integer> distArr = Arrays.asList(
                                                (Math.abs(i / 5 - x) + Math.abs(i % 5 - y)) * arr[x][y],
                                                (Math.abs(j / 5 - x) + Math.abs(j % 5 - y)) * arr[x][y],
                                                (Math.abs(k / 5 - x) + Math.abs(k % 5 - y)) * arr[x][y],
                                                (Math.abs(l / 5 - x) + Math.abs(l % 5 - y)) * arr[x][y],
                                                (Math.abs(m / 5 - x) + Math.abs(m % 5 - y)) * arr[x][y]);
                                        int minHere = distArr.get(0);
                                        for (int z = 1; z < 5; z++) {
                                            if (distArr.get(z) < minHere) {
                                                minHere = distArr.get(z);
                                            }
                                        }
                                        total += minHere;
                                    }
                                }
                                if (total < minDist) {
                                    minDist = total;
                                    ans[0] = i;
                                    ans[1] = j;
                                    ans[2] = k;
                                    ans[3] = l;
                                    ans[4] = m;
                                }
                            }
                        }

                    }
                }
            }
            System.out.println(ans[0] + " " + ans[1] + " " + ans[2] + " " + ans[3] + " " + ans[4]);
        }
    }
}
