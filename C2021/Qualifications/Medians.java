package C2021.Qualifications;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Medians {


    private static boolean err = false;
    private final Scanner sc;
    private final int N;
    private final int Q;
    private int[] result;


    public static void main(String[] args) {

        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

        String s = read(in);
        int[] line = lineToInt(s, " ");
        int T = line[0];
        int N = line[1];
        int Q = line[2];

        for (int i = 1; i <= T; ++i) {
            Medians solution = new Medians(in, N, Q);
            solution.solve();
        }

    }


    private void solve() {
        List<Exchange> exchanges = new ArrayList<>();
        List<int[]> combinations = generate(N, 3);
        for (int[] combination : combinations) {
            for (int i = 0; i < combination.length; i++) {
                combination[i]++;
            }
            print(format(combination));
            int median = Integer.parseInt(read(sc));
            exchanges.add(new Exchange(combination, median));
        }

        result = new int[N];

        Set<Integer> allNumbers = new HashSet<>();
        for (int i = 1; i <= N; i++) {
            allNumbers.add(i);
        }

        HashSet<Integer> remaining = new HashSet<>(allNumbers);

        removeMedians(remaining, exchanges);
        if(remaining.size() != 2){
            throw new RuntimeException();
        }

        Iterator<Integer> iterator = remaining.iterator();
        int minimum = iterator.next();
        int maximum = iterator.next();

        result[0] = minimum;
        result[N-1] = maximum;
        exchanges = filteredExchanges(exchanges, minimum);

        remaining = new HashSet<>(allNumbers);
        remaining.remove(minimum);
        remaining.remove(maximum);

        for (int index = 1; index < N-2; index++) {
            minimum = findNonMedian(remaining, exchanges);
            remaining.remove(minimum);
            exchanges = filteredExchanges(exchanges, minimum);
            result[index] = minimum;
        }

        if(remaining.size() != 1){
            throw new RuntimeException();
        }

        result[N-2] = remaining.iterator().next();

        print(format(result));
        String read = read(sc);
        if(read.equals("-1")){
            throw new RuntimeException("wrong answer");
        }
    }

    private int findNonMedian(Set<Integer> numbers, List<Exchange> exchanges){
        HashSet<Integer> remaining = new HashSet<>(numbers);
        removeMedians(remaining, exchanges);

        if(remaining.size() != 1){
            throw new RuntimeException();
        }
        return remaining.iterator().next();
    }

    private List<Exchange> filteredExchanges(List<Exchange> exchanges, int value){
        return exchanges.stream().filter(exchange -> !exchange.inputs.contains(value)).collect(Collectors.toList());
    }


    private void removeMedians(Set<Integer> remaining, Collection<Exchange> exchanges){
        for (Exchange exchange : exchanges) {
            remaining.remove(exchange.median);
        }
    }

    public Medians(Scanner sc, int n, int q) {
        this.sc = sc;
        N = n;
        Q = q;
    }

    private void print(String s) {
        System.out.println(s);
        if (err) {
            System.err.println("out: " + s);
        }
        System.out.flush();
    }

    private static String read(Scanner sc) {
        String s = sc.nextLine();
        if (err) {
            System.err.println("in: " + s);
        }
        return s;
    }

    private static class Exchange{
        private Set<Integer> inputs;
        private int median;

        public Exchange(int[] inputs, int median) {
            Set<Integer> set = new HashSet<>();
            for (int input : inputs) {
                set.add(input);
            }
            this.inputs = set;
            this.median = median;
        }
    }

    private static String format(int[] array){
        return Arrays.stream(array).mapToObj(Integer::toString).collect(Collectors.joining(" "));
    }

    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }

    public static List<int[]> generate(int n, int r) {
        List<int[]> combinations = new ArrayList<>();
        int[] combination = new int[r];

        // initialize with lowest lexicographic combination
        for (int i = 0; i < r; i++) {
            combination[i] = i;
        }

        while (combination[r - 1] < n) {
            combinations.add(combination.clone());

            // generate next combination in lexicographic order
            int t = r - 1;
            while (t != 0 && combination[t] == n - r + t) {
                t--;
            }
            combination[t]++;
            for (int i = t + 1; i < r; i++) {
                combination[i] = combination[i - 1] + 1;
            }
        }

        return combinations;
    }
}
