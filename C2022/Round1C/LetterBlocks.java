package C2022.Round1C;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class LetterBlocks {


    public static final String IMPOSSIBLE = "IMPOSSIBLE";
    private static String[] words;

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            in.nextLine();
            words = in.nextLine().split(" ");

            String result = solve();

            System.out.println(String.format("Case #%d: %s", i, result));
        }
    }

    private static String solve() {
        Map<Character, String> startWith = new HashMap<>();
        Map<Character, String> endWith = new HashMap<>();
        Map<Character, List<String>> fullWord = new HashMap<>();
        Set<Character> allLetters = new HashSet<>();
        Set<Character> middles = new HashSet<>();
        for (String word : words) {
            if (!isValid(word)) {
                return IMPOSSIBLE;
            }
            char firstLetter = word.charAt(0);
            char lastLetter = word.charAt(word.length() - 1);
            allLetters.add(firstLetter);
            if (middles.contains(firstLetter) || middles.contains(lastLetter)) return IMPOSSIBLE;
            allLetters.add(lastLetter);
            if (onlyOne(word)) {
                if (fullWord.containsKey(firstLetter)) {
                    fullWord.get(firstLetter).add(word);
                } else {
                    ArrayList<String> list = new ArrayList<>();
                    list.add(word);
                    fullWord.put(firstLetter, list);
                }
                continue;
            }
            if (startWith.containsKey(firstLetter)) return IMPOSSIBLE;
            startWith.put(firstLetter, word);
            if (endWith.containsKey(lastLetter)) return IMPOSSIBLE;
            endWith.put(lastLetter, word);

            int start = 0;
            for (; start < word.length(); start++) {
                if (word.charAt(start) != firstLetter) break;
            }
            int end = word.length() - 1;
            for (; end >= 0; end--) {
                if (word.charAt(end) != lastLetter) break;
            }
            Set<Character> set = new HashSet<>();
            for (int i = start; i <= end; i++) {
                char middle = word.charAt(i);
                set.add(middle);
            }

            for (Character character : set) {
                if (middles.contains(character) || allLetters.contains(character)) return IMPOSSIBLE;
            }
            allLetters.addAll(set);
            middles.addAll(set);

        }

        List<String> list = new ArrayList<>();
            for (char letter : startWith.keySet().toArray(new Character[0])) {
            if (!endWith.containsKey(letter)) {
                while (startWith.containsKey(letter)) {
                    String s = startWith.remove(letter);
                    list.add(s);
                    letter = s.charAt(s.length() - 1);
                }
            }
        }

        if(!startWith.isEmpty()) return IMPOSSIBLE;



        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            char first = s.charAt(0);
            if (fullWord.containsKey(first)) {
                fullWord.get(first).forEach(sb::append);
                fullWord.remove(first);
            }
            sb.append(s);
            char last = s.charAt(s.length() - 1);
            if (fullWord.containsKey(last)) {
                fullWord.get(last).forEach(sb::append);
                fullWord.remove(last);
            }
        }

        for (List<String> remaining : fullWord.values()) {
            remaining.forEach(sb::append);
        }
        return sb.toString();
    }

    private static boolean onlyOne(String word) {
        Set<Character> set = new HashSet<>();
        for (char c : word.toCharArray()) {
            set.add(c);
        }
        return set.size() == 1;
    }

    private static boolean isValid(String word) {
        Set<Character> set = new HashSet<>();
        char prev = 0;
        for (char c : word.toCharArray()) {
            if (c != prev && set.contains(c)) return false;
            prev = c;
            set.add(c);
        }
        return true;
    }

    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }

    public static int[] lineToInt(String line) {
        return lineToInt(line, " ");
    }

    public static Stream<String> binaryStrings(int n) {
        return LongStream.range(0, 1L << n).mapToObj(l -> getBinaryString(l, n));
    }

    public static String getBinaryString(long value, int size) {
        return Long.toBinaryString(1L << size | value).substring(1, size + 1);
    }
}