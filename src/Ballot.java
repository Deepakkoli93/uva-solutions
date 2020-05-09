import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ballot {

    private static boolean solve(Map<String, Double> map, String q, int t) {
        String[] arr = q.split("\\s+\\+\\s+");
        int len = arr.length;
        double sum = 0;
        for(int i=0; i < len-1; i++) {
            sum += map.get(arr[i]);
        }
        String last = arr[len-1];
        Pattern pattern = Pattern.compile("([a-zA-Z0-9]+)\\s*(<=|=|>=|<|>)\\s*(\\d+)");
        Matcher matcher = pattern.matcher(last);
        matcher.matches();
        sum += map.get(matcher.group(1));
        String op = matcher.group(2);
//        if (t==112) {
//            System.out.println("op = " + op);
//        }
        int num = Integer.parseInt(matcher.group(3));

        if (op.equalsIgnoreCase("=")) {
//            if (t==46) {
//                System.out.println("sum = " + sum);
//                System.out.println("num ="+ num);
//            }
            return Math.abs(sum - num) < 0.0001;//Double.compare(sum, num) == 0;
//            return (int)sum == num;
        }
        if (op.equalsIgnoreCase("<=")) {
//                        if (t==112) {
//                System.out.println("sum = " + sum);
//                System.out.println("num ="+ num);
//            }
            return 0.0001 <= num - sum || Math.abs(sum - num) < 0.0001;
        }
        if (op.equalsIgnoreCase("<")) {
            return 0.0001 < num - sum;
        }
        if (op.equalsIgnoreCase(">=")) {
            return (sum - num) >= 0.0001 || Math.abs(sum - num) < 0.0001;
        }
        if (op.equalsIgnoreCase(">")) {
//                        if (t==96) {
//                System.out.println("sum = " + sum);
//                System.out.println("num ="+ num);
//            }
            return (sum - num) > 0.0001;
        }

        return false;
    }

    public static void main(String[] args) throws IOException {
//        String q = "FDP + SPD + CDU = 42";
//        String[] arr3 = q.split("\\s+\\+\\s+");
//        String[] arr4 = arr3[2].split("<|>|<=|>=|=");
//        Pattern pattern = Pattern.compile("([a-zA-Z0-9]+)\\s*(<=|=|>=|<|>)\\s*(\\d+)");
//        Matcher matcher = pattern.matcher(arr3[2]);
//        matcher.matches();
//        System.out.println("g1 " + matcher.group(1));
//        System.out.println("g2 " + matcher.group(2));
//        System.out.println("g3 " + matcher.group(3));
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] arr = reader.readLine().split("\\s+");
        int p = Integer.parseInt(arr[0]);
        int g = Integer.parseInt(arr[1]);
        Map<String, Double> map = new HashMap<>();
        while(p-- > 0) {
            String[] arr1 = reader.readLine().split("\\s+");
            map.put(arr1[0], Double.parseDouble(arr1[1]));
        }
        for(int i=1; i<=g ;i++) {
            String query = reader.readLine();
            boolean ans = solve(map, query, i);
            if(ans) {
                System.out.println("Guess #" + i + " was correct.");
            } else {
                System.out.println("Guess #" + i + " was incorrect.");
            }
        }
    }
}
