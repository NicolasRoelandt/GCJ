package C2021.Kickstart.RoundA;

import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class KGoodnessString {



    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int[] line = lineToInt(in.nextLine());
            int K = line[1];
            System.out.println(String.format("Case #%d: %d", i, solve(in.nextLine().toCharArray(), K)));
        }
    }

    private static int solve(char[] s, int K){
        int l = s.length;
        int score = 0;
        for (int i = 0; i <= (l-1)/2; i++) {
            if(s[i] != s[l-1-i]) score++;
        }

        return Math.abs(score-K);
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