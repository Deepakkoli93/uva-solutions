import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class BrokenKeyboard {

    private static String solve(String str) {
        char[] arr = str.toCharArray();
        int n = arr.length;
        int i = 0;
        List<Character> list = new LinkedList<>();
        int index = -1;
        while(i < n) {
            if (arr[i] == '[') {
                index = 0;
            } else if (arr[i] == ']') {
                index = -1;
            } else {
                if (index == -1) {
                    list.add(arr[i]);
                } else {
                    list.add(index, arr[i]);
                    index++;
                }
            }
            i++;
        }
        StringBuilder s = new StringBuilder();
        for(Character c: list) {
            s.append(c);
        }
        return s.toString();
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while((line = reader.readLine()) != null) {
            System.out.println(solve(line));
        }
    }
}
