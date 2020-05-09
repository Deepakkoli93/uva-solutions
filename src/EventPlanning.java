import java.util.Scanner;

import static java.lang.Integer.min;


public class EventPlanning {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            int ans;

            String[] s = scanner.nextLine().split(" ");
            int n = Integer.parseInt(s[0]);
//            System.out.println("here");
            int b = Integer.parseInt(s[1]);
//            System.out.println("here");
            ans = b + 1;
            int h = Integer.parseInt(s[2]);
//            System.out.println("here");
            int w = Integer.parseInt(s[3]);
//            System.out.println("here");
            for (int i = 0; i < h; i++) {
                int p = Integer.parseInt(scanner.nextLine());
                String[] beds = scanner.nextLine().split(" ");
                for (String bed : beds) {
                    int availableBeds = Integer.parseInt(bed);
                    if (availableBeds >= n) {
                        ans = min(ans, n * p);
                    }
                }
            }
            if (ans <= b) {
                System.out.println(ans);
            } else {
                System.out.println("stay home");
            }
        }
    }
}
