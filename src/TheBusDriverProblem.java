import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TheBusDriverProblem {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String[] arr = reader.readLine().split("\\s+");
            int n = Integer.parseInt(arr[0]);
            int d = Integer.parseInt(arr[1]);
            int r = Integer.parseInt(arr[2]);
            if(n==0 && d==0 && r==0) break;
            arr = reader.readLine().split("\\s+");
            List<Integer> arr1 = new ArrayList<>();
            for(int i=0;i<n;i++) {
                arr1.add(Integer.parseInt(arr[i]));
            }
            arr = reader.readLine().split("\\s+");
            List<Integer> arr2 = new ArrayList<>();
            for(int i=0;i<n;i++) {
                arr2.add(Integer.parseInt(arr[i]));
            }

            arr1.sort(Integer::compareTo);
            arr2.sort(Comparator.reverseOrder());
            int ans = 0;
            for(int i=0;i<n;i++) {
                int sum = arr1.get(i) + arr2.get(i);
                if (sum > d) {
                    ans += (r * (sum - d));
                }
            }
            System.out.println(ans);
        }
    }
}
