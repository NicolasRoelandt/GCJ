package C2021.Kickstart.RoundB;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class IncreasingSubstring {



    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            in.nextLine();
            char[] chars = in.nextLine().toCharArray();


            String result = Arrays.stream(solve(chars)).mapToObj(Integer::toString).collect(Collectors.joining(" "));

            System.out.println(String.format("Case #%d: %s", i, result));
        }
    }

    public static int[] solve(char[] chars){
        int[] result = new int[chars.length];
        int max = 1;
        char previous = 'Z';

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (previous >= c) {
                max = 1;
            } else {
                max++;
            }
            result[i] = max;
            previous = c;
        }
        return result;

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