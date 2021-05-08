package C2021.Round1C;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class ClosestPick {


    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int[] input = lineToInt(in.nextLine());
            int N = input[0];
            int K = input[1];
            int[] tickets = lineToInt(in.nextLine());

            double solve = solve(K, tickets);

            System.out.println(String.format("Case #%d: %f", i, solve));
        }
    }

    public static double solve(int K, int[] tickets) {
        List<Integer> list = Arrays.stream(tickets).boxed().distinct().sorted(Integer::compareTo).collect(Collectors.toList());
        list.add(K+1);
        int previous = 0;
        int[] holes = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            Integer value = list.get(i);
            holes[i] = Math.max(0, value - previous - 1);
            previous = value;
        }
        int max = maximize(holes);

        return ((double) max)/K;

    }

    public static int maximize(int[] holes){
        int max = 0;
        for (int i = 0; i < holes.length; i++) {
            for (int j = 0; j < holes.length; j++) {
                max = Math.max(max, compute(holes, i, j));
            }
        }
        return max;
    }

    // attention aux extremes!
    public static int compute(int[] holes, int i, int j){
        // meme trou
        if(i == j){
            return holes[i];
        } else {
            return oneCardInHole(holes, i) + oneCardInHole(holes, j);
        }
    }

    public static int oneCardInHole(int[] holes, int i){
        if(isExtreme(holes, i)){
            return holes[i];
        } else {
            return (holes[i]+1)/2;
        }
    }

    public static boolean isExtreme(int[] holes, int i){
        return i == 0 || i == holes.length-1;
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