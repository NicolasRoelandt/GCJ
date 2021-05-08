package C2021.Round1B;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class Subtransmutation {



    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int[] input = lineToInt(in.nextLine());
            int A = input[1];
            int B = input[2];

            long[] metals = lineToLong(in.nextLine());

            int j;
            for (j = metals.length-1; !isPossible(j, metals, A, B) && j <402; j++);


            String result = j <402 ?  Integer.toString(j + 1) : "IMPOSSIBLE";
            System.out.println(String.format("Case #%d: %s", i, result));
        }
    }

    public static boolean isPossible(int n, long[] metals, int A, int B){
        try{
        long[] D = Arrays.copyOf(metals, metals.length);
        long[] H = new long[n+1];
        H[n] = 1;

        while(true){
            removeIntersection(H, D);
            if(isEmpty(D)) return true;
            if(isEmpty(H)) return false;
            int i = IntStream.range(0, n + 1).reduce(0, (acc, j) -> H[j] > 0 ? j : acc);
            long c = H[i];
            H[i] -= c;
            if(i - B >=0) {
                H[i-B] =  Math.addExact(H[i-B], c);
            }
            if(i - A >=0)  H[i-A] =  Math.addExact(H[i-A], c);
        }} catch(Exception e){
            return false;
        }
    }

    public static void removeIntersection(long[] H, long[] D){
        for (int i = 0; i < D.length; i++) {
            long diff = Math.min(H[i],D[i]);
            H[i] -= diff;
            D[i] -= diff;
        }
    }

    public static boolean isEmpty(long[] array){
        return Arrays.stream(array).allMatch(i -> i == 0);
    }

    public static long[] lineToLong(String line) {
        return Stream.of(line.split(" ")).mapToLong(Long::parseLong).toArray();
    }


    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
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