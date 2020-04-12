package C2019.Round1A;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Stream;


public class PylonsEasy {
    private final int R;
    private final int C;
    private Set<Pair> remaining;
    private ArrayList<Pair> list;

    public PylonsEasy(int r, int c) {
        this.R = r;
        this.C = c;

    }

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int[] ints = lineToInt(in.nextLine(), " ");

            PylonsEasy solution = new PylonsEasy(ints[0], ints[1]);


            System.out.println("Case #" + i + ": " + solution.getSolution());
        }

    }

    private String getSolution() {
        if(solve()){
            StringBuilder sb = new StringBuilder();
            sb.append("POSSIBLE");
            for (Pair pair : list) {
                sb.append('\n');
                sb.append(pair);
            }
            return sb.toString();
        } else {
            return "IMPOSSIBLE";
        }
    }


    public boolean solve() {
        remaining = new HashSet<>();
        for (int r = 1; r <= R; r++) {
            for (int c = 1; c <= C; c++) {
                remaining.add(new Pair(r,c));
            }
        }
        list = new ArrayList<>();

        return solve(Pair.DUMMY);
    }

    public boolean solve(Pair previous) {
       if(remaining.isEmpty()){
           return true;
       }
        HashSet<Pair> copy = new HashSet<>(remaining);
        for (Pair pair : copy) {
            if(previous.isCompatible(pair)){
                list.add(pair);
                remaining.remove(pair);
                if(solve(pair)){
                    return true;
                }
                remaining.add(pair);
                list.remove(list.size()-1);
            }
        }

        return false;
    }

    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }

    private static class Pair{
        int r, c;

        public static Pair DUMMY = new Pair(-1, -1);

        public Pair(int r, int c) {
            this.r = r;
            this.c = c;
        }

        private boolean isCompatible(Pair other){
            if(this == DUMMY) return true;
            int r2 = other.r;
            int c2 = other.c;

            if(r == r2) return false;
            if(c == c2) return false;
            if(r-c == r2-c2) return false;
            if(r+c == r2+c2) return false;

            return true;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return r == pair.r &&
                    c == pair.c;
        }

        @Override
        public String toString() {
            return r + " " + c;
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, c);
        }
    }
}