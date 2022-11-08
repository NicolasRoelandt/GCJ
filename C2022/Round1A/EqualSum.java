package C2022.Round1A;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EqualSum {


    private static boolean err = false;
    private final Scanner sc;
    private final int N;

    public static void main(String[] args) {

        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

        String s = read(in);
        int[] line = lineToInt(s, " ");
        int T = line[0];

        for (int i = 1; i <= T; ++i) {
            read(in);
            EqualSum solution = new EqualSum(in, T);
            solution.solve();
        }

    }


    private void solve() {
        List<Integer> powers = new ArrayList<>();
        for (int i = 0; i <= 29; i++) {
            powers.add(1 << i);
        }

        List<Integer> others = new ArrayList<>();

        for (int i = 0; i < 70; i++) {
            others.add(256 + 1 + i);
        }

        ArrayList<Integer> As = new ArrayList<>();
        As.addAll(powers);
        As.addAll(others);

        print(As.stream().map(Object::toString).collect(Collectors.joining(" ")));

        int[] Bs = lineToInt(read(sc), " ");
        Arrays.stream(Bs).boxed().forEach(others::add);

        List<Integer> complete = Stream.concat(powers.stream(), others.stream()).collect(Collectors.toList());
        long sum = complete.stream().mapToLong(i -> i).sum();

            long target = sum/2;
        complete.sort(Comparator.reverseOrder());

        long subsum = 0;

        List<Integer> result = new ArrayList<>();
        for (Integer i : others) {
            System.err.println(subsum);
            if(target - subsum < i) break;
            result.add(i);
            subsum += i;
        }

        long remaining = target - subsum;

        String binaryString = new StringBuilder(Long.toBinaryString(remaining)).reverse().toString();
        char[] charArray = binaryString.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if (c == '1'){
                result.add(powers.get(i));
            }
        }

        print(result.stream().map(Object::toString).collect(Collectors.joining(" ")));
    }


    public EqualSum(Scanner sc, int n) {
        this.sc = sc;
        N = n;
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

    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }
}
