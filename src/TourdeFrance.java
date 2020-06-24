import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TourdeFrance {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String s = reader.readLine();
            if ("0".equalsIgnoreCase(s)) {
                break;
            }

            String[] arr = s.split("\\s+");
            List<Integer> front = new ArrayList<>();
            List<Integer> back = new ArrayList<>();
            arr = reader.readLine().split("\\s+");
            for(int i=0; i<arr.length;i++) {
                front.add(Integer.parseInt(arr[i]));
            }
            arr = reader.readLine().split("\\s+");
            for(int i=0;i<arr.length;i++) {
                back.add(Integer.parseInt(arr[i]));
            }
            List<Double> list = new ArrayList<>();
            for(int f: front) {
                for(int b:back) {
                    list.add(b*1.0/f);
                }
            }
            list.sort(Double::compareTo);
            Double ans = list.get(1) / list.get(0);
            for(int i=0; i < list.size() - 1; i++) {
                ans = Math.max(ans, list.get(i+1) / list.get(i));
            }
            System.out.printf("%.2f\n", ans);
        }
    }
}
