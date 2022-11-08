package C2022.Kickstart.RoundG;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class CuteLittleButterflyHard {


    private static Flower[] flowers;
    private static int E;

    public static void main(String[] args) throws FileNotFoundException {
        URL url = CuteLittleButterflyHard.class.getResource("ts3_input.txt");
        File myObj = new File(url.getPath());
        Scanner in = new Scanner(myObj);
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int[] line = lineToInt(in.nextLine());
            int N = line[0];
            E = line[1];
            flowers = new Flower[N];
            for (int j = 0; j < N; j++) {
                int[] line2 = lineToInt(in.nextLine());
                flowers[j] = new Flower(line2[0], line2[1], line2[2]);
            }

            System.out.println(String.format("Case #%d: %d", i, solve()));

        }
    }

    private static long solve(){
        Map<Integer, TreeMap<Integer, Flower>> energies = new HashMap<>();
        for (Flower flower : flowers) {
            energies.computeIfAbsent(flower.Y, _k -> new TreeMap<>()).put(flower.X, flower);
        }

        Integer[] ys = energies.keySet().toArray(new Integer[0]);

        Arrays.sort(ys, Comparator.reverseOrder());

        TreeMap<Integer, Flower> above = new TreeMap<>();
        above.put(-1, new Flower(-1, -E, (long)0));
        above.put(Integer.MAX_VALUE, new Flower(Integer.MAX_VALUE, -E, (long)0));
        for (Integer y : ys) {
            TreeMap<Integer, Flower> tree = energies.get(y);
//                for (Flower flower : above.values()) {
//                    if(!tree.containsKey(flower.X)) tree.put(flower.X, new Flower(flower.X, flower.maxL, flower.maxR));
//                }

            // going right
            for (Flower flower : tree.values()) {
                Map.Entry<Integer, Flower> upEntry = above.floorEntry(flower.X);
                long up = upEntry == null ? 0 :upEntry.getValue().maxR;
                Map.Entry<Integer, Flower> leftFlower = tree.lowerEntry(flower.X);
                long left = leftFlower == null ? above.firstEntry().getValue().maxL -E : leftFlower.getValue().maxR;
                flower.maxR = Math.max(up, left) + flower.C;
            }

           // going left
            for (Flower flower : tree.descendingMap().values()) {
                Map.Entry<Integer, Flower> upEntry = above.ceilingEntry(flower.X);
                long up = upEntry == null ? 0 : upEntry.getValue().maxL;
                Map.Entry<Integer, Flower> rightFlower = tree.higherEntry(flower.X);
                long right = rightFlower == null ? above.lastEntry().getValue().maxR - E : rightFlower.getValue().maxL;
                flower.maxL = Math.max(up, right) + flower.C;
            }

            above = tree;
        }

        return Math.max(above.firstEntry().getValue().maxL, above.lastEntry().getValue().maxR);
    }

    private static class Flower{
        int X, Y, C;
        long maxL;
        long maxR;

        public Flower(int x, int y, int c) {
            X = x;
            Y = y;
            C = c;
        }

        public Flower(int x, long maxL, long maxR) {
            X = x;
            this.maxL = maxL;
            this.maxR = maxR;
        }


        public int getX() {
            return X;
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