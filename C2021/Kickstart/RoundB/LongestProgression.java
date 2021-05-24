package C2021.Kickstart.RoundB;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class LongestProgression {



    public static void main(String[] args) throws Exception{
//        Scanner in = new Scanner(new BufferedReader(new FileReader("src/input.txt")));
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            in.nextLine();
            int[] a = lineToInt(in.nextLine());

            int[] reverse = IntStream.rangeClosed(1, a.length)
                    .map(index -> a[a.length - index])
                    .toArray();

            int result = Math.max(solve(a), solve(reverse));

            System.out.println(String.format("Case #%d: %d", i, result));
        }
    }

    public static int solve(int[] a){
        List<Subarray> list = new ArrayList<>();
        int prevDiff = a[1] - a [0];
        int startingIndex = 0;

        for (int i = 2; i < a.length; i++) {
            int currDiff = a[i] - a[i - 1];
            if(currDiff != prevDiff) {
                list.add(new Subarray(startingIndex, i - 1, prevDiff));
                startingIndex = i-1;
                prevDiff = currDiff;
            }
        }

        if(startingIndex != a.length-1){
            list.add(new Subarray(startingIndex, a.length-1, prevDiff));
        }

        Map<Integer, Subarray> starts = list.stream().collect(Collectors.toMap(s -> s.start, Function.identity()));
//        Map<Integer, Subarray> ends = list.stream().collect(Collectors.toMap(s -> s.end, Function.identity()));

        int max = 0;
        for (Subarray s : list) {
//           max = Math.max(max, checkLeft(s, ends, a));
           max = Math.max(max, checkRight(s, starts, a));
        }

        return max;
    }

    public static int checkLeft(Subarray s, Map<Integer, Subarray> ends, int[] a){
        int start = s.start;
        int length = s.getLength();
        if(start == 0) return length;
        if(start == 1) return length +1;
        if(a[start] - a[start - 2] == 2* s.diff){
            Subarray prev = ends.get(start - 2);
            if(prev != null && prev.diff == s.diff) return prev.getLength() + length + 1;
            else return length + 2;
        }

        return length +1;
    }

    public static int checkRight(Subarray s, Map<Integer, Subarray> starts, int[] a){
        int end = s.end;
        int length = s.getLength();
        if(end == a.length-1) return length;
        if(end == a.length-2) return length +1;
        if(a[end + 2] - a[end] == 2* s.diff){

            Subarray next = starts.get(end + 2);
            if(next != null && next.diff == s.diff) return next.getLength() + length + 1;
            else return length + 2;
        }

        return length +1;
    }


    public static class Subarray{
        int start, end, diff;

        public Subarray(int start, int end, int diff) {
            this.start = start;
            this.end = end;
            this.diff = diff;
        }

        public int getLength(){
            return end-start+1;
        }
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