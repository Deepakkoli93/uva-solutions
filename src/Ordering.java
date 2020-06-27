import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Ordering {

    private static boolean allDepMet(Map<Character, List<Character>> map, char c, Map<Character, Boolean> seen) {
        for(char x: map.get(c)) {
            if (!seen.getOrDefault(x, false)) {
                return false;
            }
        }
        return true;
    }

    private static void dfs(char[] vars, int n, Map<Character, List<Character>> map, Map<Character, Boolean> seen, List<List<Character>> ans, List<Character> curr) {
        if (curr.size() == n) {
            List<Character> currCopy = new ArrayList<>(curr);
            ans.add(currCopy);
        } else {
            for(int i=0; i<n; i++) {
                if(!seen.getOrDefault(vars[i], false) && allDepMet(map, vars[i], seen)) {
                    seen.put(vars[i], true);
                    curr.add(vars[i]);
                    dfs(vars, n, map, seen, ans, curr);
                    curr.remove(curr.size()-1);
                    seen.put(vars[i], false);
                }
            }
        }
    }

    private static void solve(char[] vars, Map<Character, List<Character>> map) {

        Arrays.sort(vars);
        int n = vars.length;
        List<List<Character>> ans = new ArrayList<>();
        Map<Character, Boolean> seen = new HashMap<>();
        dfs(vars, n, map, seen, ans, new ArrayList<>());
        if (ans.size() == 0) {
            System.out.println("NO");
        }
        for(List<Character> list: ans) {
            StringBuilder str = new StringBuilder();
            for(int i=0; i<n; i++) {
                str.append(list.get(i));
                if(i!=n-1) str.append(" ");
            }
            System.out.println(str);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine());
        while(t-- > 0) {
            reader.readLine();
            String[] arr = reader.readLine().split("\\s+");
            char[] vars = new char[arr.length];
            Map<Character, List<Character>> map = new HashMap<>();
            for(int i=0; i<arr.length; i++) {
                vars[i] = arr[i].charAt(0);
                map.put(vars[i], new ArrayList<>());
            }
            arr = reader.readLine().split("\\s+");
            for(int i=0; i<arr.length; i++) {
                map.get(arr[i].charAt(2)).add(arr[i].charAt(0));
            }
            solve(vars, map);
            if(t!=0) System.out.println();
        }
    }
}
