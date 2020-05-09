import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ICanGuess {
    private static String solve(boolean[] ans) {
        if (!ans[0] && !ans[1] && !ans[2]) {
            return "impossible";
        }
        int trueCount = 0;
        if (ans[0]) trueCount++;
        if (ans[1]) trueCount++;
        if (ans[2]) trueCount++;
        if(trueCount > 1) {
            return "not sure";
        }

        if(ans[0]) return "stack";
        if (ans[1]) return "queue";
        if (ans[2]) return "priority queue";
        return "";
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while((line = reader.readLine()) != null) {
            int n = Integer.parseInt(line);
            Stack<Integer> st = new Stack<>();
            Queue<Integer> q = new ArrayDeque<>();
            PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
            boolean[] ans = {true, true, true};
            while(n-- > 0) {
                String[] arr = reader.readLine().split("\\s+");
                int act = Integer.parseInt(arr[0]);
                int num = Integer.parseInt(arr[1]);
                if (act == 1) {
                    st.push(num);
                    q.add(num);
                    pq.add(num);
                } else if (act == 2) {
                    if (ans[0]) {
                        if (st.isEmpty()) {
                            ans[0] = false;
                        } else {
                            if (st.pop() != num) {
                                ans[0] = false;
                            }
                        }
                    }

                    if (ans[1]) {
                        if (q.isEmpty()) {
                            ans[1] = false;
                        } else {
                            if (q.poll() != num) {
                                ans[1] = false;
                            }
                        }
                    }

                    if (ans[2]) {
                        if (pq.isEmpty()) {
                            ans[2] = false;
                        } else {
                            if (pq.poll() != num) {
                                ans[2] = false;
                            }
                        }
                    }
                }
            }
            System.out.println(solve(ans));
        }
    }
}
