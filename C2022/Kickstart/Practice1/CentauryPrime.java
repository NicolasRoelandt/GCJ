package C2022.Kickstart.Practice1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class CentauryPrime {

    static Set<Character> vowels = new HashSet<>();
    public static void main(String[] args) {
        char[] vowelsArray = {'A', 'E', 'I', 'O', 'U', 'a', 'e', 'i', 'o', 'u'};

        for (char c : vowelsArray) {
            vowels.add(c);
        }
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            String kingdom = in.nextLine();
            System.out.println(String.format("Case #%d: %s is ruled by %s.", i, kingdom, solve(kingdom)));
        }
    }

    private static String solve(String kingdom){
        char lastLetter = kingdom.charAt(kingdom.length()-1);
        if(vowels.contains(lastLetter)){
            return "Alice";
        } else if(lastLetter == 'y'){
            return "nobody";
        } else {
            return "Bob";
        }
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
