package C2021.Kickstart.RoundC;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class AlienGeneratorUsingEquation {



    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            long G = Long.parseLong(in.nextLine());
            long result = solve(G);


            System.out.println(String.format("Case #%d: %d", i, result));
        }
    }

    private static long solve(long G){
        long count = 0;
        for (long j = 1; j < Math.sqrt(2*G); j++) {
            if((2*G+j*j-j) % (2*j) == 0) {
                count++;
            }
        }
        return count;
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