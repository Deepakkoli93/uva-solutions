import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Rails {
    private static String solve(int n, List<Integer> list) {
        Stack<Integer> stack = new Stack<>();
        int i = 0; // till n current max
        int j = 0; // indexing into list
        while(j < n) {
            if (!stack.empty() && stack.peek().equals(list.get(j))) {
                stack.pop();
                j++;
            } else {
                if (list.get(j) <= i) {
                    return "No";
                } else {
                    while(i < list.get(j)) {
                        i++;
                        stack.push(i);
                    }
                }
            }
        }
        return "Yes";
    }
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            int n = Integer.parseInt(reader.readLine());
            if (n==0) break;
            while(true) {
                String line = reader.readLine();
                if (line.equalsIgnoreCase("0")) break;
                String[] arr = line.split("\\s+");
                List<Integer> list = new ArrayList<>();
                for(String x: arr) list.add(Integer.parseInt(x));
                System.out.println(solve(n, list));
            }
            System.out.println();
        }
    }
}
