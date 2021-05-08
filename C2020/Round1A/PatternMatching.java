package C2020.Round1A;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;


public class PatternMatching {

    private int N;
    private List<String> words;
    private static final char AST = '*';


    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int N = Integer.parseInt(in.nextLine());
            List<String> words = new ArrayList<>();

            for (int j = 0; j < N; j++) {
                words.add(in.nextLine().trim());
            }

            PatternMatching solution = new PatternMatching(words);


            System.out.println(String.format("Case #%d: %s", i, solution.solve()));
        }

    }

    public PatternMatching(List<String> words) {
        this.words = words;
        this.N = words.size();
    }

    public String solve() {
        try {
            List<String> pres = new ArrayList<>();
            List<String> sufs = new ArrayList<>();
            List<String> mids = new ArrayList<>();

            for (String word : words) {

                int first = word.indexOf(AST);
                String pre = word.substring(0, first);
                if (!pre.isEmpty()) pres.add(pre);
                int last = word.lastIndexOf(AST);
                String suf = word.substring(last + 1);
                if (!suf.isEmpty()) sufs.add(suf);
                if (last > first) {
                    String mid = word.substring(first + 1, last).replaceAll("\\*", "");
                    if (!mid.isEmpty()) mids.add(mid);
                }
            }

            Optional<String> longestPreOpt = findLonguest(pres);
            longestPreOpt.ifPresent(longest -> {
                        for (String pre : pres) {
                            if (!longest.startsWith(pre)) throw new MyException();
                        }
                    }
            );

            Optional<String> longestSufOpt = findLonguest(sufs);
            longestSufOpt.ifPresent(longest -> {
                        for (String suf : sufs) {
                            if (!longest.endsWith(suf)) throw new MyException();
                        }
                    }
            );

            StringBuilder sb = new StringBuilder();
            longestPreOpt.ifPresent(sb::append);

            for (String mid : mids) {
                sb.append(mid);
            }

            longestSufOpt.ifPresent(sb::append);
            return sb.toString();

        } catch (MyException e) {
            return "" + AST;
        }
    }

    private class MyException extends RuntimeException {
    }

    private Optional<String> findLonguest(List<String> pres) {
        return pres.stream().reduce((s1, s2) -> s1.length() > s2.length() ? s1 : s2);
    }

    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }
}