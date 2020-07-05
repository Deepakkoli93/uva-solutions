import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class TheNewVilla {

    private static int getRoomNumber(int bitmask) {
        int ans = 0;
        for(int i=0; i<4; i++) {
            if((bitmask & (1 << i)) != 0) {
                ans += Math.pow(2, i);
            }
        }
        return ans;
    }

    private static int switchOnLight(int bitmask, int room) {
        return bitmask | (1 << (room + 4));
    }

    private static int switchOffLight(int bitmask, int room) {
        return bitmask & ~(1 << (room + 4));
    }

    private static boolean isLightOn(int bitmask, int room) {
        return (bitmask & (1 << (room + 4))) != 0;
    }

    private static void solve(Map<Integer, List<Integer>> adjList, Map<Integer, List<Integer>> switches, int n, int t) {

        Queue<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[100000];
        int[] parent = new int[100000]; Arrays.fill(parent, -1);
        int finalState = switchOnLight(n-1, n-1);
        q.add(switchOnLight(0, 0));
        boolean found = false;
        while(!q.isEmpty()) {
            int bitmask = q.poll();
            visited[bitmask] = true;
            if (bitmask == finalState) {
                found = true;
                break;
            }
            int currRoom = getRoomNumber(bitmask);
//            System.out.println("curr room = " + currRoom);
            for(int nei: adjList.get(currRoom)) {
//                System.out.println("nei " + nei);
                if(isLightOn(bitmask, nei)) {
                    int newBitmask = nei;
                    for(int i=0; i<n; i++) {
                        if (isLightOn(bitmask, i)) {
                            newBitmask = switchOnLight(newBitmask, i);
                        }
                    }
                    if (!visited[newBitmask]) {
                        parent[newBitmask] = bitmask;
                        q.add(newBitmask);
                        visited[newBitmask] = true;
                    }
                }
            }

            for(int sw: switches.get(currRoom)) {
                if(sw != currRoom) {
                    if (isLightOn(bitmask, sw)) {
                        int newBitmask = switchOffLight(bitmask, sw);
                        if (!visited[newBitmask]) {
                            parent[newBitmask] = bitmask;
                            q.add(newBitmask);
                            visited[newBitmask] = true;
                        }
                    } else {
                        int newBitmask = switchOnLight(bitmask, sw);
                        if (!visited[newBitmask]) {
                            parent[newBitmask] = bitmask;
                            q.add(newBitmask);
                            visited[newBitmask] = true;
                        }
                    }
                }
            }
        }


        System.out.println("Villa #" + t);
        if (!found) {
            System.out.println("The problem cannot be solved.");
        } else {
            List<String> steps = new ArrayList<>();
            int prev = finalState;
            int par = parent[finalState];
            while (par != -1) {
                if (getRoomNumber(prev) != getRoomNumber(par)) {
                    steps.add("- Move to room " + (getRoomNumber(prev) + 1) + ".");
                } else {
                    for (int i = 0; i < n; i++) {
                        if (isLightOn(par, i) && !isLightOn(prev, i)) {
                            steps.add("- Switch off light in room " + (i+1) + ".");
                            break;
                        } else if (!isLightOn(par, i) && isLightOn(prev, i)) {
                            steps.add("- Switch on light in room " + (i+1) + ".");
                            break;
                        }
                    }
                }
                prev = par;
                par = parent[prev];
            }
            System.out.println("The problem can be solved in " + steps.size() + " steps:");
            for(int i=steps.size()-1; i>=0; i--) {
                System.out.println(steps.get(i));
            }
        }
        System.out.println();


    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = 0;
        while(true) {
            String[] arr = reader.readLine().split("\\s+");
            int r = Integer.parseInt(arr[0]);
            int d = Integer.parseInt(arr[1]);
            int s = Integer.parseInt(arr[2]);
            if(r==0 && d==0 && s==0) break;
            t++;
            Map<Integer, List<Integer>> adjList = new HashMap<>();
            Map<Integer, List<Integer>> switches = new HashMap<>();
            for(int i=0; i<r; i++) {
                adjList.put(i, new ArrayList<>());
                switches.put(i, new ArrayList<>());
            }
            for(int i=0; i<d; i++) {
                arr = reader.readLine().split("\\s+");
                int x = Integer.parseInt(arr[0]) - 1;
                int y = Integer.parseInt(arr[1]) - 1;
                adjList.get(x).add(y);
                adjList.get(y).add(x);
            }
            for(int i=0; i<s; i++) {
                arr = reader.readLine().split("\\s+");
                int x = Integer.parseInt(arr[0]) - 1;
                int y = Integer.parseInt(arr[1]) - 1;
                switches.get(x).add(y);
            }
            reader.readLine();
            solve(adjList, switches, r, t);
        }
    }
}
