import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hardwood {

    private static void solve(Map<String, Integer> map, int total) {
        List<String> keys = new ArrayList<>(map.keySet());
        keys.sort(String::compareTo);
        for(String key: keys) {
            double perc = map.get(key) * 100.0/total;
            System.out.println(key + " " + String.format("%.4f", perc));
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());
        reader.readLine();
        while(n-- > 0) {
            Map<String, Integer> map = new HashMap<>();
            int total = 0;
            while(true) {
                String line = reader.readLine();
                if (line == null || line.isEmpty()) break;
                total++;
                map.put(line, map.getOrDefault(line, 0) + 1);
            }
            solve(map, total);
            if(n!=0){
                System.out.println();
            }
        }
    }
}
