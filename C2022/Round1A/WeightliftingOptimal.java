package C2022.Round1A;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class WeightliftingOptimal {


    private static int E;
    private static int W;
    private static int[][] exercises;

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int[] line = lineToInt(in.nextLine());
            E = line[0];
            W = line[1];

            exercises = new int[E][];

            for (int j = 0; j < E; j++) {
                exercises[j] = lineToInt(in.nextLine());
            }

            long result = solve();

            System.out.println(String.format("Case #%d: %d", i, result));
        }
    }

    private static long solve(){
        long[][] prefixes = new long[E][E];
        for (int i = 0; i < E; i++) {
            for (int j = i; j < E; j++) {
                prefixes[i][j] = longestPrefix(i, j, exercises);
            }
        }

        long[][] dp = new long[E][E];


            for (int l = 2; l <= E; l++) {
                for (int i = 0; i <= E-l; i++) {
                int j = i + l-1;
                long min = Long.MAX_VALUE;
                for (int m = i; m < j; m++) {
                    long operations = dp[i][m] + dp[m+1][j] + 2*(prefixes[i][m] + prefixes[m+1][j] - 2*prefixes[i][j]);
                    min = Math.min(operations, min);
                }
                dp[i][j] = min;
            }
        }


       return dp[0][E-1] + 2*prefixes[0][E-1];
    }


    private static long longestPrefix(int i, int j, int[][] exercises){
        int[] result = new int[W];
        for (int w = 0; w < W; w++) {
            int min = Integer.MAX_VALUE;
            for (int k = i; k <= j; k++) {
                min = Math.min(min, exercises[k][w]);
            }
            result[w] = min;
        }
        return Arrays.stream(result).sum();
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