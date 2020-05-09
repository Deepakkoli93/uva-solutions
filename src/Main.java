import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        Map<String, Integer> map = new HashMap<>();
        for(int i=0; i<n; i++) {
            String country = scanner.nextLine().split(" ")[0];
            map.put(country, map.getOrDefault(country, 0) + 1);
        }
        List<String> l = new ArrayList<>(map.keySet());
        Collections.sort(l);
        for(String country: l) {
            System.out.println(country + " " + map.get(country));
        }
    }
}
