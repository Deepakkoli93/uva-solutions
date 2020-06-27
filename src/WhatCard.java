import java.util.Scanner;
import java.util.Stack;

public class WhatCard {

    private static int getValue(String s) {
        char c = s.charAt(0);
        if (c == 'T' || c=='J' || c == 'Q' || c == 'K' || c=='A') {
            return 10;
        } else {
            return Integer.parseInt(Character.toString(c));
        }
    }

    private static String solve(Stack<String> s) {
        int y = 0;
        for(int i=0; i<3; i++) {
            int x = getValue(s.pop());
            y += x;
            int n = 10-x;
            while (n-- > 0) {
                s.pop();
            }
        }
        int n = y;//s.size() - y + 1;
        while (n-- > 1) {
            s.pop();
        }
        return s.peek();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        for(int i=0; i<n; i++) {
            String[] arr = scanner.nextLine().split("\\s+");
            Stack<String> s = new Stack<>();
            for(int j=51; j>=0; j--) {
                s.push(arr[j]);
            }
//            for(String s1: arr) {
//                s.push(s1);
//            }
            String ans = solve(s);
            System.out.println("Case " + (i+1) + ": " + ans);
        }
//        System.out.println();
    }
}
