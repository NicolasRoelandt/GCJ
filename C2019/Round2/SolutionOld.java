package C2019.Round2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SolutionOld {


    private static boolean err = false;
    private final Scanner sc;


    public static void main(String[] args) {

        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));


        String s = read(in);
        int[] line = lineToInt(s, " ");
        int t = line[0];// Scanner has functions to read ints, longs, strings, chars, etc.
//        int t = 10;
        for (int i = 1; i <= t; ++i) {
            SolutionOld solution = new SolutionOld(in);
            solution.solve();
        }

    }


    private void solve() {
        int remaining = 6;
        int trash = 20 - remaining;
        for (int i = 1; i <= 72; i++) {
            String read = read(sc);
            if (Integer.parseInt(read) != i) throw new RuntimeException(read + " instead of " + i);
            print((i % trash + remaining + 1) + " " + i);
        }


        List<Vase> list = new ArrayList<>();
        for (int i = 73; i <= 78; i++) {
            String read = read(sc);
            if (Integer.parseInt(read) != i) throw new RuntimeException(read + " instead of " + i);
            int vase = i % remaining + 1;
            print(vase + " " + 0);
            int length = read(sc).split(" ").length;
            list.add(new Vase(vase, length));
        }

        list.sort(Comparator.comparing(Vase::getTokens));

        Vase min = list.get(0);


        List<Vase> dangerous = Collections.emptyList();
        for (int diff = 4; diff < 20 && dangerous.size()==0; diff++) {
            dangerous = getDangerous(list, min, diff);

        }

        int size = dangerous.size();

        for (int i = 79; i <= 99; i++) {
            String read = read(sc);
            if (Integer.parseInt(read) != i) throw new RuntimeException(read + " instead of " + i);
            Vase vase = dangerous.get(i % size);
            print(vase.index + " " + i);
        }



        String read = read(sc);
        //System.err.println(min.tokens);
        if (Integer.parseInt(read) != 100) throw new RuntimeException(read + " instead of " + 100);
        print((min.index) + " " + 100);


        String collect = dangerous.stream().map(Vase::getTokens).map(i -> Integer.toString(i)).collect(Collectors.joining(" "));
        //System.err.println(collect);
    }

    private List<Vase> getDangerous(List<Vase> list, Vase min, int diff) {
        List<Vase> dangerous = new ArrayList<>();
        for (int i = 1; i < list.size(); i++) {
            Vase vase = list.get(i);
            if(vase.tokens - min.tokens <= diff) dangerous.add(vase);
        }

        return dangerous;
    }

    public static class Vase{
        int index, tokens;

        public int getTokens() {
            return tokens;
        }

        public Vase(int index, int tokens) {
            this.index = index;
            this.tokens = tokens;
        }

        @Override
        public String toString() {
            return "Vase{" +
                    "index=" + index +
                    ", tokens=" + tokens +
                    '}';
        }
    }

    public SolutionOld(Scanner sc) {
//        Overrandomized rand = new Overrandomized();
//        int r = rand.nextInt(120);
//        myComb = new ArrayList<>(allCombin);
//        System.out.println(myComb.get(r));
//        myComb.remove(r);
        this.sc = sc;
    }


    private void print(String s) {
        System.out.println(s);
        if (err) {
            System.err.println("out: " + s);
        }
        System.out.flush();
//        asked = Integer.parseInt(s);
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
