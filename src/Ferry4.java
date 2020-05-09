import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Ferry4 {

    private static boolean shouldChangeShore(String currentShore, Queue<Car> left, Queue<Car> right) {
        if (currentShore.equalsIgnoreCase("left")) {
            return left.isEmpty();
        } else {
            return right.isEmpty();
        }
    }

    private static String changeShore(String currentShore) {
        if (currentShore.equalsIgnoreCase("left")) {
            return "right";
        } else {
            return "left";
        }
    }

    private static Queue<Car> getCurrQ(String currentShore, Queue<Car> left, Queue<Car> right) {
        if (currentShore.equalsIgnoreCase("left")) {
            return left;
        } else {
            return right;
        }
    }

    private static int solve(Queue<Car> left, Queue<Car> right, int l) {
        int crossed = 0;
        String currentShore = "left";
        Queue<Car> currQ = left;
        Queue<Car> ferry = new ArrayDeque<>();
        while(!left.isEmpty() || !right.isEmpty()) {
            if (shouldChangeShore(currentShore, left, right)) {
                currentShore = changeShore(currentShore);
                currQ = getCurrQ(currentShore, left, right);
                crossed++;
            } else {

                // loading
                int ferryload = 0;
                while(!currQ.isEmpty() && ferryload + currQ.peek().len <= l) {
                    ferryload += currQ.peek().len;
                    ferry.add(currQ.poll());
                }

                // move across
                crossed++;
                currentShore = changeShore(currentShore);
                currQ = getCurrQ(currentShore, left, right);

                // unload
                while(!ferry.isEmpty()) {
                    ferry.poll();
                }
            }
        }
        return crossed;
    }

    static class Car {
        int len;
        Car(int l) {
            this.len = l;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int c = Integer.parseInt(reader.readLine());
        while(c-- > 0) {
            String[] arr = reader.readLine().split("\\s+");
            int l = Integer.parseInt(arr[0]) * 100;
            int m = Integer.parseInt(arr[1]);
            Queue<Car> left = new ArrayDeque<>();
            Queue<Car> right = new ArrayDeque<>();
            while(m-- > 0) {
                String[] arr1 = reader.readLine().split("\\s+");
                if(arr1[1].equalsIgnoreCase("left")) {
                    left.add(new Car(Integer.parseInt(arr1[0])));
                } else {
                    right.add(new Car(Integer.parseInt(arr1[0])));
                }
            }
            System.out.println(solve(left, right, l));
        }
    }
}
