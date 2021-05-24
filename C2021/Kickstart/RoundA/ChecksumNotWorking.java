package C2021.Kickstart.RoundA;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class ChecksumNotWorking {


    private static int N;
    private static boolean[][] matrix;
    private static int[][] B;

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            N = Integer.parseInt(in.nextLine());

            int[][] matrixInt = createMatrix(in);

            matrix = new boolean[N][N];
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < N; k++) {
                    if (matrixInt[j][k] >= 0) matrix[j][k] = true;
                }
            }

            B = createMatrix(in);
            in.nextLine();
            in.nextLine();


            long result = solveBF();

            System.out.println(String.format("Case #%d: %d", i, result));
        }
    }

    private static int[][] createMatrix(Scanner in) {
        int[][] matrix = new int[N][];
        for (int i = 0; i < N; i++) {
            matrix[i] = lineToInt(in.nextLine());
        }
        return matrix;
    }

//    private static long solve() {
//        long hours = 0;
//        fill();
//        int[] smallest;
//        while ((smallest = findSmallest()) != null) {
//            hours += smallest[2];
//            matrix[smallest[0]][smallest[1]] = true;
//            fill();
//        }
//        return hours;
//    }

    private static long solveBF(){
        fill(matrix);
        return solveBF(matrix, 0);
    }

    private static long solveBF(boolean[][] matrix, long currentCount){
        List<int[]> missing = getAllMissing(matrix);
        if(missing.isEmpty()) return currentCount;

        long min = Integer.MAX_VALUE;
        for (int[] ints : missing) {
            boolean[][] copy = copy(matrix);
            int i = ints[0];
            int j = ints[1];
            copy[i][j] = true;
            fill(copy);
            min = Math.min(min, solveBF(copy, currentCount+ B[i][j]));
        }
        return min;

    }

    private static boolean[][] copy(boolean[][] matrix){
        boolean[][] result = new boolean[N][];
        for (int i = 0; i < N; i++) {
            result[i] = Arrays.copyOf(matrix[i], N);
        }

        return result;
    }

    private static List<int[]> getAllMissing(boolean[][] matrix){
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if(!matrix[i][j]){
                    list.add(new int[]{i,j});
                }
            }
        }
        return list;
    }

    private static int[] findSmallest() {
        int min = Integer.MAX_VALUE;
        int si = -1;
        int sj = -1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!matrix[i][j] && B[i][j] < min) {
                    si = i;
                    sj = j;
                    min = B[i][j];
                }
            }
        }

        if (si == -1) return null;
        return new int[]{si, sj, min};
    }


    private static void fill(boolean[][] matrix) {
        boolean change = true;
        while(change) {
            for (int i = 0; i < N; i++) {
                fillLine(matrix[i]);
            }
            for (int j = 0; j < N; j++) {
                change = fillRow(matrix, j);
            }
        }
    }

    private static boolean fillLine(boolean[] line) {
        int index = -1;
        for (int j = 0; j < N; j++) {
            if (!line[j]) {
                if (index >= 0) return false;
                index = j;
            }
        }
        if (index != -1) {
            line[index] = true;
            return true;
        }
        return false;
    }

    private static boolean fillRow(boolean[][] matrix, int j) {
        int index = -1;
        for (int i = 0; i < N; i++) {
            if (!matrix[i][j]) {
                if (index >= 0) return false;
                index = i;
            }
        }

        if (index != -1) {
            matrix[index][j] = true;
            return true;
        }
        return false;
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