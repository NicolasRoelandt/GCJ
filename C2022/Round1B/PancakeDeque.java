package C2022.Round1B;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class PancakeDeque {


    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int N = Integer.parseInt(in.nextLine());
            int[] pancakesInt = lineToInt(in.nextLine());
            LinkedList<Integer> queue = new LinkedList<>();
            for (int i1 : pancakesInt) {
                queue.add(i1);
            }


            System.out.println(String.format("Case #%d: %d", i, solve(queue)));
        }
    }

    private static long solve(LinkedList<Integer> queue){
        long paid = 0;
        int mostDelicious = 0;
        while(!queue.isEmpty()){
            int delicious;
            if(queue.peekFirst() < queue.peekLast()){
                delicious = queue.pollFirst();
            } else {
                delicious = queue.pollLast();
            }
            if(delicious >= mostDelicious) {
                paid++;
                mostDelicious = delicious;
            }

        }
        return paid;
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