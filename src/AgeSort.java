import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class AgeSort {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            int n = Integer.parseInt(reader.readLine());
            if (n == 0) break;
            String[] arr = reader.readLine().split("\\s+");
            Map<Integer, Integer> map = new HashMap<>();
            for(String s: arr) {
                int num = Integer.parseInt(s);
                map.put(num, map.getOrDefault(num, 0) + 1);
            }
            StringBuilder ans = new StringBuilder();

            for(int i=0; i<=100; i++) {
                if (map.getOrDefault(i, 0) > 0) {
                    int repeat = map.get(i);
                    while(repeat-- > 0) {
                        ans.append(i);
                        ans.append(" ");
                    }
                }
            }
            System.out.println(ans.toString().replaceAll("\\s+$", ""));
        }
    }
}
