import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FroshWeek {

    private static void swap(List<Long> list, int i, int j) {
        long temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    private static Long merge(List<Long> list, int start, int mid, int end) {
        long inv = 0;
        int i = start;
        int j = mid+1;
        while(i <= mid && j<= end) {
            if (list.get(i) <= list.get(j)) {
                i++;
            } else {
                inv += mid - i + 1;
                j++;
            }
        }

        List<Long> newlist = new ArrayList<>();
        i = start;
        j = mid + 1;
        while(i <= mid && j <= end) {
            if (list.get(i) <= list.get(j)) {
                newlist.add(list.get(i));
                i++;
            } else {
                newlist.add(list.get(j));
                j++;
            }
        }
        while(i <= mid) {
            newlist.add(list.get(i));
            i++;
        }
        while(j <= end) {
            newlist.add(list.get(j));
            j++;
        }
        int ind = 0;
        for(int x=start; x<=end; x++) {
            list.set(x, newlist.get(ind));
            ind++;
        }
        return inv;
    }

    private static Long solve(List<Long> list, int start, int end) {
        long inversions = 0;
        if (end == start) {
            return inversions;
        }
        if (end == start + 1) {
            if (list.get(start) <= list.get(end)) {
                return 0L;
            } else {
                swap(list, start, end);
                return 1L;
            }
        }

        int mid = (start+end)/2;
        long inv1 = solve(list, start, mid);
        long inv2 = solve(list, mid+1, end);
        long inv3 = merge(list, start, mid, end);
        return inv1 + inv2 + inv3;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while((line = reader.readLine()) != null) {
            int n = Integer.parseInt(line);
            List<Long> list = new ArrayList<>();
            while(n-- > 0) {
                list.add(Long.parseLong(reader.readLine()));
            }
            System.out.println(solve(list, 0, list.size()-1));
        }
    }
}
