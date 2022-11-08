package C2022.Qualifications;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class ChainReactionsEasy {


    private static Module abyss;
    private static long max;
    private static long[] Fs;
    private static int[] Ps;
    private static int N;

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            max = 0;
            N = Integer.parseInt(in.nextLine());
            Fs = lineToLong(in.nextLine());
            Ps = lineToInt(in.nextLine());

            long result = solve();

            System.out.println(String.format("Case #%d: %s", i, result));
        }
    }

    private static long solve(){
        Module[] modules = createTree();
        abyss= modules[0];
        List<Integer> initiators = new ArrayList<>();
        addInitiators(abyss, initiators);
        int[] indexes = initiators.stream().mapToInt(i -> i).toArray();
        testAllRecursive(0, indexes);
        return max;
    }

    private static void addInitiators(Module root, List<Integer> initiators){
        if(root.children.isEmpty()) initiators.add(root.index);
        else {
            for (Module child : root.children) {
                addInitiators(child, initiators);
            }
        }
    }

    public static void testAllRecursive(int k, int[] elements) {

            for(int i = k; i < elements.length; i++){
                swap(elements, i , k);
                testAllRecursive(k+1, elements);
                swap(elements, k , i);
            }
        if(k == elements.length -1) {
            test(elements);
        }
    }

    private static void test(int[] order){
        Module[] modules = createTree();

        long total = 0;
        for (int i : order) {
            total += initiate(modules[i], 0);
        }

        max = Math.max(total, max);
    }

    private static long initiate(Module module, long currentMax){
        if(module == abyss || module.triggered) return currentMax; //abyss
        module.triggered = true;
        return initiate(module.parent, Math.max(currentMax, module.fun));
    }

    private static Module[] createTree(){
        Module[] modules = new Module[N+1];
        abyss = new Module(0, null, 0);
        modules[0] = abyss;

        for (int i = 1; i <= N; i++) {
            Module parent = modules[Ps[i-1]];
            modules[i] = new Module(Fs[i-1], parent, i);
            parent.children.add(modules[i]);
        }

        return modules;
    }

    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }

    public static int[] lineToInt(String line) {
        return lineToInt(line, " ");
    }
    public static long[] lineToLong(String line) {
        return Stream.of(line.split(" ")).mapToLong(Long::parseLong).toArray();
    }

    public static Stream<String> binaryStrings(int n) {
        return LongStream.range(0, 1L << n).mapToObj(l -> getBinaryString(l, n));
    }

    public static String getBinaryString(long value, int size) {
        return Long.toBinaryString(1L << size | value).substring(1, size + 1);
    }

    private static  void swap(int[] input, int a, int b) {
        int tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }


    private static class Module {
        List<Module> children = new ArrayList<>();
        Module parent;
        long fun;
        int index;
        boolean triggered = false;

        public Module(long fun, Module parent, int index) {
            this.parent = parent;
            this.fun = fun;
            this.index = index;
        }
    }
}