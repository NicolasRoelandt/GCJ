package C2022.Qualifications;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;

public class TwistyLittlePassages {


    private static boolean err = false;
    private final Scanner sc;
    private final int N;
    private final int K;

    public static void main(String[] args) {

        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

        String s = read(in);
        int T = lineToInt(s, " ")[0];

        for (int i = 1; i <= T; ++i) {
            int[] line = lineToInt(read(in), " ");
            int N = line[0];
            int K = line[1];
            new TwistyLittlePassages(in, N, K).solve();
        }

    }


    private void solve() {
        read(sc);
        long total = 0;
        final int count = Math.min(K / 2, N);
        long extraEdges = 0;
        Set<Integer> visited = new HashSet<>();
        for (int i = 1; i <= count; i++) {
            visited.add(i);
        }
        for (int i = 1; i <= count; i++) {
            print("T " + i);
            int[] line = lineToInt(read(sc), " ");
            total += line[1];
            print("W");
            line = lineToInt(read(sc), " ");
            int index = line[0];
            int edges = line[1];
            if(!visited.contains(index)){
                extraEdges += edges;
                visited.add(index);
            }
        }

        double estimate = (((double) total)/count*N + extraEdges)/2;
        print("E " + Math.round(estimate));
    }

    public TwistyLittlePassages(Scanner sc, int n, int k) {
        this.sc = sc;
        N = n;
        K = k;
    }

    private void print(String s) {
        System.out.println(s);
        if (err) {
            System.err.println("out: " + s);
        }
        System.out.flush();
    }

    private static String read(Scanner sc) {
        String s = sc.nextLine();
        if (err) {
            System.err.println("in: " + s);
        }
        return s;
    }

    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }
}
