package C2021.Kickstart.RoundG;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class BananaAttempt {



    public static void main(String[] args) {
        Random rand = new Random();
//        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
//        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
//        for (int i = 1; i <= t; ++i) {
//            int[] ints = lineToInt(in.nextLine());
//            int K = ints[1];
//            int[] farm = lineToInt(in.nextLine());
//
//
//            int solve = solve(K, farm);
//
//            System.out.println(String.format("Case #%d: %s", i, solve));
//        }

        int[] values = {8, 8, 5, 2, 0, 9, 5, 8, 2, 4};
        System.out.println(solve(47, values));
//        System.out.println(solveBF(47, values));


//        System.out.println(b);

//        int[] values = new int[10];
//
//        for (int i = 0; i < 100; i++) {
//            for (int j = 0; j < values.length; j++) {
//                values[j] = rand.nextInt(10);
//            }
//
//            int k = rand.nextInt(1000);
//
//            if(solve(k, values) != solveBF(k, values)){
//                System.out.println(Arrays.toString(values));
//                System.out.println(k);
//                break;
//            }
//        }
    }

    private static int solve(int K, int[] farm){
        int n = farm.length;

       Map<Integer, Integer> best = new HashMap<>();

       int min = Integer.MAX_VALUE;
        for (int i = n - 1; i >= 0; i--) {
            int currSum = 0;
            for (int j = i; j >= 0; j--) {
                currSum += farm[j];

                if(currSum <= K && best.containsKey(K-currSum)){
                    min = Math.min(min, j-i+1 + best.get(K-currSum));
                }
            }

            int currPostSum = 0;

            for (int x = i; x < n; x++) {
                currPostSum += farm[x];
                if(currPostSum <= K)
                    best.put(currPostSum, Math.min(best.getOrDefault(currPostSum, Integer.MAX_VALUE), x-i+1));
            }
        }



        return min == Integer.MAX_VALUE ? -1 : min;

    }

    private static int solveBF(int K, int[] farm){
        int n = farm.length;
        int[] runningSum = new int[n+1];
        for (int i = 0; i < n; i++) {
            runningSum[i+1] = runningSum[i] + farm[i];
        }

        int min = Integer.MAX_VALUE;
        for (int start1 = 0; start1 < n; start1++) {
            for (int end1 = start1; end1 < n; end1++) {
                int row1 = sum(runningSum, start1, end1);
                if(row1 > K) continue;
                int size1 = end1-start1 +1;
                if(row1 == K) {
                    min = Math.min(min, size1);
                    continue;
                }

                int target = K - row1;

                for (int start2 = end1 + 2; start2 < n; start2++) {
                    int bs = Arrays.binarySearch(runningSum, start2, n+1, target + runningSum[start2]);
                    if(bs >=0){
                        min = Math.min(min, size1 + bs - start2);
                    }
                }
            }

        }

        return min == Integer.MAX_VALUE ? -1 : min;

    }

//    private static int findMin(TreeSet<Data> tree, int start){
//
//    }

    private static class Data  {
        int size;
        int start;

        public Data(int size, int start) {
            this.size = size;
            this.start = start;
        }

        public int getSize() {
            return size;
        }

        public int getStart() {
            return start;
        }
    }

    private static int sum(int[] runningSum, int start, int end){
       return runningSum[end+1] - runningSum[start];
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