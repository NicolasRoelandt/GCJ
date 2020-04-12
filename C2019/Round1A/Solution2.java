package C2019.Round1A;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Stream;


public class Solution2 {


    private final Set<String> words;

    public Solution2(Set<String> words) {
        this.words = words;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int N = Integer.parseInt(in.nextLine());
            Set<String> words = new HashSet<>(N);
            for (int j = 0; j < N; j++) {
                words.add(in.nextLine());
            }

            Solution2 solution = new Solution2(words);


            System.out.println("Case #" + i + ": " + solution.solve());
        }

    }

    private int solve() {
        List<Set<String>> sets = createSubsets(0, words, true);
        int size = -1;
        int index = 1;
        while(sets.size() != size){
            size = sets.size();
            List<Set<String>> next = new ArrayList<>();
            for (Set<String> set : sets) {
                next.addAll(createSubsets(index, set, false));
            }
            index++;

            sets = next;
        }

        return sets.size()*2;
    }

    public List<Set<String>> createSubsets(int indexFromEnd, Set<String> current, boolean first) {
        List<Set<String>> result = new ArrayList<>();
        if(current.size() <=1) return result;
        if(current.size() < 4 && !first) {
            result.add(current);
            return result;
        }

        Set<String>[] sets = new Set[26];
        for (String word : current) {
            int index = word.length() - 1 - indexFromEnd;
            if(index < 0) continue;
            char letter = word.charAt(index);
            Set<String> set = sets[letter - 'A'];
            if(set == null) {
                set = sets[letter - 'A'] = new HashSet<>();
            }
            set.add(word);
        }

        for (Set<String> set : sets) {
            if(set == null) continue;
            if(set.size() >= 2){
                result.add(set);
                for (String s : set) {
                    current.remove(s);
                }
            }
        }

        if(current.size() == 0 && !first){
            List<Set<String>> temp = new ArrayList<>();
            for (Set<String> set : result) {
                if(set.size() == 3){
                    temp.add(set);
                }
            }

            if(temp.size() >= 2){
                Iterator<String> iterator = temp.get(0).iterator();
                String next = iterator.next();
                current.add(next);
                iterator.remove();

                iterator = temp.get(1).iterator();
                next = iterator.next();
                current.add(next);
                iterator.remove();
            }
        }

        if(current.size() == 0 && !first){
            for (Set<String> set : result) {
                if(set.size() >= 4){
                    Iterator<String> iterator = set.iterator();
                    String next = iterator.next();
                    current.add(next);
                    iterator.remove();

                    next = iterator.next();
                    current.add(next);
                    iterator.remove();
                }
            }
        }


        if(current.size() >= 2 && !first){
            result.add(current);
        }


        return result;

    }


    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }
}