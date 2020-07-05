import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class LieftHopping {

    private static void solve(int n, int k, int[] timePerFloor, Map<Integer, List<Integer>> floorToLift, Map<Integer, List<Integer>> liftToFloor) {
        // weight, floor, lift
        int[][] dist = new int[100][n];
        for(int[] row: dist) Arrays.fill(row, Integer.MAX_VALUE);
        Queue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));

        for(int i: floorToLift.get(0)) {
            dist[0][i] = 0;
            q.add(new int[]{0, 0, i});
        }

        while(!q.isEmpty()) {
            int[] front = q.poll();
            if(front[0] == dist[front[1]][front[2]] ) {
                // next floor up
                if(front[1] < 99) {
                    int currFloorInd = Collections.binarySearch(liftToFloor.get(front[2]), front[1]);
                    if (currFloorInd < liftToFloor.get(front[2]).size() - 1) {
                        int nextFloor = liftToFloor.get(front[2]).get(currFloorInd + 1);
                        int weight = timePerFloor[front[2]] * (nextFloor - front[1]);
                        if (front[0] + weight < dist[nextFloor][front[2]]) {
                            dist[nextFloor][front[2]] = front[0] + weight;
                            q.add(new int[]{dist[nextFloor][front[2]], nextFloor, front[2]});
//                            System.out.println("Going up to " + nextFloor);
                        }
                    }
                }

                // one floor down
                if(front[1] > 0) {
                    int currFloorInd = Collections.binarySearch(liftToFloor.get(front[2]), front[1]);
                    if (currFloorInd > 0) {
                        int prevFloor = liftToFloor.get(front[2]).get(currFloorInd - 1);
                        int weight = timePerFloor[front[2]] * (front[1] - prevFloor);
                        if (front[0] + weight < dist[prevFloor][front[2]]) {
                            dist[prevFloor][front[2]] = front[0] + weight;
                            q.add(new int[]{dist[prevFloor][front[2]], prevFloor, front[2]});
//                            System.out.println("Going down to " + prevFloor);
                        }
                    }
                }

                // switch
                for(int lift: floorToLift.get(front[1])) {
                    if(lift != front[2]) {
                        int weight = 60;
                        if (front[0] + weight < dist[front[1]][lift]) {
                            dist[front[1]][lift] = front[0] + weight;
                            q.add(new int[]{dist[front[1]][lift], front[1], lift});
                        }
                    }
                }
            }
        }

        int ans = Integer.MAX_VALUE;
        for(int i=0; i<n; i++) {
            ans = Math.min(ans, dist[k][i]);
        }
        if(ans == Integer.MAX_VALUE) {
            System.out.println("IMPOSSIBLE");
        } else {
            System.out.println(ans);
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String line = reader.readLine();
            if(line == null || line.isEmpty()) break;
            String[] arr = line.split("\\s+");
            int n = Integer.parseInt(arr[0]);
            int k = Integer.parseInt(arr[1]);
            int[] timePerFloor = new int[n];
            arr = reader.readLine().split("\\s+");
            for(int i=0; i<n; i++) {
                timePerFloor[i] = Integer.parseInt(arr[i]);
            }
            Map<Integer, List<Integer>> floorToLift = new HashMap<>();
            Map<Integer, List<Integer>> liftToFloor = new HashMap<>();
            for(int i=0; i<100; i++) floorToLift.put(i, new ArrayList<>());
            for(int i=0; i<n; i++) liftToFloor.put(i, new ArrayList<>());
            for(int i=0; i<n; i++) {
                arr = reader.readLine().split("\\s+");
                for (String s : arr) {
                    int floor = Integer.parseInt(s);
                    floorToLift.get(floor).add(i);
                    liftToFloor.get(i).add(floor);
                }
            }
            solve(n, k, timePerFloor, floorToLift, liftToFloor);
        }
    }
}
