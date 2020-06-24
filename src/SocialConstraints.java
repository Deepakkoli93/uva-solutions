import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SocialConstraints {
    private static int[] nextPerm(int[] arr) {
        int n = arr.length;
        int i = n-1;
        while(i>0 && arr[i] <= arr[i-1]) i--;
        if (i==0) return new int[]{-1};

        int nextBigger = n-1;
        while(arr[nextBigger] <= arr[i-1]) nextBigger--;

        int temp = arr[nextBigger];
        arr[nextBigger] = arr[i-1];
        arr[i-1] = temp;

        int j = n-1;
        while(i < j) {
            temp = arr[i];
            arr[i]=arr[j];
            arr[j]=temp;
            i++;
            j--;
        }

        return arr;
    }

    private static int indexOf(int[] arr, int n, int num) {
        int ans = -1;
        for(int i=0;i<n;i++) {
            if (arr[i] == num) {
                ans = i;
                break;
            }
        }
        return ans;
    }
    private static boolean check(int[] arr, int n, int[][] cons, int m) {
        for(int i=0; i<m ;i++) {
            if (cons[i][2] > 0) {
                int apart = Math.abs(indexOf(arr, n, cons[i][0]) -indexOf(arr, n, cons[i][1]));
                if (apart > cons[i][2]) {
                    return false;
                }
            } else {
                int apart = Math.abs(indexOf(arr, n, cons[i][0]) -indexOf(arr, n, cons[i][1]));
                if (apart < Math.abs(cons[i][2])) {
                    return false;
                }
            }
        }
        return true;
    }
    private static int solve(int n, int [][] cons, int m) {
        int ans = 0;
        int[] arr = new int[n];
        for(int i=0;i<n;i++) {
            arr[i] = i;
        }
        if (check(arr, n, cons, m)) {
            ans++;
        }
        while(true) {
            int[] next = nextPerm(arr);
            if (next[0] == -1) {
                break;
            }
            if (check(next, n, cons, m)) {
                ans++;
            }
            arr = next;
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String[] arr = reader.readLine().split("\\s+");
            int n = Integer.parseInt(arr[0]);
            int m = Integer.parseInt(arr[1]);
            if (n==0 && m ==0) break;
            int[][] cons = new int[m][3];
            for(int i=0;i<m;i++) {
                arr = reader.readLine().split("\\s+");
                for(int j=0;j<3;j++) {
                    cons[i][j] = Integer.parseInt(arr[j]);
                }
            }
            System.out.println(solve(n, cons, m));
        }
    }
}
