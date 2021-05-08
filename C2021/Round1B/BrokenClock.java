package C2021.Round1B;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class BrokenClock {

    private static final long NANO = 1_000_000_000L;
    private static final long MAX_TICKS = 3600*12*NANO;
    private static final long NS_PER_MIN = 60*NANO;
    private static final long NS_PER_HOUR = 3600*NANO;

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            long[] line = lineToLong(in.nextLine());

            long[] result = solve(line);

            System.out.println(String.format("Case #%d: %s", i, Arrays.stream(result).mapToObj(Long::toString).collect(Collectors.joining(" "))));
        }
    }

    public static long[] solve(long[] input) {
        ArrayList<long[]> permutations = permutations(input);

        for (long i = 0; i < 60*NANO; i++) {
            for (long[] permutation : permutations) {

                long[] compute = compute(permutation, i);
                if (compute != null) {
                    return compute;
                }
            }

        }

       return new long[]{0, 0, 0, 0};

    }

    private static long[] compute(long[] permutation, long nano_offset) {
        long offset = MAX_TICKS-(permutation[0] % 720);
        long anglePlusOff = nano_offset*720+offset;
        long p0 = (permutation[0]+anglePlusOff)%MAX_TICKS;
        long p1 = (permutation[1]+anglePlusOff)%MAX_TICKS;
        long p2 = (permutation[2]+anglePlusOff)%MAX_TICKS;
        long ns_tot = p0/720;
        long seconds = ns_tot / NANO;
        long ns_in_minute = p1/ 12;
        if (ns_in_minute % NS_PER_MIN != ns_tot) return null;

        long minutes = ns_in_minute /  NS_PER_MIN;

        long minutes_in_hour = p2 / NS_PER_MIN;
        if (p2 % NS_PER_HOUR - minutes*NS_PER_MIN != ns_tot) return null;
        long hours = minutes_in_hour / 60;

        return new long[]{hours, minutes, seconds, ns_tot%NANO};

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

    public static long[] lineToLong(String line) {
        return Stream.of(line.split(" ")).mapToLong(Long::parseLong).toArray();
    }

    public static String getBinaryString(long value, int size) {
        return Long.toBinaryString(1L << size | value).substring(1, size + 1);
    }

    public static ArrayList<long[]> permutations(long[] str) {
        ArrayList<long[]> result = new ArrayList<long[]>();
        if (str.length == 0) {
            return result;
        }
        permutations(str, 0, str.length, result);
        return result;
    }

    private static void permutations(long[] arr, int loc, int len, ArrayList<long[]> result) {
        if (loc == len) {
            result.add(Arrays.copyOf(arr, arr.length));
            return;
        }

        // Pick the element to put at arr[loc]
        permutations(arr, loc + 1, len, result);
        for (int i = loc + 1; i < len; i++) {
            // Swap the current arr[loc] to position i
            swap(arr, loc, i);
            permutations(arr, loc + 1, len, result);
            // Restore the status of arr to perform the next pick
            swap(arr, loc, i);
        }
    }

    private static void swap(long[] arr, int i, int j) {
        long tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}