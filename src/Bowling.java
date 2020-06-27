import java.util.Scanner;

public class Bowling {

    private static int scoreOfRole(int ind, String[] arr) {
        if (arr[ind].equals("X")) return 10;
        else if (arr[ind].equals("/")) return 10 - Integer.parseInt(arr[ind-1]);
        else return Integer.parseInt(arr[ind]);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String str = scanner.nextLine();

            String[] arr = str.split("\\s+");
            if (arr[0].equalsIgnoreCase("game") && arr[1].equalsIgnoreCase("over")) break;
            int start = 0;
            int n = arr.length;
            int score = 0;
            int frame = 0;
            while (start < n && frame < 10) {
                frame++;
                if (arr[start].equals("X")) {
                    score += 10 + scoreOfRole(start+1, arr) + scoreOfRole(start+2, arr);
                    start++;
                } else if (arr[start+1].equals("/")) {
                    score += 10 + scoreOfRole(start+2, arr);
                    start += 2;
                } else {
                    score += Integer.parseInt(arr[start]) + Integer.parseInt(arr[start+1]);
                    start += 2;
                }
            }
            System.out.println(score);
        }
    }
}
