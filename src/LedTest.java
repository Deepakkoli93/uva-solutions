import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class LedTest {

    private static int[] codes = new int[]{63, 6, 91, 79, 102, 109, 125, 7, 127, 111};
    private static int getBitmask(int[] row) {
        int bitmask = 0;
        for(int i=0;i<7;i++) {
            if (row[i] == 1) {
                bitmask = bitmask | (1 << i);
            }
        }
        return bitmask;
    }

    private static boolean isValid(int bitmask, int maxNum) {
        for(int i=0;i<10;i++) {
            if (codes[i] == bitmask && i >= maxNum) {
                return false;
            }
        }
        return true;
    }

    private static int getNumFromBitmask(int bitmask) {
        for(int i=0;i<10;i++) {
            if (codes[i] == bitmask) return i;
        }
        return -1;
    }

    private static boolean burntCompatible(int i, int burnt) {
        for(int b=0; b<7; b++) {
            if ((codes[i] & (1 << b)) > 0 && (burnt & (1 << b)) > 0) {
                return false;
            }
        }
        return true;
    }
    private static boolean solve(int[][] leds, int curr, int n, int maxNum, int burnt) {
        if (curr == n) return true;

        int bitmask = getBitmask(leds[curr]);
        if (!isValid(bitmask, maxNum)) return false;

        // burnt now on check
        for(int i=0; i<7; i++) {
            if (leds[curr][i] == 1 && (burnt & (1 << i)) > 0) return false;
        }

        boolean ans = false;
        for(int i=maxNum-1; i>=maxNum-1; i--) {
//            if(!burntCompatible(i, burnt)) continue;
            // can leds[curr] be made into i?
            if (getNumFromBitmask(bitmask) == i) {
                ans |= solve(leds, curr+1, n, i, burnt );
            } else {
                int newBurnt = burnt;
                boolean possibleToConvert = true;
                for(int b=0; b<7; b++) {
                    if((codes[i] & (1 << b)) > 0) {
                        if (leds[curr][b] == 0) {
//                            if(curr==0) {
//                                System.out.println("i = " + i + "b = " + b + " burnt = " + burnt + " newburnt = " + newBurnt);
//                            }
                            newBurnt = newBurnt | (1 << b);
//                            if(curr==0) {
//                                System.out.println("i = " + i + " burnt = " + burnt + " newburnt = " + newBurnt);
//                            }
                        }
                    } else {
                        // bth bit in i is 0
                        if (leds[curr][b] == 1) {
                            possibleToConvert = false;
                            break;
                        }
                    }
                }
                if (possibleToConvert) {
//                    if(curr==1) {
//                        System.out.println("Converted to " + i + " ans = " + " newburnt " + newBurnt + " burnt " + burnt);
//                    }
                    boolean temp = solve(leds, curr+1, n, i, newBurnt);
//                    if(curr==1) {
//                        System.out.println("Converted to " + i + " ans = " + temp + " newburnt " + newBurnt);
//                    }
                    ans |= temp;
                }

            }
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            int n = Integer.parseInt(reader.readLine());
            if (n==0) break;
            int[][] leds = new int[n][7];
            for(int i=0;i<n;i++) {
                String line = reader.readLine();
                for(int j=0;j<7;j++) {
                    leds[i][j] = line.charAt(j) == 'Y' ? 1 : 0;
                }
            }
            boolean ans = solve(leds, 0, n, 10, 0);
            if(ans) {
                System.out.println("MATCH");
            } else {
                System.out.println("MISMATCH");
            }
        }
    }
}
