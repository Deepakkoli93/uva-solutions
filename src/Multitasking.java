import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Multitasking {

    static int MAX_SIZE = 1000000;

    static class FenwickTree {

        int[] array; // 1-indexed array, In this array We save cumulative information to perform efficient range queries and updates

        public FenwickTree(int size) {
            array = new int[size + 1];
        }

        public int rsq(int ind) {
            assert ind > 0;
            int sum = 0;
            while (ind > 0) {
                sum += array[ind];
                //Extracting the portion up to the first significant one of the binary representation of 'ind' and decrementing ind by that number
                ind -= ind & (-ind);
            }

            return sum;
        }

        public int rsq(int a, int b) {
//            assert b >= a && a > 0 && b > 0;

            return rsq(b) - rsq(a - 1);
        }

        public void update(int ind, int value) {
            assert ind > 0;
            while (ind < array.length) {
                array[ind] += value;
                //Extracting the portion up to the first significant one of the binary representation of 'ind' and incrementing ind by that number
                ind += ind & (-ind);
            }
        }

        public int size() {
            return array.length - 1;
        }

}

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String[] arr = reader.readLine().split("\\s+");
            int n = Integer.parseInt(arr[0]);
            int m = Integer.parseInt(arr[1]);
            if (n==0 && m==0) break;
            FenwickTree fenwickTree = new FenwickTree(MAX_SIZE);
            boolean isConflict = false;
            while(n-- > 0) {
                String[] arr1 = reader.readLine().split("\\s+");
                int a = Integer.parseInt(arr1[0]) + 1;
                int b = Integer.parseInt(arr1[1]);
                if (!isConflict) {
                    if (fenwickTree.rsq(a, b) > 0) {
                        isConflict = true;
//                    break;
                    } else {
                        for (int i = a; i <= b; i++) {
                            fenwickTree.update(i, 1);
                        }
                    }
                }
            }
            if (isConflict) {
//                System.out.println("CONFLICT here");
                while(m-- > 0) reader.readLine();
//                continue;
            } else {
                while(m-- > 0) {
                    String[] arr1 = reader.readLine().split("\\s+");
                    int a = Integer.parseInt(arr1[0]) + 1;
                    int b = Integer.parseInt(arr1[1]);
                    int r = Integer.parseInt(arr1[2]);
                    while(a <= MAX_SIZE && !isConflict) {
                        if (b > MAX_SIZE) b = MAX_SIZE;
                        isConflict = fenwickTree.rsq(a, b) > 0;
                        for(int i=a;i<=b;i++) {
                            fenwickTree.update(i, 1);
                        }
                        a += r;
                        b += r;
                    }
                }
            }
            if (isConflict) {
                System.out.println("CONFLICT");
            } else {
                System.out.println("NO CONFLICT");
            }
        }
    }
}
