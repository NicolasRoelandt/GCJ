package C2020.Round1C;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class PancakesTentative {


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
        Map<Long, Long> freq = a.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        List<Long> values = new ArrayList<>(freq.keySet());
        values.sort(Comparator.reverseOrder());
        //long max = values.stream().max(Long::compare).get();

        int s = values.size();
//        int[][] dp = new int[s][D+1];
//        for (int i = 0; i < s; i++) {
//            for (int j = 1; j <= D; j++) {
//                dp[i][j] = Integer.MAX_VALUE;
//            }
//        }
//
//        for (int i = 0; i < s; i++) {
//            for (int c = 0; c <= freq.get(values.get(i)); c++) {
//                dp[i][c] = 0;
//            }
//            for
//        }

        int min = Integer.MAX_VALUE;

        for (int i = 0; i < s; i++) {
            min = Math.min(findMinOfValue(freq, values, i), min);
        }

        return min;
    }

    private static int findMinOfValue(Map<Long, Long> freq, List<Long> values, int i) {
        int cuts = 0;
        Long value = values.get(i);
        long target = D;

        List<Long> multiples = values.stream().filter(l -> l % value == 0).collect(Collectors.toList());
        Collections.reverse(multiples);

        for (long multiple : multiples) {
                long parts = multiple / value;
                long cutsPerMult = parts - 1;
                Long multCount = freq.get(multiple);
                if (multCount * parts >= target) {
                    long realMult = target / parts;
                    cuts += realMult * cutsPerMult;
                    long remaining = target % parts;
                    cuts += remaining;
                    return cuts;
                } else {
                    target -= multCount * parts;
                    cuts += multCount * cutsPerMult;
                }
        }

        for (int j = 0; j < i; j++) {
            long other = values.get(i);
            if (other / value == 0) continue;

            long parts = other / value;
            Long count = freq.get(other);
            if (count * parts >= target) {
                cuts += target;
                return cuts;
            } else {
                target -= count * parts;
                cuts += count * parts;
            }
        }

        if(i == values.size()-1) {
            long totalSlices = D - target;

            int min = Integer.MAX_VALUE;
            for (long j = 2; j <= D; j++) {
                List<Long> newValues = new ArrayList<>();
                newValues.add(j);
                newValues.add(1L);
                Map<Long, Long> newFreq = new HashMap<>();
                newFreq.put(j, totalSlices);
                newFreq.put(1L, 0L);
                min = Math.min(min,cuts+ findMinOfValue(newFreq, newValues, 1));
            }
            return min;

        } else {
            return Integer.MAX_VALUE;
        }
    }

    private static int getMin(int min, int cuts, long slices) {
        if (slices >= D) {
            min = Math.min(cuts, min);
        }
        return min;
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