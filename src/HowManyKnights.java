import java.util.Scanner;

public class HowManyKnights {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String[] arr = scanner.nextLine().split("\\s+");
            int x = Integer.parseInt(arr[0]);
            int y = Integer.parseInt(arr[1]);
            if (x==0 && y==0) break;

            int m = Math.max(x,y);
            int n = Math.min(x,y);
            int ans = 0;
            if (n == 1) ans = m;
            else if (n == 2) ans = (int)((m/4) * 4 + Math.min((m%4) * 2, 4));
            else ans = (int)(Math.ceil(m/2.0) * Math.ceil(n/2.0) + Math.floor(m/2.0) * Math.floor(n/2.0));

            System.out.println(ans + " knights may be placed on a " + x + " row " + y + " column board.");
        }
    }
}
