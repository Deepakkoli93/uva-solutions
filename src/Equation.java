import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Equation {
    private static int prec (Character ch) {
        if (ch == '+' || ch == '-') {
            return 1;
        } else if (ch == '*' || ch == '/') {
            return 2;
        } else {
            return -1;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());
        reader.readLine();
        while (n-- > 0) {
            StringBuilder ans = new StringBuilder();
            Stack<Character> stack = new Stack<>();
            while (true) {
                String line = reader.readLine();
                if (line == null || line.isEmpty()) break;
                char ch = line.charAt(0);
                if (ch>=48 && ch <= 57) {
                    ans.append(ch);
                } else {
                    if (ch == '(') {
                        stack.push(ch);
                    } else if (ch == ')') {
                        while(!stack.empty() && stack.peek() != '(') {
                            ans.append(stack.pop());
                        }
                        if (!stack.empty() && stack.peek() == '(') stack.pop();
                    } else {
                        // operator
                        if (stack.empty() || stack.peek() == '(' || prec(stack.peek()) < prec(ch)) {
                            stack.push(ch);
                        } else {
                            while(!stack.empty() && prec(stack.peek()) >= prec(ch)) {
                                ans.append(stack.pop());
                            }
                            stack.push(ch);
                        }
                    }
                }
            }
            while(!stack.empty()) {
                ans.append(stack.pop());
            }
            System.out.println(ans.toString());
            if (n!=0) {
                System.out.println();
            }
        }
    }
}
