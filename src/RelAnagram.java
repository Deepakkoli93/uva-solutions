import java.util.*;

public class RelAnagram {
    public static void main(String[] args) {

        Map<String, List<String>> map = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String s = scanner.nextLine();
            if (s.equalsIgnoreCase("#")) {break;}
            String[] arr = s.split("\\s+");
            for(String a: arr) {
                char[] chars = a.toLowerCase().toCharArray();
                Arrays.sort(chars);
                String ss = new String(chars);
                if (map.containsKey(ss)) {
                    map.get(ss).add(a);
                } else {
                    List<String> l1 = new ArrayList<>();
                    l1.add(a);
                    map.put(ss, l1);
                }
            }
        }
        List<String> ans = new ArrayList<>();
        map.forEach((key, value) -> {
            if (value.size() == 1) {
                ans.add(value.get(0));
            }
        });
        Collections.sort(ans);
        ans.forEach(System.out::println);
    }
}
