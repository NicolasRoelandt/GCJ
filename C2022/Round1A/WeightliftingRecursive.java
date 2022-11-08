package C2022.Round1A;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class WeightliftingRecursive {

    private static int E;
    private static int W;
    private static int[][] exercises;
    private static long[] lengths;

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int[] line = lineToInt(in.nextLine());
            E = line[0];
            W = line[1];

            exercises = new int[E][];
            lengths = new long[E];

            for (int j = 0; j < E; j++) {
                exercises[j] = lineToInt(in.nextLine());
                lengths[j] = Arrays.stream(exercises[j]).sum();
            }

            long result = solve();

            System.out.println(String.format("Case #%d: %d", i, result));
        }
    }

    private static long solve() {
        return solve(0, E - 1, exercises, 0) + lengths[0] + lengths[E - 1];
    }

    private static long solve(int i, int j, int[][] exercises, long currentPrefix) {
        if (i >= j) return 0;
        int[] longestPrefix = longestPrefix(i, j, exercises);
        long prefixLength = Arrays.stream(longestPrefix).sum() + currentPrefix;
        for (int w = 0; w < W; w++) {
            for (int k = i; k <= j; k++) {
                exercises[k][w] -= longestPrefix[w];
            }
        }

        long min = Long.MAX_VALUE;
        for (int m = i; m < j; m++) {
            long operations = solve(i, m, exercises, prefixLength) + solve(m + 1, j, exercises, prefixLength) + lengths[m] + lengths[m + 1] - 2 * prefixLength;
            min = Math.min(operations, min);
        }

        for (int w = 0; w < W; w++) {
            for (int k = i; k <= j; k++) {
                exercises[k][w] += longestPrefix[w];
            }
        }

        return min;
    }

    private static int[] longestPrefix(int i, int j, int[][] exercises) {
        int[] result = new int[W];
        for (int w = 0; w < W; w++) {
            int min = Integer.MAX_VALUE;
            for (int k = i; k <= j; k++) {
                min = Math.min(min, exercises[k][w]);
            }
            result[w] = min;
        }
        return result;
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
