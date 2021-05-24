package C2021.Kickstart.RoundB;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.math.BigInteger.ONE;


public class ConsecutivePrimeHardAnalysisSolution {

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            long N = Long.parseLong(in.nextLine());

            System.out.println(String.format("Case #%d: %d", i, solve(N)));
        }
    }

    private static long solve(long n) {
        long sqrt = (long) Math.sqrt(n);
        long lesser = findPrimeCloseTo(sqrt, -1, true);
        long upper = findPrimeCloseTo(sqrt, 1, false);
        if (lesser * upper <= n) return lesser * upper;

        long lesser_2 = findPrimeCloseTo(lesser, -1, false);
        return lesser * lesser_2;
    }

    private static long findPrimeCloseTo(long n, int increment, boolean include) {
        if (!include) {
            n += increment;
        }
        while (true) {
            if (BigInteger.valueOf(n).isProbablePrime(10)) return n;
            n += increment;
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