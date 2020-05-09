import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class LonesomeCargo {

    private static boolean isDone(Stack<Integer> st, List<Queue<Integer>> queues) {
        if (!st.isEmpty()) {
            return false;
        }
        for(Queue<Integer> q: queues) {
            if (!q.isEmpty()) {
                return false;
            }
        }
        return true;
    }
    private static int solve(List<Queue<Integer>> queues, int stackSize, int queueSize, int n) {
        Stack<Integer> st = new Stack<>();
        int time = 0;
        int currStation = 1;
        while(true) {
            // unload
//            while(!st.isEmpty() && st.peek() == currStation) {
//                time += 1;
//                st.pop();
//            }
//            while(!st.isEmpty() && queues.get(currStation).size() < queueSize) {
//                time += 1;
//                queues.get(currStation).add(st.pop());
//            }
            while(!st.isEmpty()) {
                if (st.peek() == currStation) {
                    time+= 1;
                    st.pop();
                } else {
                    if (queues.get(currStation).size() >= queueSize) {
                        break;
                    }
                    time += 1;
                    queues.get(currStation).add(st.pop());
                }
            }

            // load
            while(st.size() < stackSize && !queues.get(currStation).isEmpty()) {
                time += 1;
                st.push(queues.get(currStation).poll());
            }

            if (isDone(st, queues)) {
                break;
            }

            // move to next station
            if (currStation == n) {
                currStation = 1;
            } else {
                currStation++;
            }
            time += 2;
        }
        return time;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int set = Integer.parseInt(reader.readLine());
        while(set-- > 0) {
            String[] arr = reader.readLine().split("\\s+");
            int n = Integer.parseInt(arr[0]);
            int s = Integer.parseInt(arr[1]);
            int q = Integer.parseInt(arr[2]);
            List<Queue<Integer>> queues = new ArrayList<>();
            queues.add(new ArrayDeque<>()); // add dummy at index 0
            for(int xx=0; xx<n; xx++) {
                String[] arr1 = reader.readLine().split("\\s+");
                int size = Integer.parseInt(arr1[0]);
                Queue<Integer> queue = new ArrayDeque<>();
                for(int i=1; i<=size; i++) {
                    queue.add(Integer.parseInt(arr1[i]));
                }
                queues.add(queue);
            }
            System.out.println(solve(queues, s, q, n));

        }
    }
}
