package C2021.Round1A;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;


public class PrimeTimeEasy {



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
        long max = 0;

        for (long i = 0; i < 1L<< list.size(); i++) {
            max = Math.max(compute(i, list), max);
        }
        return max;

    }

    private static long compute(long number, List<Integer> list){
        int l = list.size();
        String s = Long.toBinaryString(1L << l | number).substring(1, l + 1);
        ArrayList<Integer> add = new ArrayList<>();
        ArrayList<Integer> mult = new ArrayList<>();
        for (int i = 0; i < l; i++) {
            if(s.charAt(i) == '0') add.add(list.get(i));
            else mult.add(list.get(i));
        }

        if(add.isEmpty() || mult.isEmpty()) return 0;

        BigInteger sum = add.stream().map(BigInteger::valueOf).reduce(BigInteger::add).get();
        BigInteger product = mult.stream().map(BigInteger::valueOf).reduce(BigInteger::multiply).get();

        if(sum.equals(product)){
            return sum.longValueExact();
        }
        return 0;
    }

    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }

    public static int[] lineToInt(String line) {
        return lineToInt(line, " ");
    }
}