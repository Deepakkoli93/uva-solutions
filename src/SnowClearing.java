import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class SnowClearing {

    static class EulerTour {

        List<int[]> cycle;
//        List<List<int[]>> adjList;
        int[][] adjMat;
        int n;
        Map<Integer, List<Integer>> indToNode;
        private void fleury(int u) {
            for(int v=0;v<n;v++) {
                if(adjMat[u][v] > 0) {
                    adjMat[u][v]--;
                    adjMat[v][u]--;
                    fleury(v);
                    cycle.add(new int[]{v, u});
                }
            }
        }

        public EulerTour(int[][] adjMat, Map<Integer, List<Integer>> indToNode) {
//            this.adjList = adjList;
            this.cycle = new ArrayList<>();
            this.n = adjMat.length;
            this.adjMat = adjMat;
            this.indToNode = indToNode;
        }

        public double printEulerTour() {

            double ans = 0;
            for (int[] u : cycle) {
                int ind1 = u[0];
                int ind2 = u[1];
                List<Integer> p1 = indToNode.get(ind1);
                List<Integer> p2 = indToNode.get(ind2);
                ans += getDist(p1, p2);
//                System.out.println((u[0]+1) + " " + (u[1]+1));
            }
            return ans;
        }

    }

    private static double getDist(List<Integer> p1,  List<Integer> p2) {
        return Math.sqrt((p1.get(0) - p2.get(0))*(p1.get(0) - p2.get(0)) + (p1.get(1) - p2.get(1))*(p1.get(1) - p2.get(1)));
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int tt = Integer.parseInt(reader.readLine().trim());
        for(int t=1; t<=tt; t++) {
            if(t==1) {
                reader.readLine();
            }
            String[] arr = reader.readLine().trim().split("\\s+");
            int startX = Integer.parseInt(arr[0]);
            int startY = Integer.parseInt(arr[1]);
//            Map<int[], List<int[]>> adjList = new HashMap<>();
            List<List<Integer>> adjList = new ArrayList<>();
            Map<List<Integer>, Integer> nodeToInd = new HashMap<>();
            Map<Integer, List<Integer>> indToNode = new HashMap<>();
            int nodeCount = 0;

//            List<Integer> startNode = new ArrayList<>(); startNode.add(startX); startNode.add(startY);
//            nodeToInd.put(startNode, nodeCount);
//            indToNode.put(nodeCount, startNode);
//            adjList.add(new ArrayList<>());
//            nodeCount++;
            double total = 0;
            while(true) {
                String line = reader.readLine();
                if(line==null || line.isEmpty()) {
                    break;
                }
                arr = line.trim().split("\\s+");
//                List<Integer> u = new ArrayList<>();
//                u.add(Integer.parseInt(arr[0])); u.add(Integer.parseInt(arr[1]));
//                List<Integer> v = new ArrayList<>();
//                v.add(Integer.parseInt(arr[2])); v.add(Integer.parseInt(arr[3]));
//                int uInd = -1;
//                int vInd = -1;
//                if(nodeToInd.containsKey(u)) {
//                    uInd = nodeToInd.get(u);
//                } else {
//                    uInd = nodeCount;
//                    nodeToInd.put(u, nodeCount);
//                    indToNode.put(nodeCount, u);
//                    adjList.add(new ArrayList<>());
//                    nodeCount++;
//                }
//
//                if(nodeToInd.containsKey(v)) {
//                    vInd = nodeToInd.get(v);
//                } else {
//                    vInd = nodeCount;
//                    nodeToInd.put(v, nodeCount);
//                    indToNode.put(nodeCount, v);
//                    adjList.add(new ArrayList<>());
//                    nodeCount++;
//                }
//
//                adjList.get(uInd).add(vInd);
//                adjList.get(vInd).add(uInd);
                int x1 = Integer.parseInt(arr[0]);
                int y1 = Integer.parseInt(arr[1]);
                int x2 = Integer.parseInt(arr[2]);
                int y2 = Integer.parseInt(arr[3]);
                total += Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2)) * 2;

            }

//            int n = adjList.size();
//            int[][] adjMat = new int[n][n];
//            for(int u=0; u<n; u++) {
//                for(int v: adjList.get(u)) {
//                    adjMat[u][v]++;
//                    adjMat[v][u]++;
//                }
//            }

            // 0th index is start node

//            if (adjList.get(0).isEmpty()) {
//                int minNodeInd = 1;
//                List<Integer> node = indToNode.get(1);
//                double minDist = getDist(startNode, node);
//                int i = 1;
//                for(i=1; i<n; i++) {
//                    List<Integer> node1 = indToNode.get(i);
//                    double dist = getDist(startNode, node1);
//                    if(dist < minDist) {
//                        node = node1;
//                        minNodeInd = i;
//                    }
//                }
//                adjMat[0][minNodeInd]++;
//                adjMat[minNodeInd][0]++;
//            }


//            EulerTour e = new EulerTour(adjMat, indToNode);

//            boolean hasKey = nodeToInd.containsKey(startNode);
//            int startInd = nodeToInd.get(startNode);
//            e.fleury(1);
//            double dist = e.printEulerTour();
            double dist = total;
            double time = (dist * 60) / 20000;
            int roundedMinutes = (int)Math.round(time);

            int hours = (int)roundedMinutes / 60;
            int minutes = roundedMinutes % 60;
            String roundedMinutesStr = "";
            if(minutes < 10) {
                roundedMinutesStr = "0" + minutes;
            } else {
                roundedMinutesStr = ""+minutes;
            }
            System.out.println(hours+":"+roundedMinutesStr);
            if(t!=tt) {
                System.out.println();
            }
        }
    }
}
