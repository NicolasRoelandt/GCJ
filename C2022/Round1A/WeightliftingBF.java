package C2022.Round1A;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class WeightliftingBF {


    private static int E;
    private static int W;
    private static int[][] exercises;
    private static Map<Memo, Long> map;

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int[] line = lineToInt(in.nextLine());
            E = line[0];
            W = line[1];

            exercises = new int[E][];

            for (int j = 0; j < E; j++) {
                exercises[j] = lineToInt(in.nextLine());
            }

            long result = solve();

            System.out.println(String.format("Case #%d: %d", i, result));
//
//            Set<String> allPermutations = createAllPermutations(new int[]{2, 2, 2});
//            allPermutations.forEach(System.out::println);
//            System.out.println(allPermutations.size());

        }
    }

    private static class Memo{
        int exercise;
        String order;

        public Memo(int exercise, String order) {
            this.exercise = exercise;
            this.order = order;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Memo memo = (Memo) o;
            return exercise == memo.exercise &&
                    Objects.equals(order, memo.order);
        }

        @Override
        public int hashCode() {
            return Objects.hash(exercise, order);
        }
    }

    private static long solve(){
        map = new HashMap<>();
        return solve(0, "");
    }

    private static long solve(int exercise, String previous){
        Memo key = new Memo(exercise, previous);
        if(map.containsKey(key)) return map.get(key);
        if(exercise >= E){
           return computeOperations(previous, "");
        }
        Set<String> allPermutations = createAllPermutations(exercises[exercise]);

        long minimum = Long.MAX_VALUE;
        for (String permutation : allPermutations) {
            long solve = solve(exercise + 1, permutation);
            minimum = Math.min(solve + computeOperations(previous, permutation), minimum);
        }
        map.put(key, minimum);
        return minimum;
    }

    private static int computeOperations(String previous, String next){
        int i = getFirstIndex(previous, next);
        return previous.length()-i + next.length()-i;
    }

    private static int getFirstIndex(String s, String p){
        int min = Math.min(s.length(), p.length());
        for (int i = 0; i < min; i++) {
            if(s.charAt(i) != p.charAt(i)) return i;
        }

        return min;
    }

//    private static String reverse(String s){
//        return new StringBuilder(s).reverse().toString();
//    }

    private static Set<String> createAllPermutations(int[] exercise){
        int count = Arrays.stream(exercise).sum();
        int[] array = new int[count];
        int i = 0;
        for (int weight = 0; weight < W; weight++) {
            int weightCount = exercise[weight];
            for (int j = i; j < i + weightCount; j++) {
                array[j] = weight;
            }
            i =  i + weightCount;
        }

        Set<String> result = new HashSet<>();
        getPemutationsRecursive(count, array, result);
        return result;
    }

//    private static Set<String> fillPermutations(int[] exercise){
//
//    }

    public static void getPemutationsRecursive(int n, int[] elements, Set<String> result) {

        if(n == 1) {
            result.add(Arrays.stream(elements).mapToObj(i -> "" + i).collect(Collectors.joining()));
        } else {
            for(int i = 0; i < n-1; i++) {
                getPemutationsRecursive(n - 1, elements, result);
                if(n % 2 == 0) {
                    swap(elements, i, n-1);
                } else {
                    swap(elements, 0, n-1);
                }
            }
            getPemutationsRecursive(n - 1, elements, result);
        }
    }

    private static void swap(int[] input, int a, int b) {
        int tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
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