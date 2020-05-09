import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class AddAll {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            int n = Integer.parseInt(reader.readLine());
            if (n==0) break;
            String[] arr = reader.readLine().split("\\s+");
            PriorityQueue<Long> q = new PriorityQueue<>();
            for(String s: arr) {
                q.add(Long.parseLong(s));
            }
            long ans = 0;
            while(q.size() > 1) {
                long x = q.poll();
                long y = q.poll();
                ans += x + y;
                q.add(x+y);
            }
            System.out.println(ans);
        }
    }
}
