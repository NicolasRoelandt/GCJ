package C2021.Kickstart.RoundC;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class RockPaperScissorsBF {
    int R = 60;
    private static int[] counts;
    private static char[] chars;


    // TLE, O(3^R)
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            in.nextLine();
            String result = playRound();
            System.out.println(String.format("Case #%d: %s", i, result));
        }
    }

    private static String playRound() {
        StringBuilder sb = new StringBuilder();
        chars = new char[]{'R', 'S', 'P'};

        double expectedResult = 0;
        counts = new int[3];

        counts[0] = 1;
        expectedResult += 7d/15;
        Object[] result = playRoundRecursive(1, expectedResult, sb);
        System.out.println(result[0]);

        return (String) result[1];
    }

    private static Object[] playRoundRecursive(int day, double currentExpectedResult, StringBuilder sb){
        if(day == 60){
            return new Object[]{currentExpectedResult, sb.toString()};
        }
        List<Integer> indexes = choseIndexes();
        List<Object[]> results = new ArrayList<>();
        for (Integer index : indexes) {
            sb.append(chars[index]);
            char value = chars[index];
            double expectedResult = currentExpectedResult;
            for (int k = 0; k < 3; k++) {
                Result result = getResult(value, chars[k]);
                if (result == Result.TIE) {
                    expectedResult +=  (counts[k] / (double) day) * 2/5;
                } else if (result == Result.WIN) {
                    expectedResult += counts[k] / (double) day;
                }
            }
            counts[(index + 2) % 3]++;
            results.add(playRoundRecursive(day+1, expectedResult, sb));
            counts[(index + 2) % 3]--;
            sb.deleteCharAt(sb.length()-1);
        }

        Optional<Object[]> max = results.stream().max(Comparator.comparingDouble(o -> (Double) o[0]));
        return max.get();
    }



    private static List<Integer> choseIndexes() {
        int max = Math.max(Math.max(counts[0], counts[1]), counts[2]);

        List<Integer> result = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            if(counts[i] == max){
                result.add((i + 2) % 3);
            }
        }
        return result;
    }

    private static Result getResult(char mine, char his) {
        if (mine == his) return Result.TIE;
        if (mine == 'R' && his == 'S' || mine == 'S' && his == 'P' || mine == 'P' && his == 'R') return Result.WIN;
        return Result.LOSE;
    }

    private static int solve() {
        return 0;
    }

    private enum Result {
        WIN, TIE, LOSE;
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