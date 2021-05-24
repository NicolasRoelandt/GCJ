package C2021.Kickstart.RoundB;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.math.BigInteger.ONE;


public class ConsecutivePrimesHard {

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            long N = Long.parseLong(in.nextLine());

            System.out.println(String.format("Case #%d: %d", i, solve(N)));
        }
    }

    private static long solve(long n) {
        BigInteger p = findPrimeSmallerThan(BigInteger.valueOf((long) Math.sqrt(n)));
        BigInteger mult = p.multiply(p.nextProbablePrime());
        if (mult.compareTo(BigInteger.valueOf(n)) > 0) {
            p = findPrimeSmallerThan(p.subtract(ONE));
            mult = p.multiply(p.nextProbablePrime());
            if (mult.compareTo(BigInteger.valueOf(n)) > 0) {
                System.out.println(n);
                throw new RuntimeException();
            }
        }
        return mult.longValueExact();
    }

    private static BigInteger findPrimeSmallerThan(BigInteger n) {
        BigInteger TWO = BigInteger.valueOf(2);
        BigInteger start = ONE;
        BigInteger end = n;
        // f(i) = true if(next prime after i) > n
        // invariant : f(max(1, start-1)) = false et f(end) = true
        // loop exits when i == j
        while(start.compareTo(end) < 0) {
            BigInteger mid = start.add(end).divide(TWO);
            if(mid.nextProbablePrime().compareTo(n) > 0){
                end = mid;
            } else {
                start = mid.add(ONE);
            }
        }
        return end;
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