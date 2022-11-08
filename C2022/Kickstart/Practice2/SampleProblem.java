    package C2022.Kickstart.Practice2;

    import java.io.BufferedReader;
    import java.io.InputStreamReader;
    import java.util.*;
    import java.util.stream.LongStream;
    import java.util.stream.Stream;


    public class SampleProblem {

        public static void main(String[] args) {
            Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
            int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
            for (int i = 1; i <= t; ++i) {
                int[] line = lineToInt(in.nextLine());
                int M = line[1];
                int sum = Arrays.stream(lineToInt(in.nextLine())).sum();
                System.out.println(String.format("Case #%d: %d", i, sum % M));
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
