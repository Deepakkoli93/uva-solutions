import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LEDTest {

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


    private static boolean solve3(int[][] leds, int curr, int burnt, int num, int n) {
//        if(curr==1){
//            System.out.println("burnt = " + burnt + " curr = " + curr + " n = " + n);
//        }
        if (curr == n) return true;
        if (num < 0) return false;

        for(int b=0; b<7; b++) {
            if ((burnt & (1 << b)) > 0 && leds[curr][b] == 1) return false;
        }
        // make leds[curr] into -> num
        if (getBitmask(leds[curr]) == codes[num]) {
            return solve3(leds, curr+1, burnt, num-1, n);
        }
        int bitmask = codes[num];
        int newBurnt = burnt;
//        if(curr==1){
//            System.out.println("newburnt = " + newBurnt);
//        }
        for(int b=0; b<7; b++) {
            if ((burnt & (1 << b)) > 0 && leds[curr][b] == 1) return false;
            if ((bitmask & (1 << b)) > 0) {
                if (leds[curr][b] == 0) {
//                    if(num==9) {
//                        System.out.println("burnt bit = " + b);
//                    }
                    newBurnt = newBurnt | (1 << b);
                }
            } else {
                if (leds[curr][b] == 1) {
                    return false;
                }
            }
        }

        return solve3(leds, curr+1, newBurnt, num-1, n);
    }

    private static boolean solve4(int[][] leds, int n) {
        boolean ans = false;
        for(int i=9; i>=0; i--) {
            boolean temp = solve3(leds, 0, 0, i, n);
            if(temp) {
                System.out.println("true for i = " + i);
            }
            ans = ans | temp;
            if (ans) return true;
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            int n = Integer.parseInt(reader.readLine().trim());
            if (n==0) break;
            int[][] leds = new int[n][7];
            for(int i=0;i<n;i++) {
                String line = reader.readLine().trim();
                for(int j=0;j<7;j++) {
                    leds[i][j] = line.charAt(j) == 'Y' ? 1 : 0;
                }
            }
            boolean ans = solve4(leds, n);
            if(ans) {
                System.out.println("MATCH");
            } else {
                System.out.println("MISMATCH");
            }
        }
    }
}