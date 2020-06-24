import java.io.BufferedReader;

public class Test {

    public static void main(String[] args) {
        String s = "6.09";
        String[] arr = s.split("\\.");
        System.out.println(Integer.parseInt(arr[0]));
        System.out.println(Integer.parseInt(arr[1]));

    }
}
