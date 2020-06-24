import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Dominator {

    private static void mark(char[][] ans, boolean[] visitedByZero, boolean[] visited, int n, int disabled) {
        for(int i=0; i<n; i++) {
            if (visitedByZero[i] && !visited[i]) {
                ans[disabled][i] = 'Y';
            } else {
                ans[disabled][i] = 'N';
            }
        }
    }

    private static void dfs(boolean[][] adjMat, int n, boolean[] visited, int curr, int disabled) {
        for(int i=0; i<n; i++) {
            if(adjMat[curr][i] && !visited[i] && i != disabled) {
                visited[i] = true;
                dfs(adjMat, n, visited, i, disabled);
            }
        }
    }

    private static void solve(boolean[][] adjMat, int n, int t) {
        boolean[] visitedByZero = new boolean[n];
        visitedByZero[0] = true;
        dfs(adjMat, n, visitedByZero, 0, -1);

        char[][] ans = new char[n][n];
        mark(ans, visitedByZero, new boolean[n], n, 0);
        for(int disabled=1; disabled<n; disabled++) {
            boolean[] visited = new boolean[n];
            visited[0] = true;
            dfs(adjMat, n, visited, 0, disabled);
            mark(ans, visitedByZero, visited, n, disabled);
        }
        StringBuilder deco = new StringBuilder("+");
        for(int i=0; i<2*n-1;i++) deco.append("-");
        deco.append("+");
        System.out.println("Case " + t + ":");
        for(int i=0; i<n; i++) {
            System.out.println(deco);
            StringBuilder line = new StringBuilder("|");
            for(int j=0; j<n; j++) {
                line.append(ans[i][j]).append("|");
            }
            System.out.println(line);
        }
        System.out.println(deco);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine());
        for(int tt=1; tt<=t; tt++) {
            int n = Integer.parseInt(reader.readLine());
            boolean[][] adjMat = new boolean[n][n];
            for(int i=0; i<n; i++) {
                String[] arr = reader.readLine().split("\\s+");
                for(int j=0; j<n; j++) {
                    if(arr[j].equalsIgnoreCase("1")) {
                        adjMat[i][j] = true;
                    }
                }
            }
            solve(adjMat, n, tt);
        }

    }
}
