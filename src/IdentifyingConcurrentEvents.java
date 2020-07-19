import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class IdentifyingConcurrentEvents {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Scanner sc = new Scanner(System.in);
        int t = 0;
        while(true) {
            String line = reader.readLine();
            if(line == null || line.isEmpty()) break;
            int nc = Integer.parseInt(line.trim());
            Map<String, Integer> nameToInd = new HashMap<>();
            Map<Integer, String> indToName = new HashMap<>();
            List<List<Integer>> adjList = new ArrayList<>();
            if (nc==0) break;
            t++;
            int indCounter = 0;
            for(int i=0; i<nc; i++) {
                String[] arr = reader.readLine().trim().split("\\s+");
                int len = Integer.parseInt(arr[0].trim());
                if(len == 1) {
                    String x = arr[1].trim();
                    int xx = -1;
                    if (nameToInd.containsKey(x)) {
                        xx = nameToInd.get(x);
                    } else {
                        xx = indCounter;
                        nameToInd.put(x, xx);
                        indToName.put(xx, x);
                        adjList.add(new ArrayList<>());
                        indCounter++;
                    }
                } else {
                    for (int j = 0; j < len - 1; j++) {
                        String x = arr[j + 1].trim();
                        String y = arr[j + 2].trim();
                        int xx = -1;
                        if (nameToInd.containsKey(x)) {
                            xx = nameToInd.get(x);
                        } else {
                            xx = indCounter;
                            nameToInd.put(x, xx);
                            indToName.put(xx, x);
                            adjList.add(new ArrayList<>());
                            indCounter++;
                        }
                        int yy = -1;
                        if (nameToInd.containsKey(y)) {
                            yy = nameToInd.get(y);
                        } else {
                            yy = indCounter;
                            nameToInd.put(y, yy);
                            indToName.put(yy, y);
                            adjList.add(new ArrayList<>());
                            indCounter++;
                        }
                        adjList.get(xx).add(yy);
                    }
                }
            }
            int nm = Integer.parseInt(reader.readLine().trim());
            for(int i=0; i<nm; i++) {
                String[] arr = reader.readLine().trim().split("\\s+");
                String x = arr[0].trim();
                String y = arr[1].trim();
                int xx =-1;
                if (nameToInd.containsKey(x)) {
                    xx = nameToInd.get(x);
                } else {
                    xx = indCounter;
                    nameToInd.put(x, xx);
                    indToName.put(xx, x);
                    adjList.add(new ArrayList<>());
                    indCounter++;
                }
                int yy = -1;
                if (nameToInd.containsKey(y)) {
                    yy = nameToInd.get(y);
                } else {
                    yy = indCounter;
                    nameToInd.put(y, yy);
                    indToName.put(yy, y);
                    adjList.add(new ArrayList<>());
                    indCounter++;
                }
                adjList.get(xx).add(yy);
            }
            int n = adjList.size();
            boolean[][] dist = new boolean[n][n];
            for(int i=0; i<n ;i++) {
                for(int j: adjList.get(i)) {
                    dist[i][j] = true;
                }
            }
            for(int k=0; k<n; k++) {
                for(int i=0; i<n; i++) {
                    for(int j=0; j<n; j++) {
                        dist[i][j] = dist[i][j] || (dist[i][k] && dist[k][j]);
                    }
                }
            }
            int ans = 0;
            StringBuilder str = new StringBuilder();
            for(int i=0; i<n; i++) {
                for(int j=i+1; j<n; j++) {
                    if(i!=j && !dist[i][j] && !dist[j][i]) {
                        ans++;
                        if(ans <= 2)
                        str.append("(").append(indToName.get(i)).append(",").append(indToName.get(j)).append(") ");
                    }
                }
            }
            if(ans!=0) {
                System.out.println("Case " + t + ", " + ans + " concurrent events:");
                System.out.println(str.toString());
            } else {
                System.out.println("Case " + t + ", no concurrent events.");
            }

        }
    }
}
