import java.util.GregorianCalendar;
import java.util.Scanner;

public class Y3K {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String[] arr = scanner.nextLine().split("\\s+");
            int n = Integer.parseInt(arr[0]);
            int day = Integer.parseInt(arr[1]);
            int month = Integer.parseInt(arr[2]);
            int year = Integer.parseInt(arr[3]);
            if (n==0 && year==0 && month== 0&& day==0) break;
            GregorianCalendar calendar = new GregorianCalendar(year, month-1, day, 0, 0);
            calendar.add(GregorianCalendar.DAY_OF_MONTH, n);
            System.out.println(calendar.get(GregorianCalendar.DAY_OF_MONTH) + " " + (calendar.get(GregorianCalendar.MONTH)+1) + " " + calendar.get(GregorianCalendar.YEAR));
        }
    }
}
