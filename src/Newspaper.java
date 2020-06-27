import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Newspaper {

    private static String fmt(long cents) {
        long dollars = cents/100;
        cents = cents % 100;
        String x = String.valueOf(dollars);
        String y = String.valueOf(cents);
        if (cents < 10) {
            y = "0"+y;
        }
        return x+"."+y+"$";
    }
    public static void main(String[] args) throws IOException {
//        Scanner scanner = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(reader.readLine());
        while(n-- > 0) {
            int k = Integer.parseInt(reader.readLine());
            Map<Character, Integer> map = new HashMap<>();
            while(k-- > 0) {
                String[] arr = reader.readLine().split(" ");;
                map.put(arr[0].charAt(0), Integer.parseInt(arr[1]));
            }
            int m = Integer.parseInt(reader.readLine());
            long cents = 0;
            while(m-- > 0) {
                String line = reader.readLine();
                for(Character c: line.toCharArray()) {
                    cents += map.getOrDefault(c, 0);
                }
            }
            System.out.println(fmt(cents));
        }
    }
}
