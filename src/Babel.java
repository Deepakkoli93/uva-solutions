import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    private static void solve(Map<String, List<String>> wordToLang, Map<String, List<String>> langToWord, String o, String d, int m) {
        String[] words = new String[m];
        Map<String, Integer> wordToInd = new HashMap<>();
        int counter = 0;
        for(String word: wordToLang.keySet()){
            wordToInd.put(word, counter);
            words[counter++] = word;
        }
        Set<String> allLangs = new HashSet<>(langToWord.keySet());
        allLangs.add(o);
        allLangs.add(d);
        int langsLen = allLangs.size();
        String[] langs = new String[langsLen];
        Map<String, Integer> langToInd = new HashMap<>();
        counter = 0;
        for(String lang: allLangs) {
            langToInd.put(lang, counter);
            langs[counter++] = lang;
        }

        int[][] dist = new int[langsLen][m];
        for(int[] row: dist) Arrays.fill(row, Integer.MAX_VALUE);

        // weight, language, word
        Queue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(obj -> obj[0]));
        for(String word: langToWord.get(o)) {
            q.add(new int[]{word.length(), langToInd.get(o), wordToInd.get(word)});
            dist[langToInd.get(o)][wordToInd.get(word)] = word.length();
        }

        int ans = Integer.MAX_VALUE;
        while(!q.isEmpty()) {
            int[] front = q.poll();
            if(front[0] == dist[front[1]][front[2]]) {
                String currLang = langs[front[1]];
                String currWord = words[front[2]];
                for(String lang: wordToLang.get(currWord)) {
                    if (!lang.equalsIgnoreCase(currLang)) {
                        if(lang.equalsIgnoreCase(d)) {
                            ans = Math.min(ans, front[0]);
                        }
                        // all words in this lang
                        for(String word: langToWord.get(lang)) {
                            if(!word.equalsIgnoreCase(currWord) && word.charAt(0) != currWord.charAt(0)) {
                                int weight = word.length();
                                if(front[0] + weight < dist[langToInd.get(lang)][wordToInd.get(word)]) {
                                    dist[langToInd.get(lang)][wordToInd.get(word)] = front[0] + weight;
                                    q.add(new int[]{dist[langToInd.get(lang)][wordToInd.get(word)], langToInd.get(lang), wordToInd.get(word)});
                                }
                            }
                        }
                    }
                }
            }
        }

//        int ans = Integer.MAX_VALUE;
//        for(int i: dist[langToInd.get(d)]) {
//            ans = Math.min(ans, i);
//        }
        if (ans == Integer.MAX_VALUE) {
            System.out.println("impossivel");
        } else {
            System.out.println(ans);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            int m = Integer.parseInt(reader.readLine());
            if (m==0) break;
            String[] arr = reader.readLine().split("\\s+");
            String o = arr[0];
            String d = arr[1];
            Map<String, List<String>> wordToLang = new HashMap<>();
            Map<String, List<String>> langToWord = new HashMap<>();
            langToWord.put(o, new ArrayList<>());
            langToWord.put(d, new ArrayList<>());
            for(int i=0; i<m; i++) {
                arr = reader.readLine().split("\\s+");
                String lang1 = arr[0];
                String lang2 = arr[1];
                String word = arr[2];
                if (wordToLang.containsKey(word)) {
                    wordToLang.get(word).add(lang1);
                    wordToLang.get(word).add(lang2);
                } else {
                    wordToLang.put(word, new ArrayList<>());
                    wordToLang.get(word).add(lang1);
                    wordToLang.get(word).add(lang2);
                }

                if(langToWord.containsKey(lang1)) {
                    langToWord.get(lang1).add(word);
                } else {
                    langToWord.put(lang1, new ArrayList<>());
                    langToWord.get(lang1).add(word);
                }

                if(langToWord.containsKey(lang2)) {
                    langToWord.get(lang2).add(word);
                } else {
                    langToWord.put(lang2, new ArrayList<>());
                    langToWord.get(lang2).add(word);
                }
            }
            solve(wordToLang, langToWord, o, d, m);
        }
    }
}
