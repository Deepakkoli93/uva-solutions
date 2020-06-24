import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BlockVoting {

    private static void dfs(List<Integer> list, int n, int curr, int majority, int currTotal, Set<Integer> coalition, Map<Integer, Integer> ans) {
        if (curr >= n) {
            for(int i=0; i<n; i++) {
                if (currTotal < majority && !coalition.contains(i) && list.get(i) + currTotal >= majority) {
                    ans.put(i, ans.get(i)+1);
                }
            }
        } else {
            dfs(list, n, curr+1, majority, currTotal, coalition, ans);
            coalition.add(curr);
            dfs(list, n, curr+1, majority, currTotal + list.get(curr), coalition, ans);
            coalition.remove(curr);
        }
    }

    private static void solve(List<Integer> list) {
        int total = 0;
        int n = list.size();
        for (Integer integer : list) {
            total += integer;
        }
        int majority = total/2 + 1;
        Map<Integer, Integer> ans = new HashMap<>();
        for(int i=0;i<n;i++) {
            ans.put(i, 0);
        }
        dfs(list, n, 0, majority, 0, new HashSet<>(), ans);
        for(Map.Entry<Integer, Integer> entry: ans.entrySet()) {
            System.out.println("party " + (entry.getKey()+1) + " has power index " + entry.getValue());
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine());
        while (t-- > 0) {
            String[] arr = reader.readLine().split("\\s+");
            List<Integer> list = new ArrayList<>();
            int p = Integer.parseInt(arr[0]);
            for(int i=1;i<=p;i++) {
                list.add(Integer.parseInt(arr[i]));
            }
            solve(list);
            System.out.println();
        }
    }
}
