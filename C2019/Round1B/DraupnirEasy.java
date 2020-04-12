package C2019.Round1B;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DraupnirEasy {

    long[] rings = new long[7];
    static int W;

    private static boolean err = true;
    private final Scanner sc;
    private long[] days;

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        String s = read(in);
        int[] line = lineToInt(s, " ");
        int t = line[0];// Scanner has functions to read ints, longs, strings, chars, etc.

        W = line[1];
        for (int i = 1; i <= t; ++i) {
            DraupnirEasy solution = new DraupnirEasy(in);
            solution.solve();
        }

//        Random random = new Random();
//
//        for (int j = 0; j < 100_000; j++) {
//            long[] rings = new long[7];
//            for (int i = 1; i <= 6; i++) {
//                rings[i] = random.nextInt(101);
//            }
//
//            if (Arrays.stream(rings).sum() != 0) {
//                C2019.Round1B.C2019.Round1C.Arrangers sol = new C2019.Round1B.C2019.Round1C.Arrangers(in);
//                long[] days = computeSolution(rings);
//                sol.days = days;
//
//                long[] response = sol.createResponse();
//                if (!Arrays.equals(response, rings)) {
//                    throw new RuntimeException();
//                }
//            }
//        }


    }

    private void solve() {
        days = new long[W + 1];
        for (int i = 1; i <= W; i++) {
            print(i + "");
            String result = read(sc);
            days[i] = Long.parseLong(result);
        }
        long[] response = createResponse();
        print(Arrays.stream(response).skip(1).mapToObj(Long::toString).collect(Collectors.joining(" ")));

        if(Integer.parseInt(read(sc)) == -1){
            throw new RuntimeException("hoho");
        }
    }


    public DraupnirEasy(Scanner sc) {
        this.sc = sc;
    }

    private long[] createResponse() {
        for (int total = 1; total <= days[1]; total++) {
            if (possible(total)) {
                return rings;
            }
        }

        throw new IllegalStateException();
    }

    private static long[] computeSolution(long[] ringsD1) {
        long[][] rings = new long[7][7];
        rings[0] = ringsD1;

        long[] days = new long[7];
        for (int d = 1; d <= 6; d++) {
            long total = 0;
            rings[d] = Arrays.copyOf(rings[d - 1], 7);
            for (int i = 1; i <= 6; i++) {
                if (d % i == 0) {
                    rings[d][i] = rings[d][i] * 2;
                }
                total += rings[d][i];
            }
            days[d] = total;
        }

        return days;
    }

    private boolean possible(int total) {
        long[][] knownRings = new long[7][7];
        days[0] = total;
        for (int d = 1; d <= 6; d++) {
            long newRings = 0;
            for (int i = 1; i <= d; i++) {
                long prev = knownRings[i][d - 1];
                knownRings[i][d] = prev;
                if (d % i == 0) {
                    knownRings[i][d] *= 2;
                    newRings += prev;
                }
            }
            rings[d] = days[d] - days[d - 1] - newRings;
            knownRings[d][d] = rings[d] * 2;
        }
        return (computeDay6() == days[6]);

    }

    private long computeDay6() {
        long total = 0;
        for (int i = 1; i <= 6; i++) {
            long ringsT = rings[i] * (1 << 6 / i);
            total += ringsT;
        }
        return total;
    }

    private void print(String s) {
        System.out.println(s);
        if (err) System.err.println("out: " + s);
        System.out.flush();
    }

    private static String read(Scanner sc) {
        String s = sc.nextLine();
        if (err) System.err.println("in: " + s);
        return s;
    }

    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }
}
