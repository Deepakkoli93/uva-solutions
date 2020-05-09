import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ContestScoreboard {
    static class Score {
        int userId;
        int questionsSolved = 0;
        int penalty = 0;
        Score(int u) {
            this.userId = u;
        }
    }

    private static void solve(Collection<Score> scores) {
        Comparator<Score> comparator = (o1, o2) -> {
            if (o1.questionsSolved > o2.questionsSolved) {
                return -1;
            } else if (o1.questionsSolved < o2.questionsSolved) {
                return 1;
            } else {
                if (o1.penalty < o2.penalty) {
                    return -1;
                } else if (o1.penalty > o2.penalty) {
                    return 1;
                } else {
                    return Integer.compare(o1.userId, o2.userId);
                }
            }
        };
        List<Score> scores1 = new ArrayList<>(scores);
        scores1.sort(comparator);
        for(Score s: scores1) {
            System.out.println(s.userId + " " + s.questionsSolved + " " + s.penalty);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());
        reader.readLine();
        while(n-- > 0) {


            boolean[][] hasSolved = new boolean[101][10];
            for(int i=0;i<101;i++) Arrays.fill(hasSolved[i], false);

            int[][] incorrectAttempts = new int[101][10];
            for(int i=0;i<101;i++) Arrays.fill(incorrectAttempts[i], 0);

            Map<Integer, Score> userIdToScore = new HashMap<>();
//            boolean first = true;
            while(true) {
                String line = reader.readLine();
                if (line == null || line.isEmpty()) break;
//                if(first) System.out.println("first line " + line);
//                first = false;
                String[] arr = line.split(" ");
                int userId = Integer.parseInt(arr[0]);
                int question = Integer.parseInt(arr[1]);
                int time = Integer.parseInt(arr[2]);
                if (!hasSolved[userId][question]) {
                    userIdToScore.putIfAbsent(userId, new Score(userId));
                    if(arr[3].equals("I")) {
//                        Score newScore = userIdToScore.getOrDefault(userId, new Score(userId));
//                        newScore.penalty += 20;
//                        userIdToScore.put(userId, newScore);
                        incorrectAttempts[userId][question]++;
                    } else if (arr[3].equals("C")) {
                        hasSolved[userId][question] = true;
//                        Score newScore = userIdToScore.getOrDefault(userId, new Score(userId));
//                        newScore.penalty += time;
//                        newScore.questionsSolved += 1;
//                        userIdToScore.put(userId, newScore);
                        int penalty = time + (incorrectAttempts[userId][question] * 20);
                        Score oldVal = userIdToScore.get(userId);
                        oldVal.questionsSolved = oldVal.questionsSolved + 1;
                        oldVal.penalty = oldVal.penalty + penalty;
                        userIdToScore.put(userId, oldVal);
                    }
                }

            }
            solve(userIdToScore.values());
            if(n!=0) System.out.println();
        }
    }
}