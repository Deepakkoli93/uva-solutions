import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Transpose {

    static class AdjVals {
        int n;
        List<Integer> indexes = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        AdjVals(int n1, List<Integer> ind, List<Integer> val) {
            this.n = n1;
            this.indexes = ind;
            this.values = val;
        }
    }

    private static void solve(List<AdjVals> list, int r, int c) {
        List<AdjVals> ans = new ArrayList<>();
        // constructing ith row of transpose
        for(int i=0; i<c; i++) {
            int n = 0;
            List<Integer> indexes = new ArrayList<>();
            List<Integer> values = new ArrayList<>();
            // must visit ith column of every row
            for(int j=0; j<r; j++) {
                // visit every row and ask is ith col non zero?
                if (list.get(j).indexes.contains(i+1)) {
                    n++;
                    indexes.add(j+1);
                    values.add(list.get(j).values.get(list.get(j).indexes.indexOf(i+1)  ));
                }
            }
            ans.add(new AdjVals(n, indexes, values));
        }

        System.out.println(c+ " " + r);
        for(int i=0; i<c; i++) {
            StringBuilder str = new StringBuilder();
            str.append(ans.get(i).n); //str.append(" ");
            List<Integer> indexes = ans.get(i).indexes;
            for(int j=0; j<indexes.size(); j++) {
                if (j == 0) {
                    str.append(" ");
                }
                str.append(indexes.get(j));
                if (j != indexes.size() - 1) {
                    str.append(" ");
                }
            }
            System.out.println(str.toString());

            List<Integer> values = ans.get(i).values;
            StringBuilder str2 = new StringBuilder();
            for(int j=0; j<values.size(); j++) {
                str2.append(values.get(j));
                if (j != values.size() - 1) {
                    str2.append(" ");
                }
            }
            System.out.println(str2.toString());
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while((line = reader.readLine()) != null) {
            String[] arr = line.split("\\s+");
//            System.out.println(line);
            int r = Integer.parseInt(arr[0]);
            int c=0;
            try {
                c = Integer.parseInt(arr[1]);
            } catch(Exception e) {
                System.out.println(e);
            }
            List<AdjVals> list = new ArrayList<>();
            for(int i=0; i<r; i++) {
                String[] arr1 = reader.readLine().split("\\s+");
                int n = Integer.parseInt(arr1[0]);
                if (n == 0) {
                    list.add(new AdjVals(n, new ArrayList<>(), new ArrayList<>()));
                    reader.readLine();
                } else {
                    List<Integer> set = new ArrayList<>();
                    for(int j=1; j<=n ;j++) {
                        set.add(Integer.parseInt(arr1[j]));
                    }
                    String[] arr2 = reader.readLine().split("\\s+");
                    List<Integer> l1 = new ArrayList<>();
                    int arrlen = arr2.length;
                    for(int j=0; j<arrlen; j++) {
                        l1.add(Integer.parseInt(arr2[j]));
                    }
                    list.add(new AdjVals(n, set, l1));
                }
            }
            solve(list, r, c);
        }
    }
}
