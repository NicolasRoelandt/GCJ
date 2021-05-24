package C2021.Kickstart.RoundC;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class RockPaperScissors {
    int R = 60;



    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            in.nextLine();

            StringBuilder sb = new StringBuilder();
            char[] chars = {'R','S'};

            for (int j = 0; j < 60; j++) {
                sb.append(chars[j%2]);
            }

            String result = sb.toString();

            System.out.println(String.format("Case #%d: %s", i, result));
        }
    }

    private static int solve(){
        return 0;
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