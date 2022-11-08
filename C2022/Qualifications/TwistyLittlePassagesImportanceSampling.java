package C2022.Qualifications;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Stream;

public class TwistyLittlePassagesImportanceSampling {


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
            new TwistyLittlePassagesImportanceSampling(in, N, K).solve();
        }

    }


    private void solve() {
        read(sc);
        long total = 0;
        double weight = 0;
        final int count = Math.min(K, N);

        for (int i = 1; i <= count/2; i++) {
            print("T " + i);
            int[] line = lineToInt(read(sc), " ");
            int edgesA = line[1];
            total += edgesA*2;
            print("W");
            line = lineToInt(read(sc), " ");
            int edgesB = line[1];
            weight += 1 + (double)edgesA/edgesB;
        }

        double estimate = total/weight*N/2;
        print("E " + Math.round(estimate));
    }


    public TwistyLittlePassagesImportanceSampling(Scanner sc, int n, int k) {
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
