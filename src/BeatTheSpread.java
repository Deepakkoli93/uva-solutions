import java.util.Scanner;

public class BeatTheSpread {

    private static String solve(long sum, long diff) {
        if ((sum+diff) %2 != 0) {
            return "impossible";
        }
        long a = (sum+diff)/2;
        long b = sum - a;
        if (a<0 || b<0) return "impossible";
        return ""+Math.max(a,b) + " " + Math.min(a,b);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        while(n-->0) {
         String[] arr = scanner.nextLine().split("\\s+");
         long sum = Long.parseLong(arr[0]);
         long diff = Long.parseLong(arr[1]);
         System.out.println(solve(sum, diff));
        }
    }
}
