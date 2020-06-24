import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class AccountBook {

    private static int offset = 45000;
    private static boolean solve2(int[] nums, int n, int f, char[] ans, int[][] dp, int maxSum) {
        dp[f + offset][n] = 1;
        for(int c=n-1; c>=0; c--) {
            for(int r=-maxSum; r<=maxSum; r++) {
                if (dp[r + nums[c] + offset][c+1] == 1|| dp[r - nums[c] + offset][c+1] == 1) {
//                    System.out.println("r = " + r + "nums[c] = " + nums[c]);
//                    System.out.println(dp[r + nums[c] + offset][c+1]);
//                    System.out.println(dp[r - nums[c] + offset][c+1]);
                    dp[r + offset][c] = 1;
                }
            }
        }
//        for(int i=0; i<40; i++) {
//            StringBuilder str = new StringBuilder();
//            for(int j=0;j<6;j++) {
//                str.append(dp[i][j] + " ");
//            }
//            System.out.println(str);
//        }
        if (dp[0 + offset][0] != 1) {
            return false;
        } else {
            dp[0 + offset][0] = 2;
            for (int c = 0; c < n; c++) {
                int pos = 0;
                int neg = 0;
                for (int r = -maxSum; r <= maxSum; r++) {
                    if (dp[r + offset][c] == 2) {
                        if (dp[r + nums[c] + offset][c + 1] == 1 || dp[r + nums[c] + offset][c + 1] == 2) {
                            dp[r + nums[c] + offset][c + 1] = 2;
                            pos++;
                        }
                        if (dp[r - nums[c] + offset][c + 1] == 1 || dp[r - nums[c] + offset][c + 1] == 2) {
                            dp[r - nums[c] + offset][c + 1] = 2;
                            neg++;
                        }
                    }
                }

//                System.out.println("#### " + c);
//                for(int i=0; i<40; i++) {
//                    StringBuilder str = new StringBuilder();
//                    for(int j=0;j<6;j++) {
//                        str.append(dp[i][j] + " ");
//                    }
//                    System.out.println(str);
//                }


                if (pos > 0 && neg > 0) {
                    ans[c] = '?';
                } else if (pos > 0) {
                    ans[c] = '+';
                } else if (neg > 0) {
                    ans[c] = '-';
                } else {
                    ans[c] = '.';
                }
            }
            return true;
        }
    }

    private static int solve(int[] nums, int curr, int n, int sum, int f, char[] ans, char[] finalAns, int[][] dp) {
        if (dp[curr][sum+50000] != -1) {
            System.out.println("from dp");
            return dp[curr][sum+50000];
        }
        if (curr == n) {
            if(sum == f) {
                for(int i=0;i<n;i++) {
                    if(finalAns[i] == '.') {
                        finalAns[i] = ans[i];
                    } else {
                        if (finalAns[i] != ans[i]) {
                            finalAns[i] = '?';
                        }
                    }
                }
                dp[curr][sum+50000] = 1;
                return 1;
            } else {
                dp[curr][sum+50000] = 0;
                return 0;
            }
        }
//                if (dp[curr][sum+50000] != -1) {
//                    System.out.println("from dp");
//            return dp[curr][sum+50000];
//        }
        ans[curr] = '+';
        int x = solve(nums, curr+1, n, sum + nums[curr], f, ans, finalAns, dp);
        ans[curr] = '-';
        int y = solve(nums, curr+1, n, sum - nums[curr], f, ans, finalAns, dp);
        if (x==1 || y==1) {
            dp[curr][sum+50000] = 1;
            return 1;
        } else {
            dp[curr][sum+50000] = 0;
            return 0;
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String[] arr = reader.readLine().split("\\s+");
            int n = Integer.parseInt(arr[0]);
            int f = Integer.parseInt(arr[1]);
            if (n==0 && f==0) break;
            int[] nums = new int[n];
            for(int i=0;i<n;i++) {
                nums[i] = Integer.parseInt(reader.readLine().trim());
            }
            char[] ans = new char[n];
            char[] finalAns = new char[n];
            Arrays.fill(finalAns, '.');
            int[][] dp = new int[100000][n+1];
            for(int[] row:dp) {
                Arrays.fill(row, -1);
            }
            boolean res = solve2(nums, n, f, ans, dp, 41000);
            if (res) {
                StringBuilder str = new StringBuilder();
                for(int i=0;i<n;i++) {
                    str.append(ans[i]);
                }
                System.out.println(str);
            } else {
                System.out.println('*');
            }
        }
    }
}
