package C2022.Round1B;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class ControlledInflationOvercomplexified {


    private static int N;
    private static int P;
    private static long[][] products;

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int[] line = lineToInt(in.nextLine());
            N = line[0];
            P = line[1];

            products = new long[N][];

            for (int j = 0; j < N; j++) {
                products[j] = lineToLong(in.nextLine());
            }

            long result = solve();

            System.out.println(String.format("Case #%d: %d", i, result));
        }
    }

    private static long solve(){
        long[][] dp = new long[P][N+1];
        for (int j = N-1; j >=0 ; j--) {
            for (int i = 0; i < P; i++) {
                long iVal = j == 0 ? 0 : products[j-1][i];
                long  minimum = Long.MAX_VALUE;
                for (int k = 0; k < P; k++) {
                   minimum = Math.min(minimum(iVal, k, products[j]) + dp[k][j+1], minimum);
                }

                dp[i][j] = minimum;
            }
        }
        return dp[0][0];
    }

    private static void reverseArray(long[] arr){
        for( int i = 0; i < arr.length/2; ++i )
        {
            long temp = arr[i];
            arr[i] = arr[arr.length - i - 1];
            arr[arr.length - i - 1] = temp;
        }
    }

    private static long minimum(long iVal, int k, long[] cust){
        long[] toSort = new long[P-1];
        int index = 0;
        for (int j = 0; j < P; j++) {
            if(j != k){
                toSort[index] = cust[j];
                index++;
            }
        }
        Arrays.sort(toSort);

        if(iVal > cust[k]){
            reverseArray(toSort);
        }

        long count = Math.abs(iVal - toSort[0]);
        for (int i = 1; i < toSort.length; i++) {
            count += Math.abs(toSort[i] - toSort[i-1]);
        }

        count += Math.abs(cust[k] - toSort[toSort.length-1]);

        return count;
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