package C2021.Round1A;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;


public class AppendSort {

    long count = 0;


    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            in.nextLine();
            long[] ints = lineToInt(in.nextLine());

            BigInteger[] bis = Arrays.stream(ints).mapToObj(BigInteger::valueOf).toArray(BigInteger[]::new);


            long result = new AppendSort().solve(bis);

            System.out.println(String.format("Case #%d: %d", i, result));
        }
    }

    public long solve(BigInteger[] list) {
        BigInteger previous = BigInteger.valueOf(0);

        for (int i = 0; i < list.length; i++) {
            list[i] = computeValue(list[i], previous);
            previous = list[i];
        }
        return count;
    }

    public BigInteger computeValue(final BigInteger value, final BigInteger previous) {



        if (value.compareTo(previous) > 0) {
            return value;
        }

        String prev_string = previous.toString();
        String val_string = value.toString();

        if (prev_string.startsWith(val_string) && previous.compareTo(value) != 0) {
            BigInteger next = previous.add(BigInteger.valueOf(1));
            if (next.toString().startsWith(val_string)) {
                count += next.toString().length() - val_string.length();
                return next;
            }
        }



        BigInteger substring = new BigInteger(prev_string.substring(0, val_string.length()));
        int diff = prev_string.length() - val_string.length();
        if (value.compareTo(substring) <= 0) diff++;
        count += diff;
        BigInteger result = value;
        for (int i = 0; i < diff; i++) {
            result=result.multiply(BigInteger.TEN);
        }
        return result;

    }

    public static long[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToLong(Long::parseLong).toArray();
    }

    public static long[] lineToInt(String line) {
        return lineToInt(line, " ");
    }
}