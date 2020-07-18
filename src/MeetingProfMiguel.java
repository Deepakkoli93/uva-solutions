import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeetingProfMiguel {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            int n = Integer.parseInt(reader.readLine());
            if (n==0) break;
            Map<String, Integer> nodes = new HashMap<>();
            Map<Integer, String> nodeNames = new HashMap<>();
            int counter = 0;
            List<List<int[]>> adjListOld = new ArrayList<>();
            List<List<int[]>> adjListYoung = new ArrayList<>();
            for(int i=0;i<n;i++) {
                String[] arr = reader.readLine().split("\\s+");
                String x = arr[2];
                String y = arr[3];
                if(!nodes.containsKey(x)) {
                    nodes.put(x, counter);
                    nodeNames.put(counter, x);
                    adjListOld.add(new ArrayList<>());
                    adjListYoung.add(new ArrayList<>());
                    counter++;
                }
                if(!nodes.containsKey(y)) {
                    nodes.put(y, counter);
                    nodeNames.put(counter, y);
                    adjListOld.add(new ArrayList<>());
                    adjListYoung.add(new ArrayList<>());
                    counter++;
                }
                int w = Integer.parseInt(arr[4]);
                if(arr[0].equals("Y")) {
                    if(!x.equals(y)) {
                        adjListYoung.get(nodes.get(x)).add(new int[]{nodes.get(y), w});
                        if (arr[1].equals("B")) {
                            adjListYoung.get(nodes.get(y)).add(new int[]{nodes.get(x), w});
                        }
                    }
                } else if(arr[0].equals("M")) {
                    if(!x.equals(y)) {
                        adjListOld.get(nodes.get(x)).add(new int[]{nodes.get(y), w});
                        if (arr[1].equals("B")) {
                            adjListOld.get(nodes.get(y)).add(new int[]{nodes.get(x), w});
                        }
                    }
                }
            }
            String[] arr = reader.readLine().split("\\s+");
            if(arr[0].equals(arr[1]) && !nodes.containsKey(arr[0])) {
                System.out.println("0 " + arr[0]);
                continue;
            }
            if(!nodes.containsKey(arr[0]) || !nodes.containsKey(arr[1])) {
                System.out.println("You will never meet.");
                continue;
            }
            int s = nodes.get(arr[0]);
            int e = nodes.get(arr[1]);

            long[][] distYoung = new long[counter][counter];
            long[][] distOld = new long[counter][counter];
            for(int i=0; i<counter; i++) {
                for(int j=0;j<counter; j++) {
                    if(i==j) {
                        distYoung[i][j] = 0;
                        distOld[i][j] = 0;
                    } else {
                        distOld[i][j] = Integer.MAX_VALUE;
                        distYoung[i][j] = Integer.MAX_VALUE;
                    }
                }
            }

            for(int i=0; i<adjListOld.size(); i++) {
                List<int[]> nei = adjListOld.get(i);
                for(int[] p: nei) {
                    distOld[i][p[0]] = Math.min(distOld[i][p[0]], p[1]);
                }
            }

            for(int i=0; i<adjListYoung.size(); i++) {
                List<int[]> nei = adjListYoung.get(i);
                for(int[] p: nei) {
                    distYoung[i][p[0]] = Math.min(distYoung[i][p[0]], p[1]);
                }
            }

            for(int k=0; k<counter; k++) {
                for(int i=0; i<counter; i++) {
                    for(int j=0; j<counter; j++) {
                        distOld[i][j] = Math.min(distOld[i][j], distOld[i][k] + distOld[k][j]);
                        distYoung[i][j] = Math.min(distYoung[i][j], distYoung[i][k] + distYoung[k][j]);
                    }
                }
            }

            long ans = Integer.MAX_VALUE;
            for(int i=0; i<counter; i++) {
                ans = Math.min(ans, distYoung[s][i] + distOld[e][i]);
            }
            if(ans == Integer.MAX_VALUE) {
                System.out.println("You will never meet.");
            } else {
                List<String> list = new ArrayList<>();
                StringBuilder str = new StringBuilder();
                for(int i=0; i<counter; i++) {
                    if(ans == distYoung[s][i] + distOld[e][i]) {
                        list.add(nodeNames.get(i));

                    }
                }
                list.sort(String::compareTo);
                for(String ss: list) {
                    str.append(ss).append(" ");
                }
                System.out.println(ans + " " + str.toString().trim());
            }

        }
    }
}
