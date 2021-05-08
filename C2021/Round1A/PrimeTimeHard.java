package C2021.Round1A;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class PrimeTimeHard {



    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int M = Integer.parseInt(in.nextLine());
            Map<Integer,Integer> primes = new HashMap<>();
            for (int j = 0; j < M; j++) {
                int[] line = lineToInt(in.nextLine());
                primes.put(line[0],line[1]);

            }


            long result = solve(primes);

            System.out.println(String.format("Case #%d: %d", i, result));
        }

    }

    public static long solve(Map<Integer,Integer> primes){

        long maxSum = primes.entrySet().stream().mapToLong(e -> e.getKey() * e.getValue()).sum();

        for (long i = maxSum; i >= Math.max(maxSum-3025, 1); i--) {
            if(test(i, primes, maxSum)) return i;
        }
        return 0;
    }

    public static boolean test(long value, Map<Integer,Integer> primes, long maxSum){
        List<Integer> primeFactors = primeFactors(value, 499);
        if(primeFactors == null){
            return false;
        }
        primeFactors.sort(Comparator.naturalOrder());

        if(isPossible(primeFactors, primes)){
            long sum = maxSum;
            for (Integer primeFactor : primeFactors) {
                sum-=primeFactor;
            }
            return sum == value;
        }
        return false;
    }

    public static boolean isPossible( List<Integer> primeFactors, Map<Integer,Integer> primes){

        Map<Integer, Long> factors = primeFactors.stream().collect(Collectors.groupingBy(
                Function.identity(), Collectors.counting()));

        for (Map.Entry<Integer, Long> entry : factors.entrySet()) {
            if(!primes.containsKey(entry.getKey()) || primes.get(entry.getKey()) < entry.getValue()) return false;
        }
        return true;
    }

    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }

    public static int[] lineToInt(String line) {
        return lineToInt(line, " ");
    }

    // from https://www.geeksforgeeks.org/print-all-prime-factors-of-a-given-number/

    // A function to print all prime factors
    // of a given number n
    public static List<Integer> primeFactors(long n, int maxPrime) {
        ArrayList<Integer> result = new ArrayList<>();

        // Print the number of 2s that divide n
        while (n%2==0)
        {
            result.add(2);
            n /= 2;
        }

        // n must be odd at this point.  So we can
        // skip one element (Note i = i +2)
        for (int i = 3; i <= Math.sqrt(n); i+= 2)
        {
            // While i divides n, print i and divide n
            while (n%i == 0)
            {
                result.add(i);
                n /= i;
            }

            if(i > maxPrime){
                return null;
            }
        }

        // This condition is to handle the case when
        // n is a prime number greater than 2
        if (n > 2 && n < maxPrime)
            result.add((int)n);

        return result;
    }
}