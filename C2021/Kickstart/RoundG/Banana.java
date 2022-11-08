package C2021.Kickstart.RoundG;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class Banana {



    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int[] ints = lineToInt(in.nextLine());
            int K = ints[1];
            int[] farm = lineToInt(in.nextLine());


            int solve = solve(K, farm);

            System.out.println(String.format("Case #%d: %s", i, solve));
        }
    }

    private static int solve(int K, int[] farm){
        int n = farm.length;
        int[] runningSum = new int[n+1];
        for (int i = 0; i < n; i++) {
            runningSum[i+1] = runningSum[i] + farm[i];
        }

        int min = Integer.MAX_VALUE;
        for (int start1 = 0; start1 < n; start1++) {
            for (int end1 = start1; end1 < n; end1++) {
                int row1 = sum(runningSum, start1, end1);
                if(row1 > K) continue;
                int size1 = end1-start1 +1;
                if(row1 == K) {
                    min = Math.min(min, size1);
                    continue;
                }

                int target = K - row1;

                for (int start2 = end1 + 2; start2 < n; start2++) {
                    int bs = Arrays.binarySearch(runningSum, start2, n+1, target + runningSum[start2]);
                    if(bs >=0){
                        min = Math.min(min, size1 + bs - start2);
                    }
                }
            }

        }

        return min == Integer.MAX_VALUE ? -1 : min;

    }

    private static int sum(int[] runningSum, int start, int end){
       return runningSum[end+1] - runningSum[start];
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