package C2022.Round1A;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class Weightlifting {


    private static int E;
    private static int W;
    private static int[][] exercises;

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
        }
    }

    private static long solve(){
        List<Map<String, Long>> nodesPerIndex = createAllNodes();

        for (int i = 0; i <= E; i++) {
            Map<String, Long> prevNodes = nodesPerIndex.get(i);
            for (String prev : prevNodes.keySet()) {
                Map<String, Long> nextNodes = nodesPerIndex.get(i + 1);
                for (String next :  nextNodes.keySet()) {
                    int operations = computeOperations(prev, next);
                    nextNodes.put(next, Math.min(nextNodes.get(next), prevNodes.get(prev) + operations));
                }
            }
        }

        return nodesPerIndex.get(E+1).get("");
    }

    private static List<Map<String, Long>> createAllNodes(){
        List<Map<String, Long>> result = new ArrayList<>();
        HashMap<String, Long> start = new HashMap<>();
        start.put("", 0L);
        result.add(start);

        for (int i = 0; i < E; i++) {
            HashMap<String, Long> map = new HashMap<>();
            result.add(map);
            for (String permutation : createAllPermutations(exercises[i])) {
                map.put(permutation, Long.MAX_VALUE);
            }
        }
        HashMap<String, Long> end = new HashMap<>();
        end.put("", Long.MAX_VALUE);
        result.add(end);
        return result;
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