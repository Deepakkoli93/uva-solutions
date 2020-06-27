import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class BrokenPedometer {

    private static int countOnes(int num) {
        int ans = 0;
        while(num > 0) {
            ans += num & 1;
            num = num >> 1;
        }
        return ans;
    }

    private static int solve(Set<Integer> codes, int p) {
        int curr = 0;
        int end = (int) Math.pow(2, p);
        int ans = p;
        while(curr <= end) {
            Set<Integer> currSet = new HashSet<>();
            boolean allSat = true;
            for(int code: codes) {
                int rem = code & curr;
                if (currSet.contains(rem)) {
                    allSat = false;
                    break;
                } else {
                    currSet.add(rem);
                }
            }
            if (allSat) {
                ans = Math.min(ans, countOnes(curr));
            }
            curr++;
        }
        return ans;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine());
        while(t-- > 0) {
            int p = Integer.parseInt(reader.readLine());
            int n = Integer.parseInt(reader.readLine());
            Set<Integer> codes = new HashSet<>();
            while(n-- > 0) {
                String[] arr = reader.readLine().split("\\s+");
                int code = 0;
                for (int i = 0; i < arr.length; i++) {
                    int bit = Integer.parseInt(arr[i]);
                    code = code << 1;
                    code = code | bit;
                }
                codes.add(code);
//                System.out.println("code " + code);
            }
            System.out.println(solve(codes, p));
        }
    }
}
