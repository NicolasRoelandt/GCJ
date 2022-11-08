package C2022.Qualifications;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class PunchedCards {



    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int[] line = lineToInt(in.nextLine());


            String result = solve(line[0], line[1]);

            System.out.println(String.format("Case #%d:\n%s", i, result));
        }
    }

    private static String solve(int R, int C){
        StringBuilder sb = new StringBuilder();
        sb.append("..");
        sb.append(line(C-1));
        sb.append("..");
        sb.append(cells(C-1));

        for (int i = 0; i < R-1; i++) {
            sb.append(line(C));
            sb.append((cells(C)));
        }
        sb.append(line(C));

        return sb.toString();
    }

    private static CharSequence line (int C){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < C; i++) {
            sb.append('+');
            sb.append('-');
        }
        sb.append('+');
        sb.append('\n');
        return sb;
    }

    private static CharSequence cells (int C){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < C; i++) {
            sb.append('|');
            sb.append('.');
        }
        sb.append('|');
        sb.append('\n');
        return sb;
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