package C2022.Kickstart.RoundG;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.stream.LongStream;
import java.util.stream.Stream;


    public class Walktober {



        public static void main(String[] args) {
            Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
            int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
            for (int i = 1; i <= t; ++i) {
                int[] line = lineToInt(in.nextLine());
                int M = line[0];
                int N = line[1];
                int P = line[2];

                int[][] steps = new int[M][];

                for (int j = 0; j < M; j++) {
                    steps[j] = lineToInt(in.nextLine());
                }

                long count = 0;
    
                for (int j = 0; j < N; j++) {
                    int max = 0;
                    int John = 0;
                    for (int k = 0; k < M; k++) {
                        int st = steps[k][j];
                        if(k == P -1) John = st;
                        max = Math.max(max, st);
                    }
                    if(max > John) count += max- John;
                }

                System.out.println(String.format("Case #%d: %d", i, count));
            }
        }

        private static int solve(){
            return 0;
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