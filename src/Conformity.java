import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Conformity {
    private static int solve(Map<String, Integer> map) {
        int maxFreq = 0;
        for(Map.Entry<String, Integer> entry: map.entrySet()) {
            maxFreq = Math.max(maxFreq, entry.getValue());
        }
        int ans = 0;
        for(Map.Entry<String, Integer> entry: map.entrySet()) {
            if (entry.getValue() == maxFreq) {
                ans+=entry.getValue();
            }
        }
        return ans;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            int n = Integer.parseInt(reader.readLine());
            if (n==0) break;
            Map<String, Integer> map = new HashMap<>();
            while(n-- > 0) {
                String[] arr = reader.readLine().split("\\s+");
                List<String> list = new ArrayList<>();
                Collections.addAll(list, arr);
                list.sort(String::compareTo);
                String key = String.join("", list);
                map.put(key, map.getOrDefault(key, 0) + 1);
            }
            System.out.println(solve(map));
        }
    }
}
