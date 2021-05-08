package C2021.Round1A;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Stream;


public class PrimeTimeMid {



    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int M = Integer.parseInt(in.nextLine());
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < M; j++) {
                int[] ints = lineToInt(in.nextLine());
                int P = ints[0];
                int N = ints[1];

                for (int k = 0; k < N; k++) {
                    list.add(P);
                }
            }


            long result = solve(list);

            System.out.println(String.format("Case #%d: %d", i, result));
        }

    }

    public static long solve(List<Integer> list){
        int[] fa = new int[list.get(list.size()-1)+1];
        for (Integer i : list) {
            fa[i]++;
        }
        int maxSum = list.stream().reduce(Integer::sum).get();
        for (int i = maxSum; i >=2; i--) {
            if(test(i, fa, maxSum)) return i;
        }
        return 0;
    }

    public static boolean test(int value, int[] fa, int maxSum){
        List<Integer> primeFactors = primeFactors(value);
        primeFactors.sort(Comparator.naturalOrder());

        if(isPossible(primeFactors, fa)){
            int sum = maxSum;
            for (Integer primeFactor : primeFactors) {
                sum-=primeFactor;
            }
            return sum == value;
        }
        return false;
    }

    public static boolean isPossible( List<Integer> primeFactors, int[] fa){
        int[] copy = Arrays.copyOf(fa, fa.length);
        for (Integer primeFactor : primeFactors) {
            if(primeFactor < copy.length && copy[primeFactor] > 0){
                copy[primeFactor] --;
            }else {
                return false;
            }
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
    public static List<Integer> primeFactors(int n) {
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
        }

        // This condition is to handle the case when
        // n is a prime number greater than 2
        if (n > 2)
            result.add(n);

        return result;
    }
}