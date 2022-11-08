package C2022.Kickstart.Practice2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Q4 {
    private static int[][] grid;
    private static int C;
    private static int R;


    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int[] ints = lineToInt(in.nextLine());
            R = ints[0];
            C = ints[1];
            grid = new int[R][];
            for (int j = 0; j < R; j++) {
                grid[j] = Arrays.stream(in.nextLine().split("")).mapToInt(Integer::parseInt).toArray();
            }
            System.out.println(String.format("Case #%d: %d", i, solve()));
        }
    }

    private static int solve(){
        int max = 0;
        int i = 0;
        int j = 0;
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                int dist = computeDist(r, c);
                if(dist > max){
                    max = dist;
                    i = r;
                    j = c;
                }
            }
        }

        grid[i][j] = 1;

        int maxDist = 0;
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                maxDist = Math.max(maxDist, computeDist(r, c));
            }
        }
        return maxDist;
    }

    private static int computeDist(int r, int c){
        int minDist = Integer.MAX_VALUE;
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if(grid[i][j] == 1){
                    minDist = Math.min(minDist, getDist(r, c, i, j));
                }
            }
        }

        return minDist;
    }

    private static int getDist(int r1, int c1, int r2, int c2) {
        return Math.abs(r1 - r2) + Math.abs(c1 - c2);
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