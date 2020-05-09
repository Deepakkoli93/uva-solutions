import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class StackEmUp {
    private static String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
    private static String[] cardValues = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
    private static String[] cards = new String[53];
    private static void solve(int[][] shuffles, int rows, List<Integer> applied) {
        String[] prev = Arrays.copyOf(cards, 53);
        String[] curr = Arrays.copyOf(cards, 53);
        for(int app: applied) {
            curr = Arrays.copyOf(prev, 53);
            for (int i=1; i<=52; i++) {
//                System.out.println(i + " " +  app );
                curr[i] = prev[shuffles[app][i]];
            }
            prev = curr;
        }
        for(int i=1; i<=52; i++) {
            System.out.println(curr[i]);
        }
    }
    public static void main(String[] args) {
        int count = 1;
        for (String s : suits) {
            for (String val : cardValues) {
                cards[count++] = val + " of " + s;
            }
        }

        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        scanner.nextLine();
        for(int i=0; i<n; i++) {
            int rows = Integer.parseInt(scanner.nextLine());
            int[][] shuffles = new int[rows+1][53];
//            System.out.println("rows = " + rows);
            for(int r=1; r<=rows; r++) {
                int c = 1;
//                for (String s : scanner.nextLine().split("\\s+")) {
//                    shuffles[r][c++] = Integer.parseInt(s);
//                }
                for(int ii=0; ii<52; ii++) {
                    shuffles[r][c++] = Integer.parseInt(scanner.next());
//                    System.out.println((ii) + " " + shuffles[r][c-1]);
                }
            }
            scanner.nextLine();
            List<Integer> applied = new ArrayList<>();
            while (scanner.hasNext()) {
//                System.out.println("here");
                String k = scanner.nextLine();
//                System.out.println(" k = " + k);
                if (k.isEmpty()) break;
                applied.add(Integer.parseInt(k));
            }

            solve(shuffles, rows, applied);
            if (i < n-1) {
                System.out.println();
            }
        }
    }
}