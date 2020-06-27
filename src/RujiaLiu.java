import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class RujiaLiu {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while((line = reader.readLine()) != null) {
            String[] arr = line.split("\\s+");
            int n = Integer.parseInt(arr[0]);
            int m = Integer.parseInt(arr[1]);
            Map<String, List<Integer>> map = new HashMap<>();
            StringTokenizer st = new StringTokenizer(reader.readLine());
            for(int i=0; i<n; i++) {
//                int num = Integer.parseInt(arr1[i]);
                String s = st.nextToken();
                if (!map.containsKey(s)) {
                    map.put(s, new ArrayList<>());
                }
                map.get(s).add(i+1);
            }
            for(int i=0; i<m; i++) {
                String[] arr2 = reader.readLine().split("\\s+");
                int k = Integer.parseInt(arr2[0]);
                String v = arr2[1];
                if (!map.containsKey(v)) {
                    System.out.println("0");
                } else if (map.get(v).size() < k){
                    System.out.println("0");
                } else {
                    System.out.println(map.get(v).get(k-1));
                }
            }
        }
    }
}
