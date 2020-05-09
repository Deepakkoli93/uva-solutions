import java.util.Scanner;

public class ClockHands {

    private static String solve(int hour, int min) {
        double minDegrees = min * 6.0;
        double hrDegrees = hour * 5 * 6.0;
        double delHr = (min * 6) / 12.0 ;

        hrDegrees += delHr;
        double diff = Math.abs(minDegrees - hrDegrees);
        double ans = Math.min(diff, 360-diff);
        return String.format("%.3f", ans);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            String[] arr = scanner.nextLine().split(":");
            int hr = Integer.parseInt(arr[0]);
            int min = Integer.parseInt(arr[1]);
            if (hr==0 && min==0) break;
            String ans = solve(hr, min);
            System.out.println(ans);
        }
    }
}
