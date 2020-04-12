package C2019.Round1B;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DraupnirHard {

    long[] rings = new long[7];
    static int W;

    private static boolean err = false;
    private final Scanner sc;
    private long[] days;

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        String s = read(in);
        int[] line = lineToInt(s, " ");
        int t = line[0];// Scanner has functions to read ints, longs, strings, chars, etc.

        W = line[1];
        for (int i = 1; i <= t; ++i) {
            DraupnirHard solution = new DraupnirHard(in);
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
//               DraupnirHard sol = new DraupnirHard(in);
//
//                long[] response = sol.createResponse();
//                if (!Arrays.equals(response, rings)) {
//                    throw new RuntimeException();
//                }
//            }
//        }


    }

    private void solve() {


        int d1 = 215;
        print(d1 + "");
        String result = read(sc);
        long l = Long.parseLong(result);

        this.rings[4] = get2Pow(l, 53);
        this.rings[5] = get2Pow(l, 43);
        this.rings[6] = get2Pow(l, 35);
        int d2 = 44;
        print(d2 + "");
        result = read(sc);
        l = Long.parseLong(result);
        l = l - rings[4] * (1 << 11) - rings[5] * (1 << 8) - rings[6] * (1 << 7);
        this.rings[1] = get2Pow(l, 44);
        this.rings[2] = get2Pow(l, 22);
        this.rings[3] = get2Pow(l, 14);

        print(Arrays.stream(this.rings).skip(1).mapToObj(Long::toString).collect(Collectors.joining(" ")));
        if (Integer.parseInt(read(sc)) == -1) {
            throw new RuntimeException("hoho");
        }


    }

    private long get2Pow(long l, int exp) {
        return (l % (1L << (exp + 7))) / (1L << exp);
    }

    public DraupnirHard(Scanner sc) {
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

    private static long computeSolution(int day, long[] rings) {
        BigInteger total = BigInteger.ZERO;
        for (int i = 1; i <= 6; i++) {
            total = total.add(BigInteger.valueOf(rings[i]).multiply(powerOfTwo(day / i)));
        }

        return total.mod(powerOfTwo(63)).longValueExact();
    }

    private static BigInteger powerOfTwo(int exponent) {
        return BigInteger.valueOf(2).pow(exponent);
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
