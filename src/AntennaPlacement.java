import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AntennaPlacement {

    private static int mcbm(int left, boolean[] visited, List<List<Integer>> adjList, int[] owner) {
        if(visited[left]) return 0;
        visited[left] = true;
        for(int right: adjList.get(left)) {
            if(owner[right] == -1 || mcbm(owner[right], visited, adjList, owner) > 0) {
                owner[right] = left;
                return 1;
            }
        }
        return 0;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine());
        while(t-->0) {
            String[] arr = reader.readLine().split("\\s+");
            int r = Integer.parseInt(arr[0]);
            int c = Integer.parseInt(arr[1]);
            int[][] grid = new int[r][c];
            for(int[] row: grid) Arrays.fill(row, -1);
            int n = r * c;
            List<List<Integer>> adjList = new ArrayList<>();
            for(int i=0; i<n; i++) adjList.add(new ArrayList<>());
            boolean[] left = new boolean[n];

            int counter = 0;
            for(int i=0; i<r; i++) {
                String line = reader.readLine();
                for(int j=0; j<c; j++) {
                    char ch = line.charAt(j);
                    if(ch == '*') {
                        grid[i][j] = counter;
                        left[counter] = (i+j) % 2 == 0;
                        counter++;
                        if(i>0 && grid[i-1][j] >= 0) {
                            adjList.get(grid[i][j]).add(grid[i-1][j]);
                            adjList.get(grid[i-1][j]).add(grid[i][j]);
                        }
                        if(j>0 && grid[i][j-1] >= 0) {
                            adjList.get(grid[i][j]).add(grid[i][j-1]);
                            adjList.get(grid[i][j-1]).add(grid[i][j]);
                        }
                    }
                }
            }


            boolean[] visited = new boolean[counter];
            int[] owner = new int[counter];
            int card = 0;
            Arrays.fill(owner, -1);
            for(int i=0; i<counter; i++) {
                if(left[i]) {
                    Arrays.fill(visited, false);
                    card += mcbm(i, visited, adjList, owner);
                }
            }

            System.out.println(counter - card);
        }
    }
}
