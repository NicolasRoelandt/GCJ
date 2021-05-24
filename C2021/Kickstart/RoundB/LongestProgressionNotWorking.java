package C2021.Kickstart.RoundB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class LongestProgressionNotWorking {



    public static void main(String[] args) throws Exception{
        Scanner in = new Scanner(new BufferedReader(new FileReader("src/input.txt")));
//        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
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
        int diff = a[1] - a [0];
        boolean changed = false;
        int longestSoFar = 2;
        int currentlongest = 2;
        Optional<Integer> modifiedPrevious = Optional.empty();

        // case 1 : diff is same than previous
        // case 2 : diff is different but we can make one change
        // case 3 : we just made a change previously
        // case 4: none of the above
        for (int i = 2; i < a.length; i++) {
            int currDiff = a[i] - a[i - 1];
            if(modifiedPrevious.isPresent()){
                if(a[i] - modifiedPrevious.get() == diff){
                    currentlongest++;
                } else {
                    diff = currDiff;
                    currentlongest = a[i-1]- a[i-2] == diff ? 3 : 2;
                    changed = false;
                }
                modifiedPrevious = Optional.empty();
            } else if(currDiff == diff){
                currentlongest++;
                modifiedPrevious = Optional.empty();
            } else if(!changed){
                modifiedPrevious = Optional.of(a[i-1] + diff);
                changed = true;
                currentlongest++;
            } else {
                modifiedPrevious = Optional.empty();
                diff = currDiff;
                currentlongest = 2;
            }
            longestSoFar = Math.max(longestSoFar, currentlongest);
        }

        return longestSoFar;
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