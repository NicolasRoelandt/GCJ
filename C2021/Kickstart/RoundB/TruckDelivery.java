package C2021.Kickstart.RoundB;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

// passes all test sets
public class TruckDelivery {

    static TreeMap<Integer, Long>[] gcds;


    public static void main(String[] args) {

        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int[] line = lineToInt(in.nextLine());
            int N = line[0];
            int Q = line[1];

            gcds = new TreeMap[N + 1];
            gcds[1] = new TreeMap<>();
            gcds[1].put(1, 0L);

            for (int j = 0; j < N - 1; j++) {
                long[] longs = lineToLong(in.nextLine());
                int X = (int) longs[0];
                int Y = (int) longs[1];
                int L = (int) longs[2];
                long A = longs[3];

                if (gcds[X] == null) {
                    int temp = X;
                    X = Y;
                    Y = temp;
                }

                TreeMap<Integer, Long> map = new TreeMap<>(gcds[X]);
                gcds[Y] = map;
                Map.Entry<Integer, Long> floor = map.floorEntry(L);

                // Pour un poids de L, gcd = gcd(A, gcd du poids juste en dessous de L)
                long gcd = gcd(floor.getValue(), A);
                map.put(L, gcd);
                NavigableMap<Integer, Long> tail = map.tailMap(L, false);

                long previousGcd = gcd;
                // Pour les poids au dessus de L, if faut rajouter A dans leur GCD
                for (Iterator<Map.Entry<Integer, Long>> iterator = tail.entrySet().iterator(); iterator.hasNext(); ) {
                    Map.Entry<Integer, Long> entry = iterator.next();
                    gcd = gcd(A, entry.getValue());
                    entry.setValue(gcd);
                    if (previousGcd == gcd) {
                        iterator.remove();
                    }
                    previousGcd = gcd;
                }
            }

            long[] result = new long[Q];

            for (int j = 0; j < Q; j++) {
                line = lineToInt(in.nextLine());
                int C = line[0];
                int W = line[1];
                result[j] = gcds[C].floorEntry(W).getValue();
            }

            String s = Arrays.stream(result).mapToObj(Long::toString).collect(Collectors.joining(" "));

            System.out.println(String.format("Case #%d: %s", i, s));
        }
    }

    public static long gcd(long n1, long n2) {
        if (n2 == 0) {
            return n1;
        }
        return gcd(n2, n1 % n2);
    }

    private static int solve() {
        return 0;
    }

    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }

    public static long[] lineToLong(String line) {
        return Stream.of(line.split(" ")).mapToLong(Long::parseLong).toArray();
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