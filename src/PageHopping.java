import java.util.Arrays;
import java.util.Scanner;

public class PageHopping {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = 0;
        while(true) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            if(a==0 && b==0) break;
            t++;
            int n = Math.max(a, b);
            long[][] dist = new long[100][100];
            boolean[] isInGraph = new boolean[100];
            for(long[] row: dist) Arrays.fill(row, Integer.MAX_VALUE);
            dist[a-1][b-1] = 1;
            isInGraph[a-1] = true;
            isInGraph[b-1] = true;
            while(true) {
                a = sc.nextInt();
                b = sc.nextInt();
                if(a==0 && b==0) break;
                n = Math.max(n, a);
                n = Math.max(n, b);
                dist[a-1][b-1] = 1;
                isInGraph[a-1] = true;
                isInGraph[b-1] = true;
            }
            for(int i=0;i<n;i++) dist[i][i]=0;
            for(int k=0; k<n; k++) {
                for(int i=0; i<n; i++) {
                    for(int j=0; j<n; j++) {
                        if(isInGraph[i] && isInGraph[j] && isInGraph[k])
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }
            double avg = 0;
            for(int i=0;i<n;i++) {
                for(int j=0;j<n;j++) {
                    if(i!=j && isInGraph[i] && isInGraph[j])
                    avg += dist[i][j];
                }
            }
            int numNodes = 0;
            for(boolean i: isInGraph) {
                if(i) numNodes++;
            }
            avg = avg / (numNodes * (numNodes-1));
            System.out.println(String.format("Case %d: average length between pages = %.3f clicks", t, avg));
        }
    }
}
