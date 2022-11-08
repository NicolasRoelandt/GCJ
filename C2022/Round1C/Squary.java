package C2022.Round1C;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class Squary {


    public static final String IMPOSSIBLE = "IMPOSSIBLE";


    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            in.nextLine();
            int[] xs = lineToInt(in.nextLine());

            String s = getResult(xs);

            System.out.println(String.format("Case #%d: %s", i, s));
        }
    }

    private static String getResult(int[] xs) {
        long sum = Arrays.stream(xs).sum();
        long sqSum = Arrays.stream(xs).map(j -> j*j).sum();
        if(sum == 0){ return sqSum == 0 ? "1": IMPOSSIBLE;}
        long num = sqSum - sum * sum;
        long den = 2 * sum;
        return num % den == 0  ?  Long.toString(num / den) : IMPOSSIBLE;
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