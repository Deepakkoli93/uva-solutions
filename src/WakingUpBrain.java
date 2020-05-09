import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class WakingUpBrain {

    private static int solve(Map<Character, List<Character>> adjList, String initWoke, int totalNodes) {
        Map<Character, Integer> connectedCount = new HashMap<>();
        for(Character c: adjList.keySet()) {
            connectedCount.put(c, 0);
        }
        Queue<Character> q = new ArrayDeque<>();
        q.add(initWoke.charAt(0));
        q.add(initWoke.charAt(1));
        q.add(initWoke.charAt(2));

        Set<Character> woke = new HashSet<>();
        woke.add(initWoke.charAt(0));
        woke.add(initWoke.charAt(1));
        woke.add(initWoke.charAt(2));
        int years = 0;
        while(!q.isEmpty()) {
            if (woke.size() >= totalNodes) {
                return years;
            }
            Queue<Character> nextLevel = new ArrayDeque<>();
            while (!q.isEmpty()) {
                char node = q.poll();
                List<Character> neighbours = adjList.getOrDefault(node, new ArrayList<>());
                for (char nei : neighbours) {
                    if (woke.contains(nei)) continue;
                    connectedCount.put(nei, connectedCount.get(nei) + 1);
                    if (connectedCount.get(nei) >= 3) {
                        woke.add(nei);
                        nextLevel.add(nei);
                    }
                }
            }
            q = nextLevel;
            years++;

        }
        if (woke.size() >= totalNodes) {
            return years;
        } else {
            return -1;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String line = reader.readLine();
            if (line == null || line.isEmpty()) break;
            int n = Integer.parseInt(line);
            int m = Integer.parseInt(reader.readLine());
            String str = reader.readLine();
            Map<Character, List<Character>> adjList = new HashMap<>();
            while(m-- > 0) {
                String s = reader.readLine();
                char i = s.charAt(0);
                char j = s.charAt(1);
                if (adjList.containsKey(i)) {
                    adjList.get(i).add(j);
                } else {
                    List<Character> l =new ArrayList<>();
                    l.add(j);
                    adjList.put(i, l);
                }

                if (adjList.containsKey(j)) {
                    adjList.get(j).add(i);
                } else {
                    List<Character> l =new ArrayList<>();
                    l.add(i);
                    adjList.put(j, l);
                }
            }
            int ans = solve(adjList, str, n);
            if (ans == -1) {
                System.out.println("THIS BRAIN NEVER WAKES UP");
            } else {
                System.out.println("WAKE UP IN, " + ans + ", YEARS");
            }

            reader.readLine();

        }

    }
}
