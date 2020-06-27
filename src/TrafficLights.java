import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TrafficLights {

    private static String getF(int n) {
        if (n == -1) {
            return "Signals fail to synchronise in 5 hours";
        }
        int seconds;

        if(n>=60) {
         seconds=n % 60;
        } else {
            seconds = n;
        }
        n = n/60;
        int minutes;
        if (n>=60) {
            minutes = n % 60;
        } else {
            minutes = n;
        }
        n=n/60;


        int hours = n;

        String secString = String.valueOf(seconds);
        if (seconds <10) secString = "0"+secString;

        String minString = String.valueOf(minutes);
        if(minutes < 10) minString = "0"+minString;

        String hrString = String.valueOf(hours);
        if(hours<10) hrString = "0"+hrString;
        return hrString+":"+minString+":"+secString;

    }

    private static int solve(List<Integer> cycles) {
        int n = cycles.size();
        int limit = 5 * 60 * 60;
        List<Integer> arrivals = new ArrayList<>();
        int i = 0;
        while(true) {
            boolean isLimitReached = true;
            for(int cycle: cycles) {
                int num = i * 2 * cycle;
                if (num <= limit){
                    isLimitReached = false;
                    arrivals.add(num);
                }
            }
            if(isLimitReached) break;
            i++;
        }

        List<Integer> exits = new ArrayList<>();
        i = 0;
        while(true) {
            boolean isLimitReached = true;
            for(int cycle: cycles) {
                int num = i * 2 * cycle + (cycle-5);
                if (num <= limit){
                    isLimitReached = false;
                    exits.add(num);
                }
            }
            if(isLimitReached) break;
            i++;
        }
        arrivals.sort(Integer::compare);
        exits.sort(Integer::compare);
        arrivals.remove(0);
//        System.out.println("arr last " + arrivals.get(arrivals.size()-1));
//        System.out.println("exit last " + exits.get(exits.size()-1));
        exits.remove(0);
//        int firstExit = exits.get(0);
//        exits.remove(0);
//        int z = 0;
//        while(arrivals.get(z) <= firstExit) {
//            z++;
//        }
//        arrivals = arrivals.subList(z, arrivals.size());
        int overlap = 0;
        i=0;
        int j=0;
        int n1 = arrivals.size();
        int n2 = exits.size();
        while(i<n1 && j<n2) {
            if (arrivals.get(i) < exits.get(j)) {
                overlap++;
                i++;
            } else {
                overlap--;
                j++;
            }
            if (overlap == n) break;
        }

        if (overlap == n) return arrivals.get(i-1);
        else if (i<n1) {
            while(i<n1) {
                overlap++;
                i++;
                if (overlap == n) return arrivals.get(i - 1);
            }
            if (overlap == n) return arrivals.get(i - 1);
            else return -1;
        }
        else return -1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextInt()) {

            List<Integer> cycles = new ArrayList<>();
            while(true) {
                int n = scanner.nextInt();
                if (n != 0) {
                    cycles.add(n);
                } else {
                    break;
                }
            }

            if (cycles.size() == 0) break;

            int ans = solve(cycles);
            System.out.println(getF(ans));
        }

    }
}
