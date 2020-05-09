import java.util.Scanner;

public class MagicSquare {
    private static String solve(String str) {
        int strlen = str.length();
        int root = (int) Math.sqrt(strlen);
        if (Math.pow(root, 2) != strlen) {
            return "No magic :(";
        }
        char[][] square = new char[root][root];
        int count = 0;
        for(int i=0; i<root; i++) {
            for(int j=0; j<root; j++) {
                square[i][j] = str.charAt(count++);
            }
        }

        // bottom up
        char[] newStr = new char[strlen];
        count = 0;
        for(int i=root-1; i>=0; i--) {
            for(int j=root-1; j>=0; j--) {
                newStr[count++] = square[i][j];
            }
        }
        if (!str.equals(new String(newStr))) {
            return "No magic :(";
        }


        // left -> right
        newStr = new char[strlen];
        count = 0;
        for(int i=0; i<root; i++) {
            for(int j=0; j<root; j++) {
                newStr[count++] = square[j][i];
            }
        }
        if (!str.equals(new String(newStr))) {
            return "No magic :(";
        }

        // right -> left
        newStr = new char[strlen];
        count = 0;
        for(int i=root-1; i>=0; i--) {
            for(int j=root-1; j>=0; j--) {
                newStr[count++] = square[j][i];
            }
        }
        if (!str.equals(new String(newStr))) {
            return "No magic :(";
        }
        return String.valueOf(root);

    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        for(int i=1; i<=n; i++) {
            String str = scanner.nextLine();
            str = str.replaceAll("[^a-zA-Z]", "");
            System.out.println("Case #" + i + ":");
            System.out.println(solve(str));
        }
    }
}
