import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JillRidesAgain {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int tt = Integer.parseInt(reader.readLine());
        for(int t=1;t<=tt;t++) {
            int n = Integer.parseInt(reader.readLine().trim()) - 1;
            int[] arr = new int[n];
            for(int i=0;i<n;i++) {
                arr[i] = Integer.parseInt(reader.readLine().trim());
            }
            int i=0;
            int beg = 0;
            int j = 0;
            int end = 0;
            int maxSoFar = 0;
            int currSum = 0;
            while(j < n) {

                currSum += arr[j];
                if (currSum < 0) {
                    currSum = 0;
                    i = j+1;
                }
                else if (currSum > maxSoFar) {
                    maxSoFar = currSum;
                    beg = i;
                    end = j;

                } else if (currSum == maxSoFar) {
                    if(j-i > end-beg) {
                        beg=i;
                        end=j;
                    } else if (j-i==end-beg  &&  i < beg) {

                        beg = i;
                        end = j;
                    }
                }
                j++;
            }
            if (maxSoFar <=0) {
                System.out.println("Route " + t + " has no nice parts");
            } else {
//                System.out.println("max = " + maxSoFar);
                System.out.println("The nicest part of route " + t + " is between stops " + (beg+1) + " and " + (end+2));
            }
        }
    }
}
