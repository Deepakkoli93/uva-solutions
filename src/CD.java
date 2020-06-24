import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CD {

    private static void dfs(int[] tracks, int i, int spaceLeft, boolean[] selected, boolean[] finalSelected, int space) {
        if (i >= tracks.length) {
            int currSum = 0;
            int globalSum = 0;
            for(int j=0;j<tracks.length;j++) {
                if (selected[j]) currSum += tracks[j];
                if (finalSelected[j]) globalSum += tracks[j];
            }
            if (space-currSum < space-globalSum) {
                for(int j=0;j<tracks.length;j++) {
                    finalSelected[j] = selected[j];
                }

            }
            return;
        }
        if (tracks[i] <= spaceLeft) {
            selected[i] = true;
            dfs(tracks, i+1, spaceLeft - tracks[i], selected,  finalSelected, space);
            selected[i] = false;
            dfs(tracks, i+1, spaceLeft, selected,  finalSelected, space);
        } else {
            selected[i] = false;
            dfs(tracks, i+1, spaceLeft, selected,  finalSelected, space);
        }
    }

    private static void solve(int[] tracks, int spaceLeft) {
        boolean[] selected = new boolean[tracks.length];
        boolean[] finalSelected = new boolean[tracks.length];
        dfs(tracks, 0, spaceLeft, selected, finalSelected, spaceLeft);
        StringBuilder str = new StringBuilder();
        int sum = 0;
        for(int i=0; i<tracks.length; i++) {
            if (finalSelected[i]) {
                str.append(tracks[i]);
                str.append(" ");
                sum += tracks[i];
            }
        }
        str.append("sum:" + sum);
        System.out.println(str);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String line = reader.readLine();
            if (line == null || line.isEmpty()) break;
            String[] arr = line.split("\\s+");
            int n = Integer.parseInt(arr[0]);
            int numTracks = Integer.parseInt(arr[1]);
            int[] tracks = new int[numTracks];
            for(int i=0; i<numTracks; i++) {
                tracks[i] = Integer.parseInt(arr[i+2]);
            }
            solve(tracks, n);
        }
    }
}
