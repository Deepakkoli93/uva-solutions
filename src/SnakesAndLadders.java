import java.util.Scanner;

public class SnakesAndLadders {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = Integer.parseInt(scanner.nextLine());
        while(t-- > 0) {
            String[] arr = scanner.nextLine().split("\\s+");
            int a = Integer.parseInt(arr[0]); //players
            int b = Integer.parseInt(arr[1]);//snakes, ladders
            int c = Integer.parseInt(arr[2]);//rolls
            int[] board = new int[101];
            for(int i=0; i<101; i++) {
                board[i] = i;
            }
            for(int i=0; i<b; i++) {
                String[] snl = scanner.nextLine().split("\\s+");
                board[Integer.parseInt(snl[0])] = Integer.parseInt(snl[1]);
            }
            int[] pos = new int[a+1];
            for(int i=1; i<=a; i++) {
                pos[i] = 1;
            }
            if (a >= 1) {
                int currP = 1;
                int i = 0;
                for (; i < c; i++) {
                    int val = Integer.parseInt(scanner.nextLine());
                    int newPos = board[Math.min(100, pos[currP] + val)];
                    pos[currP] = newPos;
                    if (newPos == 100) {
                        i++;
                        break;
                    }
                    if (currP == a) currP = 1;
                    else currP++;
                }
                for (; i < c; i++) scanner.nextLine();
            } else {
                for(int j=0; j<c; j++) scanner.nextLine();
            }
            for(int j=1; j<=a; j++) {
                System.out.println("Position of player " + j + " is " + pos[j] + ".");
            }
        }
    }
}
