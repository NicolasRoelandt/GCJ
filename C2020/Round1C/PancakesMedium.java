package C2020.Round1C;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class PancakesMedium {


    private static int D;
    private static int N;
    private static List<Long> a;

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int[] line = lineToInt(in.nextLine());
            N = line[0];
            D = line[1];
            a = lineToLong(in.nextLine());
            String result = "" + solve(in);

            System.out.println(String.format("Case #%d: %s", i, result));
        }

    }

    private static int solve(Scanner in) {
        Collections.sort(a);
        int min = D;
        Set<Fraction> computed = new HashSet<>();


        for (Long slice : a) {
            for (int i = 1; i <= D; i++) {
                Fraction target = new Fraction(slice, i);
                if (!computed.contains(target)) {
                    int cuts = getCuts(target);
                    min = Math.min(cuts, min);
                    computed.add(target);
                }
            }
        }


        return min;
    }

    public static int getCuts(Fraction target) {
        int remaining = D;
        int K = 0;
        List<Long> others = new ArrayList<>(a.size());
        for (Long slice : a) {
            Fraction divide = target.divide(slice);
            if (divide.den == 1) {
                long numberOfSlices = divide.num;
                if (numberOfSlices > remaining) {
                    return D - K;
                } else {
                    K += 1;
                    remaining -= numberOfSlices;
                }
            } else {
                others.add(slice);
            }
        }

        for (Long other : others) {
            if (remaining <= 0) return D - K;
            remaining -= target.divide(other).integerPart();
        }

        if (remaining <= 0) return D - K;
        else return Integer.MAX_VALUE;
    }

    private static class Fraction {
        public long num, den;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Fraction fraction = (Fraction) o;
            return num == fraction.num &&
                    den == fraction.den;
        }

        @Override
        public int hashCode() {
            return Objects.hash(num, den);
        }

        public Fraction(long num, long den) {
            long gcd = gcd(num, den);
            this.num = num / gcd;
            this.den = den / gcd;
        }

        public Fraction divide(long value) {
            return multiply(new Fraction(value, 1), inverse());
        }

        public Fraction inverse() {
            return new Fraction(den, num);
        }

        public Fraction multiply(Fraction a, Fraction b) {
            return new Fraction(a.num * b.num, a.den * b.den);
        }


        public double val() {
            return ((double) num) / den;
        }

        public long integerPart() {
            return num / den;
        }
    }

    public static long gcd(long n1, long n2) {
        if (n2 == 0) {
            return n1;
        }
        return gcd(n2, n1 % n2);
    }


    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }

    public static int[] lineToInt(String line) {
        return lineToInt(line, " ");
    }

    public static List<Long> lineToLong(String line) {
        return Stream.of(line.split(" ")).map(Long::parseLong).collect(Collectors.toList());
    }
}