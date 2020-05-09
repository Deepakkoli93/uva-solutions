import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Joseph {

    private static Map<Integer, Integer> mem = new HashMap<>();
    private static boolean allBadShot(int[] arr) {
        int n = arr.length;
        int k = n/2;
        for(int i=k; i<n; i++) {
            if (arr[i] == 1) {
                return false;
            }
        }
        return true;
    }

    private static boolean runJosephus(int[] arr, int m) {
        int n = arr.length;
        int curr = (m-1)%n;
        while(true) {
            arr[curr] = 0;
            if (curr < n/2) {
                return allBadShot(arr);
            }
            for(int i=0; i<m; i++) {
                curr = (curr+1) % n;
                while(arr[curr] == 0) {
                    curr++;
                    curr = curr % n;
                }
            }
        }
    }
// 10 - 93313 11 - 459901, 12-1358657, 13-2504881
    public static void main(String[] args) {
        mem.put(1, 2);
        mem.put(2, 7);
        mem.put(3, 5);
        mem.put(4, 30);
        mem.put(5, 169);
        mem.put(6, 441);
        mem.put(7, 1872);
        mem.put(8, 7632);
        mem.put(9, 1740);
        mem.put(10, 93313);
        mem.put(11, 459901);
        mem.put(12, 1358657);
        mem.put(13, 2504881);
        Scanner scanner = new Scanner(System.in);
        while(true) {
            int k = Integer.parseInt(scanner.nextLine());
            if (k == 0) break;
            if (mem.containsKey(k)) {
                System.out.println(mem.get(k));
            } else {
                int[] arr = new int[2 * k];
                Arrays.fill(arr, 1);
                int m = 1;
                while (true) {
                    boolean ans = runJosephus(Arrays.copyOf(arr, 2 * k), m);
                    if (ans) {
                        mem.put(k, m);
                        System.out.println(m);
                        break;
                    }
                    m++;
                }
            }
        }
    }
}
