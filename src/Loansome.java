import java.util.Scanner;

public class Loansome {

    private static int solve(double[] dep, int months, double loan, double downPayment) {
        double inst = loan / months;
        int curr = 0;
        double carVal = loan + downPayment;
//        loan += downPayment;

        // 0th month
//        loan -= downPayment;
        carVal = carVal * (1-dep[curr]);
        if (carVal > loan) {
            return curr;
        }
        curr++;
        while(true) {
            loan -= inst;
            carVal = carVal * (1-dep[curr]);
//            System.out.println(loan + " "+ carVal);
            if (carVal > loan) {
                return curr;
            }
            curr++;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String[] arr = scanner.nextLine().split("\\s+");
            int months = Integer.parseInt(arr[0]);
            if (months < 0) break;
            Double downPayment = Double.parseDouble(arr[1]);
            Double loan = Double.parseDouble(arr[2]);
            int records = Integer.parseInt(arr[3]);
            double[] dep = new double[101];
            int currInd = 0;
            double currVal = 0;
            for(int i=0; i<records; i++) {
                String[] arr1 = scanner.nextLine().split("\\s+");
                int ind = Integer.parseInt(arr1[0]);
                double val = Double.parseDouble(arr1[1]);
                while(currInd < ind) {
                    dep[currInd] = currVal;
                    currInd++;
                }
                currInd = ind;
                currVal = val;
                dep[currInd] = currVal;
            }
            while(++currInd < 101) dep[currInd] = currVal;

            int ans = solve(dep, months, loan, downPayment);
            String suff = " month";
            if (ans != 1) suff+="s";
            System.out.println(ans + suff);
        }
    }
}
