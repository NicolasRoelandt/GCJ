package C2021.Kickstart.RoundB;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class ConsecutivePrimes {
    private static final TreeSet<Long> tree = computeAllMult(34000);


    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int N = Integer.parseInt(in.nextLine());




            System.out.println(String.format("Case #%d: %d", i, solve(N)));
        }
    }

    private static long solve(int n){
        return tree.floor((long)n);
    }


    public static TreeSet<Long> computeAllMult(int max){
        long prev = 2;
        TreeSet<Long> tree = new TreeSet<>();
        for (long i = 3; i <= max; i++) {
            if(BigInteger.valueOf(i).isProbablePrime(10)){
                tree.add(prev*i);
                prev = i;
            }
        }
        return tree;
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