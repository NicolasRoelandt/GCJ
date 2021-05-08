package C2019.Round2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.stream.Stream;


public class Q3 {



    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int c = 1; c <= t; ++c) {
            int N = Integer.parseInt(in.nextLine());
            int[][] order = new int[N][2];

            for (int j = 0; j < N; j++) {
                order[j] = lineToInt(in.nextLine(), " ");
            }


            int[] sol = solve(order);
            String val = sol == null ? "IMPOSSIBLE" : sol[0] + " " + sol[1];
            System.out.println("Case #" + c + ": " + val);
        }

    }

    public static int[] solve(int[][] order){
        for (int c = 1; c <= 100; c++) {
            for (int j = 0; j <= 100; j++) {
                if(isValid(c,j, order)) return new int[]{c,j};
            }
        }

        return null;
    }

    private static boolean isValid(int c, int j, int[][] order) {
        for (int i = 1; i < order.length; i++) {
            int[] prev = order[i-1];
            int[] cur = order[i];
            if(evaluate(c, j, prev) >= evaluate(c,j,cur)) return false;
        }
        return true;
    }

    private static long evaluate(int c, int j, int[] mol) {
        return c*mol[0] + j*mol[1];
    }

    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }
}