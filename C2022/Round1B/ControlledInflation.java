package C2022.Round1B;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LongSummaryStatistics;
import java.util.Scanner;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class ControlledInflation {


    private static class Customer {
        long min, max, diff;

        public Customer(long min, long max, long diff) {
            this.min = min;
            this.max = max;
            this.diff = diff;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int N = lineToInt(in.nextLine())[0];

            Customer prevCustomer = new Customer(0,0, 0);
            long prevDescending = 0;
            long prevAscending = 0;


            for (int j = 0; j < N; j++) {
                long[] productsArray = lineToLong(in.nextLine());
                LongSummaryStatistics stat = Arrays.stream(productsArray).summaryStatistics();
                Customer customer = new Customer(stat.getMin(), stat.getMax(), stat.getMax()-stat.getMin());

                // descending
                long withPrevDescending = prevDescending + Math.abs(customer.max - prevCustomer.min);
                long withPrevAscending = prevAscending + Math.abs(customer.max - prevCustomer.max);
                long descending = Math.min(withPrevDescending, withPrevAscending) + customer.diff;

                //ascending
                withPrevDescending = prevDescending + Math.abs(customer.min - prevCustomer.min);
                withPrevAscending = prevAscending + Math.abs(customer.min - prevCustomer.max);
                long ascending = Math.min(withPrevDescending, withPrevAscending) + customer.diff;

                prevDescending = descending;
                prevAscending = ascending;
                prevCustomer = customer;
            }

            long result = Math.min(prevDescending, prevAscending);

            System.out.println(String.format("Case #%d: %d", i, result));
        }
    }

    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }

    public static long[] lineToLong(String line) {
        return Stream.of(line.split(" ")).mapToLong(Long::parseLong).toArray();
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