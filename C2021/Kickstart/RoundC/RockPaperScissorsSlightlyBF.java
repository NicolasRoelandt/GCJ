package C2021.Kickstart.RoundC;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class RockPaperScissorsSlightlyBF {
    private static int W;
    private static int E;
    int R = 60;
    private static int[] counts;
    private static char[] chars;

    private final static int NUMBER_OF_DAYS = 12;


    // this solves test set 2
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        in.nextLine();
        for (int i = 1; i <= t; ++i) {
            int[] line = lineToInt(in.nextLine());
            W = line[0];
            E = line[1];
            String result = playRound();
            System.out.println(String.format("Case #%d: %s", i, result));
        }
    }

    private static String playRound() {
        StringBuilder sb = new StringBuilder();
        chars = new char[]{'R', 'S', 'P'};

        double expectedResult = 0;
        counts = new int[3];

        counts[2] = 1;
        expectedResult += (W + E) / 3d;
        sb.append('R');
        Data result = playRoundRecursive(1, expectedResult, sb, NUMBER_OF_DAYS);
        System.out.println(result.result);

        return result.s;
    }

    private static Data playRoundRecursive(int day, double currentExpectedResult, StringBuilder sb, int remainingDays) {
        if (day == 60) {
            return new Data(currentExpectedResult, sb.toString());
        }
        if (remainingDays == 0) {
            return new Data(currentExpectedResult, sb.toString());
        }
        List<Data> results = new ArrayList<>();
        for (int index = 0; index < 3; index++) {

            sb.append(chars[index]);
            char value = chars[index];

            double expectedResult = currentExpectedResult;
            for (int k = 0; k < 3; k++) {
                Result result = getResult(value, chars[k]);
                if (result == Result.TIE) {
                    expectedResult += (counts[k] * E / (double) day);
                } else if (result == Result.WIN) {
                    expectedResult += counts[k] * W / (double) day;
                }
            }
            counts[(index + 2) % 3]++;
            results.add(playRoundRecursive(day + 1, expectedResult, sb, remainingDays -1));
            counts[(index + 2) % 3]--;
            sb.deleteCharAt(sb.length() - 1);
        }

        Optional<Data> max = results.stream().max(Comparator.comparingDouble(d -> d.result));
        if(remainingDays != NUMBER_OF_DAYS) return max.get();

        char c = max.get().s.charAt(day);
        sb.append(c);
        double expectedResult = currentExpectedResult;
        for (int k = 0; k < 3; k++) {
            Result result = getResult(c, chars[k]);
            if (result == Result.TIE) {
                expectedResult += (counts[k] * E / (double) day);
            } else if (result == Result.WIN) {
                expectedResult += counts[k] * W / (double) day;
            }
        }
        counts[(index(c) + 2) % 3]++;

        return playRoundRecursive(day+1, expectedResult, sb, NUMBER_OF_DAYS);
    }

    private static int index(char c){
        switch (c){
            case 'R' : return 0;
            case 'S' : return 1;
            case 'P' : return 2;
            default: throw new RuntimeException();
        }
    }

    private static class Data{
        double result;
        String s;

        public Data(double result, String s) {
            this.result = result;
            this.s = s;
        }
    }

    private static Result getResult(char mine, char his) {
        if (mine == his) return Result.TIE;
        if (mine == 'R' && his == 'S' || mine == 'S' && his == 'P' || mine == 'P' && his == 'R') return Result.WIN;
        return Result.LOSE;
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