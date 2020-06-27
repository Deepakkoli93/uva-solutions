import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Matrioshka {

    private static boolean solve(List<Integer> list) {
        int i = 0;
        Stack<Integer> s1 = new Stack<>();
        Stack<Integer> s2 = new Stack<>();
        int n = list.size();
        while (i < n) {
            if (list.get(i) < 0) {
                s1.push(list.get(i));
                s2.push(list.get(i));
            } else {
                if (s1.empty() || !s1.peek().equals(0-list.get(i))) {
                    return false;
                } else {
                    int val = 0 - s1.pop();
                    int count = 0;
                    while (!s2.empty() && !s2.peek().equals(0-list.get(i))) {
                        count += s2.pop();
                    }
                    if (count >= Math.abs(val)) {
                        return false;
                    }
                    s2.pop();
                    s2.push(Math.abs(val));
                }
            }
            i++;
        }
        if (!s1.empty()) {
            return false;
        } else {
            return true;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                System.out.println(":-) Matrioshka!");
                continue;
            }
            String[] arr = line.split("\\s+");
            List<Integer> list = new ArrayList<>();
            for(String x: arr) list.add(Integer.parseInt(x));
            boolean ans = solve(list);
            if (ans) {
                System.out.println(":-) Matrioshka!");
            } else {
                System.out.println(":-( Try again.");
            }
        }
    }
}
