import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Necklace {

    static class EulerTour {

        List<int[]> cycle;
        List<List<int[]>> adjList;
        int[][] adjMat;

        private void eulerTour(int i, int u) {
            for(int[] v: adjList.get(u)) {
                if(adjMat[u][v[0]] == 1) {
                    adjMat[u][v[0]] = 0;
                    adjMat[v[0]][u] = 0;
                    v[1] = 0;
                    for(int[] k: adjList.get(v[0])) {
                        if(k[0] == u && adjMat[v[0]][k[0]] == 1) {
                            k[1] = 0;
                            adjMat[v[0]][k[0]] = 0;
                            adjMat[k[0]][v[0]] = 0;
                            break;
                        }
                    }
                    cycle.add(i, new int[]{v[0], u});
                    eulerTour(i, v[0]);
                }
            }
        }

        private void fleury(int u) {
            for(int v=0;v<50;v++) {
                if(adjMat[u][v] > 0) {
                    adjMat[u][v]--;
                    adjMat[v][u]--;
                    fleury(v);
                    cycle.add(new int[]{v, u});
                }
            }
        }

        public EulerTour(List<List<int[]>> adjList, int[][] adjMat) {
            this.adjList = adjList;
            this.cycle = new ArrayList<>();
            int n = adjList.size();
            this.adjMat = adjMat;//new int[50][50];
//            for(int i=0; i<50; i++) {
//                for(int[] j: adjList.get(i)) {
//                    adjMat[i][j[0]] = 1;
//                }
//            }
        }

        public void printEulerTour() {

            for(int[] u:cycle) {
                System.out.println((u[0]+1) + " " + (u[1]+1));
            }

        }

    }

    private static boolean isConnected(List<List<int[]>> adjList, int start, int[] degree) {
        boolean[] visited = new boolean[50];
        Queue<Integer> q = new ArrayDeque<>();
        q.add(start);
        while(!q.isEmpty()) {
            int u = q.poll();
            for(int[] v: adjList.get(u)) {
                if(!visited[v[0]]) {
                    visited[v[0]] = true;
                    q.add(v[0]);
                }
            }
        }
        for(int i=0; i<50; i++) {
            if(degree[i] > 0 && !visited[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int tt = Integer.parseInt(reader.readLine());
        for(int t=1; t<=tt; t++) {
            int n = Integer.parseInt(reader.readLine());
            int[] degree = new int[50];
            List<List<int[]>> adjList = new ArrayList<>();
            int[][] adjMat = new int[50][50];
            for(int i=0; i<50; i++) adjList.add(new ArrayList<>());
            for(int i=0; i<n; i++) {
                String[] arr = reader.readLine().split("\\s+");
                int u = Integer.parseInt(arr[0]) - 1;
                int v = Integer.parseInt(arr[1]) - 1;
                adjList.get(u).add(new int[]{v, 1});
                adjList.get(v).add(new int[]{u, 1});
                adjMat[u][v]++;
                adjMat[v][u]++;
                degree[u]++;
                degree[v]++;
            }
            boolean isValid = true;
            int start = -1;
            for(int i=0; i<50; i++) {
                if(degree[i] % 2 == 1) {
                    isValid = false;
                    break;
                } else if (degree[i] > 0 && start == -1) {
                    start = i;
                }
            }

            if(t!=1) {
                System.out.println();
            }
            System.out.println("Case #" + t);
            if(!isValid || !isConnected(adjList, start, degree)) {
                System.out.println("some beads may be lost");
            } else {
                EulerTour e = new EulerTour(adjList, adjMat);
                e.fleury(start);
                e.printEulerTour();
            }
        }
    }
}
