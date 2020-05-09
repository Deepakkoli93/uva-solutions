import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IDCodes {
    private static String nextPerm(String str) {
        char[] arr = str.toCharArray();
        int n = arr.length;
        int i = n-1;
        while(i>0 && arr[i] <= arr[i-1]) i--;
        if (i==0) return "No Successor";

        int nextBigger = n-1;
        while(arr[nextBigger] <= arr[i-1]) nextBigger--;

        char temp = arr[nextBigger];
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

        return new String(arr);
    }
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String s = reader.readLine();
            if (s.equals("#")) break;
            System.out.println(nextPerm(s));
        }
    }
}
