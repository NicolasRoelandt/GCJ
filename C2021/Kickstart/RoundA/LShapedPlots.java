package C2021.Kickstart.RoundA;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class LShapedPlots {


    private static int C;
    private static int R;
    private static boolean[][] grid;
    private static int[][] vSegments;
    private static int[][] hSegments;

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int[] line = lineToInt(in.nextLine());
            R = line[0];
            C = line[1];

            grid = new boolean[R][C];

            for (int j = 0; j < R; j++) {
                int[] row = lineToInt(in.nextLine());
                int finalJ = j;
                IntStream.range(0, C).forEach(k -> grid[finalJ][k] = row[k] == 1);
            }

            long result = count();

            flipHorizontally();

            result += count();

            flipHorizontally();

            flipVertically();
            result += count();

            flipHorizontally();
            result += count();

            System.out.println(String.format("Case #%d: %d", i, result));
        }
    }

    private static long count() {
        fillVerticalSegments();
        fillHorizontalSegments();
        long count = 0;
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C - 1; j++) {
                count += countLStartingIn(i, j);
            }

        }

        return count;

    }


    private static void fillVerticalSegments() {
        //segments [i][j] = s =>  longest segment qui part de i,j vers le bas a une taille de s
        vSegments = new int[R][C];
        for (int j = 0; j < C; j++) {
            for (int i = 0; i < R; i++) {
                int startIndex = i;
                while (i < R && grid[i][j]) i++;
                int endIndex = i - 1;
                int length = endIndex - startIndex + 1;

                for (; startIndex <= endIndex; startIndex++) {
                    vSegments[startIndex][j] = length;
                    length--;
                }
            }
        }
    }

    private static void fillHorizontalSegments() {
        //segments [i][j] = s =>  longest segment qui part de i,j vers la droite a une taille de s
        hSegments = new int[R][C];
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                int startIndex = j;
                while (j < C && grid[i][j]) j++;
                int endIndex = j - 1;
                int length = endIndex - startIndex + 1;

                for (; startIndex <= endIndex; startIndex++) {
                    hSegments[i][startIndex] = length;
                    length--;
                }
            }
        }
    }

    private static void flipHorizontally(){
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C/2; j++) {
                swap(i, j, i, C-1-j);
            }
        }
    }

    private static void flipVertically(){
        for (int j = 0; j < C; j++) {
        for (int i = 0; i < R/2; i++) {
                swap(i, j, R-1-i, j);
            }
        }
    }

    private static void swap(int i1, int j1, int i2, int j2){
        boolean temp = grid[i1][j1];
        grid[i1][j1] = grid[i2][j2];
        grid[i2][j2] = temp;
    }

    private static long countLStartingIn(int i, int j) {
        long count = 0;

        // -
        //|
        count += Math.max(0, Math.min(vSegments[i][j], hSegments[i][j]/2)-1);

        // ___
        //|
        count += Math.max(0, Math.min(hSegments[i][j], vSegments[i][j]/2)-1);
        return count;
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