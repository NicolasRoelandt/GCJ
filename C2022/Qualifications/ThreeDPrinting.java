package C2022.Qualifications;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class ThreeDPrinting {



    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int[][] printers = new int [3][];
            for (int printer = 0; printer < 3; printer++) {
                printers[printer] = lineToInt(in.nextLine());
            }

            String result = solve(printers);

            System.out.println(String.format("Case #%d: %s", i, result));
        }
    }

    private static String solve( int[][] printers){
        int[] minimums = new int[4];
        for (int color = 0; color < 4; color++) {
            int min = Integer.MAX_VALUE;
            for (int printer = 0; printer < 3; printer++) {
                min = Math.min(min, printers[printer][color]);
            }
            minimums[color] = min;
        }

        int remaining = 1_000_000;
        int[] selected = new int[4];
        for (int i = 0; i < 4; i++) {
            selected[i] = Math.min(remaining, minimums[i]);
            remaining = remaining - selected[i];
        }

        if(remaining > 0){
            return "IMPOSSIBLE";
        }

        return Arrays.stream(selected).mapToObj(Integer::toString).collect(Collectors.joining(" "));
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