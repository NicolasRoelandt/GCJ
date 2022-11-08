package C2022.Kickstart.RoundG;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class HappySubarrays {


    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            in.nextLine();

            long solve = solve(lineToInt(in.nextLine()));
            System.out.println(String.format("Case #%d: %d", i, solve));

        }
    }


//    private static int solve(int[] a) {
//        int start = 0;
//        int prefixSum = 0;
//        for (int end = 0; end < a.length; end++) {
//            prefixSum += a[end];
//            if (prefixSum >= 0) {
//                // count
//            } else {
//                while (prefixSum < 0 && start < end) {
//                    prefixSum -= a[start];
//                    start++;
//                }
//            }
//        }
//        return 0;
//    }

    private static long solve(int[] a) {
        long sum = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = i; j < a.length; j++) {
                sum += prefixSum(a, i, j);
            }
        }
        return sum;
    }

    private static int prefixSum(int[] a, int start, int end ) {
        int prefixSum = 0;
        for (int i = start; i <= end; i++) {
            prefixSum += a[i];
            if(prefixSum < 0) return 0;
        }
        return prefixSum;
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