import java.util.Scanner;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class Chess {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = Integer.parseInt(scanner.nextLine());
        for(int z=0; z<t; z++) {
            String[] arr = scanner.nextLine().split(" ");
            int x = Integer.parseInt(arr[1]);
            int y = Integer.parseInt(arr[2]);
            int m = max(x,y);
            int n = min(x,y);
            switch (arr[0]) {
                case "r":
                    System.out.println(min(m, n));
                    break;
                case "k":
                    if (n == 1) System.out.println(m);
                    else if (n == 2) System.out.println((int)((m/4) * 4 + Math.min((m%4) * 2, 4)));
                    else System.out.println((int)(Math.ceil(m/2.0) * Math.ceil(n/2.0) + Math.floor(m/2.0) * Math.floor(n/2.0)));
                    break;
                case "Q":
                    System.out.println(n);
                    break;
                case "K":
                    System.out.println((int)(Math.ceil(m/2.0) * Math.ceil(n/2.0)));
                    break;
            }
        }
    }
}
