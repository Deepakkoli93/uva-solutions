import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class EulerTour {

    List<Integer> cycle;
    List<List<int[]>> adjList;
    int[][] adjMat;

    private void eulerTour(int i, int u) {
        for(int[] v: adjList.get(u)) {
            if(v[1] == 1) {
//                adjMat[u][v[0]] = 0;
//                adjMat[v[0]][u] = 0;
                v[1] = 0;
                for(int[] k: adjList.get(v[0])) {
                    if(k[0] == u && k[1] == 1) {
                        k[1] = 0;
//                        adjMat[v[0]][k[0]] = 0;
//                        adjMat[k[0]][v[0]] = 0;
                        break;
                    }
                }
                cycle.add(i, u);
                eulerTour(i+1, v[0]);
            }
        }
    }

    public EulerTour(List<List<int[]>> adjList) {
        this.adjList = adjList;
        this.cycle = new ArrayList<>();
        int n = adjList.size();
        this.adjMat = new int[n][n];
        for(int i=0; i<n; i++) {
            for(int[] j: adjList.get(i)) {
                adjMat[i][j[0]] = 1;
            }
        }
    }

    public void printEulerTour() {
        StringBuilder str = new StringBuilder();
        for(int u:cycle) {
            str.append(u).append(" ");
        }
        System.out.println(str);
    }

    public static void main(String[] args) {
        List<List<int[]>> adjList = new ArrayList<>();
        for(int i=0;i<5;i++) adjList.add(new ArrayList<>());
        adjList.get(0).add(new int[]{1, 1});
        adjList.get(0).add(new int[]{2, 1});

        adjList.get(1).add(new int[]{0, 1});
        adjList.get(1).add(new int[]{2, 1});
        adjList.get(1).add(new int[]{3, 1});
        adjList.get(1).add(new int[]{4, 1});

        adjList.get(2).add(new int[]{0, 1});
        adjList.get(2).add(new int[]{1, 1});
        adjList.get(2).add(new int[]{3, 1});
        adjList.get(2).add(new int[]{4, 1});

        adjList.get(3).add(new int[]{1, 1});
        adjList.get(3).add(new int[]{2, 1});
        adjList.get(3).add(new int[]{4, 1});

        adjList.get(4).add(new int[]{1, 1});
        adjList.get(4).add(new int[]{2, 1});
        adjList.get(4).add(new int[]{3, 1});

        EulerTour e = new EulerTour(adjList);
        e.eulerTour(0, 1);
        e.printEulerTour();
    }
}
