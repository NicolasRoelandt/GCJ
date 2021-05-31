package C2021.Kickstart.RoundC;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class RockPaperScissors {
    private static int W;
    private static int E;
    private static final int R = 60;
    private static int[] counts;
    private static char[] chars;

    private static List<Map<Key, Data>> maps;

    // This passes all test sets
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
        chars = new char[]{'R', 'S', 'P'};
        counts = new int[3];

        counts[2] = 1;
        double expectedResult = (W + E) / 3d;

        maps = new ArrayList<>(60);
        maps.add(null);
        for (int i = 1; i < R; i++) {
            maps.add(i, new HashMap<>());
        }
        Data result = playRoundRecursive(1);
        System.out.println(result.result + expectedResult);

        return 'R' + result.s;
    }

    private static Data playRoundRecursive(int day) {
        if (day == R) {
            return new Data(0, "");
        }
        Key key = new Key(counts);
        Data data = maps.get(day).get(key);
        if (data != null) {
            return data;
        } else {
            List<Data> results = new ArrayList<>();
            for (int index = 0; index < 3; index++) {
                char value = chars[index];
                double thisResult = 0;
                for (int k = 0; k < 3; k++) {
                    Result result = getResult(value, chars[k]);
                    if (result == Result.TIE) {
                        thisResult += (counts[k] * E / (double) day);
                    } else if (result == Result.WIN) {
                        thisResult += counts[k] * W / (double) day;
                    }
                }
                counts[(index + 2) % 3]++;
                Data resultNextDays = playRoundRecursive(day + 1);
                Data result = new Data(resultNextDays.result + thisResult, value + resultNextDays.s);
                results.add(result);
                counts[(index + 2) % 3]--;
            }

            Optional<Data> max = results.stream().max(Comparator.comparingDouble(d -> d.result));
            maps.get(day).put(key, max.get());
            return max.get();
        }
    }

    private static class Key {
        int R, S, P;

        public Key(int[] count) {
            R = count[0];
            S = count[1];
            P = count[2];
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Key key = (Key) o;
            return R == key.R &&
                    S == key.S &&
                    P == key.P;
        }

        @Override
        public int hashCode() {
            return Objects.hash(R, S, P);
        }
    }

    private static class Data {
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