import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Ferry3 {

    private static boolean shouldChangeShore(String currentShore, Queue<Car> left, Queue<Car> right, int time) {
        if (currentShore.equalsIgnoreCase("left")) {
            if (left.isEmpty()) {
                return true;
            } else {
                if (left.peek().entry <= time) {
                    return false;
                } else {
                    if (right.isEmpty()) {
                        return false;
                    } else {
                        return right.peek().entry < left.peek().entry;
                    }
                }
            }
        } else {
            if (right.isEmpty()) {
                return true;
            } else {
                if (right.peek().entry <= time) {
                    return false;
                } else {
                    if (left.isEmpty()) {
                        return false;
                    } else {
                        return left.peek().entry < right.peek().entry;
                    }
                }
            }
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

    private static List<Car> solve(Queue<Car> left, Queue<Car> right, int n, int t) {
        List<Car> allcars = new ArrayList<>();
        String currentShore = "left";
        Queue<Car> currQ = left;
        int time = 0;
        Queue<Car> ferry = new ArrayDeque<>();
        while(!left.isEmpty() || !right.isEmpty()) {
            if (shouldChangeShore(currentShore, left, right, time)) {
                currentShore = changeShore(currentShore);
                currQ = getCurrQ(currentShore, left, right);
                if (currQ.peek().entry <= time) {
                    time += t;
                } else {
                    time = currQ.peek().entry + t;
                }

            } else {
                // waiting at a shore
                if (!currQ.isEmpty()) {
                    if (time < currQ.peek().entry) {
                        time = currQ.peek().entry;
                    }
                }
                // loading
                while(!currQ.isEmpty() && currQ.peek().entry <= time && ferry.size() < n) {
                    ferry.add(currQ.poll());
                }

                // move across
                time += t;
                currentShore = changeShore(currentShore);
                currQ = getCurrQ(currentShore, left, right);

                // unload
                while(!ferry.isEmpty()) {
                    Car cc = ferry.poll();
                    cc.exit = time;
                    allcars.add(cc);
//                    ferry.poll().exit = time;
//                    System.out.println(time);
                }
            }
        }
        return allcars;
    }

    static class Car {
        int entry;
        int exit = -1;
        int input;
        Car(int e, int i) {
            this.entry = e;
            this.input = i;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int c = Integer.parseInt(reader.readLine());
        while(c-- > 0) {
            String[] arr = reader.readLine().split("\\s+");
            int n = Integer.parseInt(arr[0]);
            int t = Integer.parseInt(arr[1]);
            int m = Integer.parseInt(arr[2]);
            Queue<Car> left = new ArrayDeque<>();
            Queue<Car> right = new ArrayDeque<>();
            int inputTime = 0;
            while(m-- > 0) {
                String[] arr1 = reader.readLine().split("\\s+");
                if(arr1[1].equalsIgnoreCase("left")) {
                    left.add(new Car(Integer.parseInt(arr1[0]), inputTime++));
                } else {
                    right.add(new Car(Integer.parseInt(arr1[0]), inputTime++));
                }
            }
            List<Car> allcars = solve(left, right, n, t);
            allcars.sort(Comparator.comparingInt(o -> o.input));
            for(Car cc: allcars) {
                System.out.println(cc.exit);
            }
            if (c!=0) {
                System.out.println();
            };
        }
    }
}
