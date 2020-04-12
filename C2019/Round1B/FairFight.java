package C2019.Round1B;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;

import static java.lang.Math.abs;
import static java.lang.Math.max;


public class FairFight {


    private static int K;
    private static int[] D;
    private static int[] C;
    private static int N;


    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int[] line = lineToInt(in.nextLine(), " ");
            N = line[0];
            K = line[1];

            D = lineToInt(in.nextLine(), " ");
            C = lineToInt(in.nextLine(), " ");

            long result = solve();



            System.out.println("Case #" + i + ": " + result);
        }

    }

    private static long solve() {
        long  count = 0;
        for (int l = 0; l < N; l++) {
            for (int r = l; r < N; r++) {
                int d = choseBest(l, r, D);
                int c = choseBest(l, r, C);
                if(abs(d-c) <= K) count++;
            }
        }
        return count;
    }

    private static int choseBest(int l, int r, int[] skills) {
        int best = 0;
        for (int i = l; i <= r; i++) {
            best = max(skills[i],best);
        }
        return best;
    }

    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }
}