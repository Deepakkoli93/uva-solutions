import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MaximumSum2 {

    private static String solve(int[] arr) {
        int n = arr.length;

        List<Integer> list = new ArrayList<>();
        for(int i=0;i<n;i++) {
            if (arr[i] != 0) {
                list.add(arr[i]);
            }
        }
        if (list.isEmpty()) list.add(0);
        StringBuilder str = new StringBuilder();
        for(int i=0;i<list.size();i++) {
            str.append(list.get(i));
            if (i != list.size()-1) {
                str.append(" ");
            }
        }
        return str.toString();
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            int n = Integer.parseInt(reader.readLine());
            if (n==0) break;
            int[] arr = new int[n];
            for(int i=0;i<n;i++) {
                arr[i] = Integer.parseInt(reader.readLine());
            }
            System.out.println(solve(arr));

        }
    }
}
