import java.util.*;

public class Anagram {

    private static boolean isLess(char c1, char c2) {
        if (Character.toLowerCase(c1) == Character.toLowerCase(c2)) {
            return c1 <= c2;
        }

        return Character.toLowerCase(c1) <= Character.toLowerCase(c2);

    }
    private static String nextPerm(String str) {
        char[] arr = str.toCharArray();
        int n = arr.length;
        int i = n-1;
        while(i>0 && isLess(arr[i], arr[i-1])) i--;
        if (i==0) return "";

        int nextBigger = n-1;
        while(isLess(arr[nextBigger], arr[i-1])) nextBigger--;

        char temp = arr[nextBigger];
        arr[nextBigger] = arr[i-1];
        arr[i-1] = temp;

        int j = n-1;
        while(i < j) {
            temp = arr[i];
            arr[i]=arr[j];
            arr[j]=temp;
            i++;
            j--;
        }

        return new String(arr);
    }
    private static List<String> solve(String str) {
        List<String> ans = new ArrayList<>();
        char[] arr1 = str.toCharArray();
        Character[] arr = new Character[arr1.length];
        for(int i=0; i<arr1.length;i++) arr[i] = arr1[i];
        Arrays.sort(arr, new Comparator<Character>() {
            @Override
            public int compare(Character o1, Character o2) {
                if (o1 == o2) return 0;
                if (isLess(o1, o2)) return -1;
                else return 1;
            }
        });
        String curr = "";
        for(Character c: arr) curr+=c;
//        System.out.println("sorted  : " + curr);
        ans.add(curr);
        while(true) {
            String next = nextPerm(curr);
            if (next.equals("")) break;
            ans.add(next);
            curr = next;
        }

        return ans;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        while(n-- > 0) {
            String str = scanner.nextLine();
            List<String> ans = solve(str);
            for(String s: ans) System.out.println(s);
        }
    }
}
