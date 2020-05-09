import java.util.GregorianCalendar;
import java.util.Scanner;

public class CancerOrScorpio {

    private static String getSign(int month, int day){
        if (month == 1) {
            if (day<=20) return "capricorn";
            else return "aquarius";
        } else if (month == 2) {
            if (day<=19) return "aquarius";
            else return "pisces";
        } else if (month == 3) {
            if (day<=20) return "pisces";
            else return "aries";
        } else if (month == 4) {
            if (day<=20) return "aries";
            else return "taurus";
        } else if (month == 5) {
            if (day<=21) return "taurus";
            else return "gemini";
        } else if (month == 6) {
            if (day<=21) return "gemini";
            else return "cancer";
        } else if (month == 7) {
            if (day<=22) return "cancer";
            else return "leo";
        } else if (month == 8) {
            if (day<=21) return "leo";
            else return "virgo";
        } else if (month == 9) {
            if (day<=23) return "virgo";
            else return "libra";
        } else if (month == 10) {
            if (day<=23) return "libra";
            else return "scorpio";
        } else if (month == 11) {
            if (day<=22) return "scorpio";
            else return "sagittarius";
        } else if (month == 12) {
            if (day<=22) return "sagittarius";
            else return "capricorn";
        }
        return null;
    }

    private static String getMonth(int m) {
        if (m>=10) {
            return String.valueOf(m);
        } else {
            return "0"+ m;
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count =Integer.parseInt(scanner.nextLine());
        for(int i=1; i<=count; i++) {

            String arr = scanner.nextLine();
            int month = Integer.parseInt(arr.substring(0, 2));
            int day = Integer.parseInt(arr.substring(2, 4));
            int year = Integer.parseInt(arr.substring(4));
            GregorianCalendar calendar = new GregorianCalendar(year, month-1, day, 0, 0);
            calendar.add(GregorianCalendar.DAY_OF_MONTH, 40*7);
            System.out.println(i + " " + getMonth(calendar.get(GregorianCalendar.MONTH)+1) + "/"+getMonth(calendar.get(GregorianCalendar.DAY_OF_MONTH)) + "/" +
                    calendar.get(GregorianCalendar.YEAR) + " " + getSign(calendar.get(GregorianCalendar.MONTH)+1, calendar.get(GregorianCalendar.DAY_OF_MONTH)));
        }
    }
}
