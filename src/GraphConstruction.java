import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GraphConstruction {

    private static boolean solve(List<Integer> list) {
        list.sort(Comparator.reverseOrder());
        long sum = 0;
        for(int i : list) {
            sum += i;
        }
        if (sum % 2 != 0) {
            return false;
        }

        List<Long> prefix = new ArrayList<>();
        int n = list.size();
        prefix.add(new Long(list.get(0)));
        for(int i=1; i<n; i++) {
            prefix.add(prefix.get(i-1) + list.get(i));
        }
        prefix.add(0, 0L);
        list.add(0, 0);
        for(int k=1; k<=n; k++) {
            long rhs = 0;
            for(int i=k+1; i<=n; i++) {
                rhs += Math.min(list.get(i), k);
            }
            if (prefix.get(k) > k*(k-1) + rhs) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String[] arr = reader.readLine().split(" ");
            if(arr[0].equalsIgnoreCase("0")) break;
            List<Integer> list = new ArrayList<>();
            int n = Integer.parseInt(arr[0]);
            for(int i=1; i<=n; i++) {
                list.add(Integer.parseInt(arr[i]));
            }
            boolean ans = solve(list);
            if (ans) {
                System.out.println("Possible");
            } else {
                System.out.println("Not possible");
            }
        }
    }
}
