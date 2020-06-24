import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TheseusAndMinotaur {

    private static void dfs(List<List<Integer>> adjList, int tStart, int curr, int k, int currK, boolean[] hasCandle, int parent, StringBuilder ans) {
        while(true) {
            boolean foundNext = false;
//            if (currK == k) {
//                hasCandle[curr] = true;
//                ans.append((char) (curr + 65)).append(" ");
//                currK = 0;
//            }
            int j = 0;
            for (j = 0; j < adjList.get(curr).size(); j++) {
                if (parent != adjList.get(curr).get(j) && !hasCandle[adjList.get(curr).get(j)]) {
                    foundNext = true;
                    break;
//                    dfs(adjList, tStart, adjList.get(curr).get(j), k, currK + 1, hasCandle, curr, ans);

                }
            }
            if(foundNext) {
                if (currK == k) {
                    hasCandle[curr] = true;
                    ans.append((char) (curr + 65)).append(" ");
                    currK = 0;
                }
                parent = curr;
                curr = adjList.get(curr).get(j);
                currK++;

            } else {
                ans.append("/").append((char) (curr + 65));
                break;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String line = reader.readLine();
            if(line.equalsIgnoreCase("#")) break;

//            boolean[][] adjList = new boolean[26][26];
            List<List<Integer>> adjList = new ArrayList<>();
            for(int z=0;z<26;z++) adjList.add(new ArrayList<>());
            String[] arr = line.split("\\.");
            String[] edges = arr[0].split(";");
            for(String s: edges) {
                String[] x = s.split(":");
                if (x.length >= 2) {
                    int node = (int) x[0].charAt(0) - 65;
                    for (int i = 0; i < x[1].length(); i++) {
                        adjList.get(node).add(x[1].charAt(i) - 65);
                    }
                }
            }
            String[] arr1 = arr[1].trim().split("\\s+");
            int k = Integer.parseInt(arr1[2]);
            int tStart = arr1[1].charAt(0) - 65;
            int mStart = arr1[0].charAt(0) - 65;
            StringBuilder ans = new StringBuilder("");
            boolean[] hasCandle = new boolean[26];
            dfs(adjList, tStart, mStart, k, 1, hasCandle, tStart, ans);
            System.out.println(ans);
        }
    }
}
