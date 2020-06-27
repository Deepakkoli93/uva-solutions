import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class DivisibleGroupSums {

    private static long dfs(int[] arr, int n, int curr, long sum, int d, int m, int selected, Map<String, Long> memo) {
        if (memo.containsKey(curr+"-"+sum+"-"+selected)) {
//            System.out.println("From memo");
            return memo.get(curr+"-"+sum+"-"+selected);
        }
        if (selected == m) {
            if (sum % d == 0) {
                return 1;
            } else {
                return 0;
            }
        } else {
            if (curr >= n) {
                return 0;
            } else {
                long ans = dfs(arr, n, curr+1, (((sum+arr[curr] % d ) + d )%d), d, m, selected+1, memo) +
                        dfs(arr, n, curr+1, sum, d, m, selected, memo);
                memo.put(curr+"-"+sum+"-"+selected, ans);
                return ans;
            }
        }
    }

    private static long solve(int[] arr, int n, int d, int m) {
        Map<String, Long> memo = new HashMap<>();
        return dfs(arr, n, 0, 0, d, m, 0, memo);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int setCount = 0;
        while(true) {
            String[] arr = reader.readLine().split("\\s+");
            int n = Integer.parseInt(arr[0]);
            int q = Integer.parseInt(arr[1]);
            setCount++;
            if(n==0 && q==0) break;
            int[] nums = new int[n];
            for(int i=0;i<n;i++) {
                nums[i] = Integer.parseInt(reader.readLine().trim());
            }
            StringBuilder str = new StringBuilder();
            str.append("SET " + setCount + ":\n");
            for(int i=0; i<q;i++) {
                arr = reader.readLine().split("\\s+");
                int d = Integer.parseInt(arr[0]);
                int m = Integer.parseInt(arr[1]);
                long ans = solve(nums, n, d, m);
                str.append("QUERY " + (i+1) + ": " + ans + '\n');
            }
            System.out.print(str.toString());
        }
    }
}
