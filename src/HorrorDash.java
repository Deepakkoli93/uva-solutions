import java.util.Scanner;

public class HorrorDash {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        for(int i=0; i<n; i++) {
            String[] arr = scanner.nextLine().split(" ");
            int ans = Integer.parseInt(arr[0]);
            for(String a: arr) {
                ans = Math.max(ans, Integer.parseInt((a)));
            }
            System.out.println("Case " + (i+1) + ": " + ans);
        }
    }
}
