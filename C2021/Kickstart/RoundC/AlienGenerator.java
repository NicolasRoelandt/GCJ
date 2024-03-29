package C2021.Kickstart.RoundC;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class AlienGenerator {



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
        long count = 1;
        Map<Long, Integer> primeFactors = primeFactors(G);
        for (Long prime : primeFactors.keySet()) {
            if(prime > 2){
                count *= primeFactors.get(prime)+1;
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

    public static Map<Long, Integer> primeFactors(long n) {
        Map<Long, Integer> result = new HashMap<>();

        // Print the number of 2s that divide n
        while (n%2==0)
        {
            result.merge(2L, 1, Integer::sum);
            n /= 2;
        }

        // n must be odd at this point.  So we can
        // skip one element (Note i = i +2)
        for (long i = 3; i <= Math.sqrt(n); i+= 2)
        {
            // While i divides n, print i and divide n
            while (n%i == 0)
            {
                result.merge(i, 1, Integer::sum);
                n /= i;
            }
        }

        // This condition is to handle the case when
        // n is a prime number greater than 2
        if (n > 2) result.merge(n, 1, Integer::sum);

        return result;
    }
}