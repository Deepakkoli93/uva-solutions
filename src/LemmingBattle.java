import java.util.*;

public class LemmingBattle {

    private static String solve(PriorityQueue<Integer> pq_g, PriorityQueue<Integer> pq_b, int fields) {
        while(true) {
            int[] greens = new int[fields];
            int[] blues = new int[fields];
            for(int i=0; i< fields; i++) {
                if (!pq_g.isEmpty()) {
                    greens[i] = pq_g.poll();
                } else {
                    greens[i] = 0;
                }
                if (!pq_b.isEmpty()) {
                    blues[i] = pq_b.poll();
                } else {
                    blues[i] = 0;
                }
                if(greens[i] >= blues[i]) {
                    greens[i] -= blues[i];
                    blues[i] = 0;
                } else {
                    blues[i] -= greens[i];
                    greens[i] = 0;
                }
            }
            for(int i=0; i<fields; i++) {
                if (greens[i] != 0) pq_g.add(greens[i]);
                if (blues[i] != 0) pq_b.add(blues[i]);
            }
            if (pq_b.isEmpty() && pq_g.isEmpty()) {
                return "green and blue died";
            } else if (pq_b.isEmpty()) {
                return "green wins";
            } else if (pq_g.isEmpty()) {
                return "blue wins";
            }
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        for(int i=0; i<n; i++) {
            String[] arr = scanner.nextLine().split("\\s+");
            int fields = Integer.parseInt(arr[0]);
            int num_green = Integer.parseInt(arr[1]);
            int num_blue = Integer.parseInt(arr[2]);
            PriorityQueue<Integer> pq_g = new PriorityQueue<>(num_green, Comparator.reverseOrder());
            PriorityQueue<Integer> pq_b = new PriorityQueue<>(num_blue, Comparator.reverseOrder());
            for(int j=0; j<num_green; j++) {
                pq_g.add(Integer.parseInt(scanner.nextLine()));
            }
            for(int j=0; j<num_blue; j++) {
                pq_b.add(Integer.parseInt(scanner.nextLine()));
            }

            String str = solve(pq_g, pq_b, fields);
            if (str.equalsIgnoreCase("green and blue died")) {
                System.out.println(str);
            } else {
                System.out.println(str);
                PriorityQueue<Integer> rem = pq_g;
                if (pq_g.isEmpty()) rem  = pq_b;
                while(!rem.isEmpty()) {
                    System.out.println(rem.poll());
                }
            }
            if (i != n-1) {
                System.out.println();
            }

        }
    }
}
