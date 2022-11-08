package C2022.Kickstart.Practice2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class BuildingPalindromes {

    private static int[][] sums;

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int[] ints = lineToInt(in.nextLine());
            int N = ints[0];
            int Q = ints[1];

            sums = new int[N+1][26];

            String s = in.nextLine();

            for (int j = 0; j < N; j++) {
                sums[j+1] = Arrays.copyOf(sums[j], 26);
                sums[j+1][s.charAt(j) - 'A']++;
            }

            int count = 0;

            for (int j = 0; j < Q; j++) {
                ints = lineToInt(in.nextLine());
                int start = ints[0];
                int end = ints[1];

               if(isYes(start, end)) count++;
            }

            System.out.println(String.format("Case #%d: %d", i, count));
        }
    }

    private static boolean isYes(int start, int end){
        boolean foundOdd = false;
        for (int i = 0; i < 26; i++) {
            int countLetter = sums[end][i] - sums[start-1][i];
            if(countLetter % 2 != 0){
                if(foundOdd) return false;
                foundOdd = true;
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

    public static Stream<String> binaryStrings(int n) {
        return LongStream.range(0, 1L << n).mapToObj(l -> getBinaryString(l, n));
    }

    public static String getBinaryString(long value, int size) {
        return Long.toBinaryString(1L << size | value).substring(1, size + 1);
    }
}