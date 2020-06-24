import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DynamicFrog {

    private static int solve(int leftBig, int rightBig, int[] rocks) {
        if (rocks.length==0) return rightBig-leftBig;
        int[] arr = new int[rocks.length + 2];
        arr[0]=leftBig;
        for(int i=1;i<=rocks.length;i++) {
            arr[i] = rocks[i-1];
        }
        arr[rocks.length+1]=rightBig;
        int ans = arr[1]-arr[0];
        int i = 2;
        int n = arr.length;
        while(i<n) {
            ans = Math.max(ans, arr[i]-arr[i-2]);
//            if(ans==12) {
//                System.out.println(leftBig + " " + rightBig);
//            }
            i+=2;
        }
        i=3;
        while(i<n) {
            ans = Math.max(ans, arr[i]-arr[i-2]);
//            if(ans==12) {
//                System.out.println(leftBig + " " + rightBig);
//                for(int z=0;z<rocks.length;z++) {
//                    System.out.println(rocks[z]);
//                }
//            }
            i+=2;
        }
        ans = Math.max(ans, arr[1]-arr[0]);
//        if(ans==12) {
//            System.out.println(leftBig + " 3 " + rightBig);
//        }
        ans = Math.max(ans, arr[n-1]-arr[n-2]);
//        if(ans==12) {
//            System.out.println(leftBig + " 4 " + rightBig);
//        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine());
        for(int tt=1; tt<=t; tt++) {
            String [] arr = reader.readLine().split("\\s+");
            int n = Integer.parseInt(arr[0]);
            int d = Integer.parseInt(arr[1]);
            int leftBig = 0;
            int rightBig = 0;
            int ans = 0;
            arr = reader.readLine().split("\\s+");
            int i=0;
            List<Integer> smallRocks = new ArrayList<>();
            while(i < n) {
                smallRocks = new ArrayList<>();
                while(i<n) {

                    String[] rock = arr[i].split("-");
                    String type = rock[0];
                    int dist = Integer.parseInt(rock[1]);
                    if (type.equalsIgnoreCase("B")) {
                        rightBig = dist;
                        int[] sarr = new int[smallRocks.size()];
                        for(int z=0;z<smallRocks.size();z++) {
                            sarr[z] = smallRocks.get(z);
                        }
                        ans = Math.max(ans, solve(leftBig, rightBig, sarr));
                        i++;
                        leftBig = rightBig;
                        smallRocks = new ArrayList<>();
                        break;
                    } else {
                        smallRocks.add(dist);
                        i++;
                    }
                }


            }
            int[] sarr = new int[smallRocks.size()];
            for(int z=0;z<smallRocks.size();z++) {
                sarr[z] = smallRocks.get(z);
            }
            rightBig = d;
            ans = Math.max(ans, solve(leftBig, rightBig, sarr));
            System.out.println("Case " + tt +": " + ans);
        }
    }
}
