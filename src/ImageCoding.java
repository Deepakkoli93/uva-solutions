import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ImageCoding {
    private static int solve(Map<Character, Integer> map, int m, int n) {
        int maxVal = 0;
        for(int val: map.values()) {
            maxVal = Math.max(maxVal, val);
        }
        int ans = 0;
        for(int val: map.values()) {
            if (val == maxVal) {
                ans += m*val;
            } else {
                ans += n*val;
            }
        }
        return ans;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int x = Integer.parseInt(reader.readLine());
        for(int t=1; t<=x; t++){
            String[] arr = reader.readLine().split(" ");
            int r = Integer.parseInt(arr[0]);
            int c = Integer.parseInt(arr[1]);
            int m = Integer.parseInt(arr[2]);
            int n = Integer.parseInt(arr[3]);
            Map<Character, Integer> map = new HashMap<>();
            while(r-- > 0) {
                char[] carr = reader.readLine().toCharArray();
                for(char ch: carr) {
                    map.put(ch, map.getOrDefault(ch, 0) + 1);
                }
            }
            int ans = solve(map, m, n);
            System.out.println("Case " + t +": " + ans);

        }
    }
}
