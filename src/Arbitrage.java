import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Arbitrage {

    private static String printPath (int[][][] path, int s, int e, int l, StringBuilder str) {
        str.append(s+1).append(" ");
        if (l==0) {
            return str.toString();
        } else {
            return printPath(path, path[l][s][e], e, l-1, str);
        }

    }

    private static void solve(double[][] adjMat, int n) {
        int[][][] path = new int[n+1][n][n];
        double[][][] cost = new double[n+1][n][n];

        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                cost[1][i][j] = adjMat[i][j];
                for(int len=2; len<=n; len++) {
                    cost[len][i][j] = 0;
                }
            }
        }

        for(int len=2; len<=n; len++) {
            for(int i=0; i<n; i++) {
                for(int j=0; j<n; j++) {
                    if (adjMat[i][j] > 0) {
                        path[1][i][j] = j;
                    }
                }
            }

            for(int k=0; k<n; k++) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (cost[len][i][j] < adjMat[i][k] * cost[len - 1][k][j]) {
                            cost[len][i][j] = adjMat[i][k] * cost[len - 1][k][j];
                            path[len][i][j] = k;//path[len][i][k];
                            if (cost[len][i][i] > 1.01) {
                                String str = printPath(path, i, i, len, new StringBuilder());
                                System.out.println(str.trim());
                                return;
                            }
                        }
                    }
                }
            }
        }

        System.out.println("no arbitrage sequence exists");

    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String line = reader.readLine();
            if (line == null || line.isEmpty()) break;
            int n = Integer.parseInt(line.trim());
            double[][] adjMat = new double[n][n];



            for(int i=0; i<n; i++) {
                String[] arr = reader.readLine().split("\\s+");
                int ind = 0;
                for(int j=0; j<n; j++) {
                    if (i == j) {
                        adjMat[i][j] = 1.0;
                    } else {
                        adjMat[i][j] = Double.parseDouble(arr[ind++].trim());
                    }
                }
            }
            solve(adjMat, n);

        }
    }
}
