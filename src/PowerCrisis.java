import java.util.Arrays;
import java.util.Scanner;

public class PowerCrisis {
    private static boolean allZero(int [] arr) {
        int n = arr.length;
        for(int i=0; i<n; i++) {
            if (arr[i] == 1) {
                return false;
            }
        }
        return true;
    }
    private static boolean runJosephus(int[] arr, int m) {
        int n = arr.length;
        int curr = 0;
        while(true) {
            arr[curr] = 0;
            if (curr == 12) {
                if (allZero(arr)) return true;
                else return false;
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
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            int n = Integer.parseInt(scanner.nextLine());
            if (n==0) break;

            int[] arr = new int[n];
            for(int i=0;i<n;i++) arr[i]=1;
            int m = 1;
            while (true) {
                boolean ans = runJosephus(Arrays.copyOf(arr, n), m);
                if (ans) {
                    System.out.println(m);
                    break;
                }
                m++;
            }
        }
    }
}
