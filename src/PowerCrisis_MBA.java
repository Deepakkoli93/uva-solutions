import java.util.Arrays;
import java.util.Scanner;

public class PowerCrisis {

    private static boolean isValid(int[] arr) {
        int n = arr.length;
        if (arr[13] == 0) {
            return false;
        }
        for(int i=1; i<n; i++) {
            if (i != 13) {
                if (arr[i] == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean allZero(int[] arr) {
        int n = arr.length;
        for(int i=1; i<n; i++) {
            if (arr[i] == 1) {
                return false;
            }
        }
        return true;
    }

    private static boolean runJos(int[] arr, int m) {
        int n = arr.length;
        int curr = 1;
        while(!allZero(arr)) {
//            System.out.println(Arrays.toString(arr));
            arr[curr] = 0;
            if (isValid(arr)) {
                return true;
            }
            if (allZero(arr)) break;
            int count = 0;
            while(count < m) {
                while(arr[curr++] == 0) {
//                    curr++;
                    curr = curr % n;
                }
                count++;
//                System.out.println("count = " + count + " m = " + m);
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            int n = Integer.parseInt(scanner.nextLine());
            if (n==0) break;
            int[] arr = new int[n+1];
            arr[0] = 0;
            for(int i=1;i<=n;i++) arr[i] = 1;
            int m = 1;
            while(!runJos(Arrays.copyOf(arr, n+1), m++));
            System.out.println(m-1);

        }
    }
}
