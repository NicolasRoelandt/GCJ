package C2019.Round2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Stream;

public class Q4 {
    static int[][] transformations;
    static int M;
    private static Metal[] metals;
    private static MetalInverted[] metalsInverted;

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            M = Integer.parseInt(in.nextLine());
            metals = new Metal[M];
            for (int j = 0; j < M; j++) {
                int[] nexts = lineToInt(in.nextLine(), " ");
                metals[j] = new Metal(j, nexts[0]-1, nexts[1]-1);
            }

            System.out.println("Case #" + i + ": " + solve());
        }
    }

    public static int solve() {
        metalsInverted = new MetalInverted[M];
        for (int i = 0; i < M; i++) {
            metalsInverted[i] = new MetalInverted(i);
        }
        for (Metal metal : metals) {
            metalsInverted[metal.m1].edges.add(metal.index);
            metalsInverted[metal.m2].edges.add(metal.index);
        }

        Set<Integer> visited = new HashSet<>();


        return 0;
    }

    public static boolean DFS() {
        return false;

    }

    public static class Metal{
        int index;
        int m1;
        int m2;

        public Metal(int index, int m1, int m2) {
            this.index = index;
            this.m1 = m1;
            this.m2 = m2;
        }
    }

    public static class MetalInverted{
        int index;
        List<Integer> edges = new ArrayList<>();

        public MetalInverted(int index) {
            this.index = index;
        }
    }

    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }
}
