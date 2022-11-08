package C2022.Kickstart.RoundG;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.LongStream;
import java.util.stream.Stream;


    public class Curling {


        private static int rs;
        private static int rh;
        private static int n;
        private static List<Stone> stones;

        public static void main(String[] args) {
            Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
            int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
            for (int i = 1; i <= t; ++i) {
                int[] line = lineToInt(in.nextLine());
                rs = line[0];
                rh = line[1];

                n = Integer.parseInt(in.nextLine());

                stones = new ArrayList<>(n);
                for (int j = 0; j < n; j++) {
                    int[] line2 = lineToInt(in.nextLine());
                   stones.add(new Stone(line2[0], line2[1], true));
                }

                int M = Integer.parseInt(in.nextLine());
                for (int j = 0; j < M; j++) {
                    int[] line2 = lineToInt(in.nextLine());
                    stones.add(new Stone(line2[0], line2[1], false));
                }

                if(stones.size() == 0) {
                    System.out.println(String.format("Case #%d: 0 0", i));
                    continue;
                }

                stones.sort(Comparator.comparingDouble(Stone::getDistance));
                int j = 0;
                boolean weWin = stones.get(0).ours;
                if(weWin){
                    for (;j < stones.size() ; j++) {
                        Stone stone = stones.get(j);
                        if(!stone.ours || !stone.isInside) break;
                    }


                    System.out.println(String.format("Case #%d: %d %d", i, j, 0));
                } else {
                    for (;j < stones.size() ; j++) {
                        Stone stone = stones.get(j);
                        if(stone.ours || !stone.isInside) break;
                    }
                    System.out.println(String.format("Case #%d: %d %d", i, 0, j));
                }

            }
        }

        private static class Stone{
            int X;
            int Y;
            boolean ours;
            double distance;
            boolean isInside;

            public Stone(int x, int y, boolean ours) {
                X = x;
                Y = y;
                this.ours = ours;
                distance = Math.sqrt(x*x + y*y);
                isInside = distance <= rs+ rh;
            }

            public double getDistance() {
                return distance;
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