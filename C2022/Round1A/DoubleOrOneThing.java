package C2022.Round1A;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class DoubleOrOneThing {

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            String s = in.nextLine();

            String result = solve(s.toCharArray());

            System.out.println(String.format("Case #%d: %s", i, result));
        }
    }

    private static String solve(char[] s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length - 1; i++) {
            sb.append(s[i]);
            for (int j = i; j < s.length - 1; j++) {
                if(s[j] == s[j + 1]) continue;
                if (s[j] < s[j + 1]) {
                    sb.append(s[i]);
                }
                break;
            }
        }
        sb.append(s[s.length - 1]);
        return sb.toString();
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