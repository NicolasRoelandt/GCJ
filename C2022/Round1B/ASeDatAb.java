package C2022.Round1B;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Stream;


public class ASeDatAb {


    private static boolean err = true;
    private final Scanner sc;

    static Map<Integer, List<Integer>> listInts = new HashMap<>();

    public static void main(String[] args) {
        for (int i = 0; i <= 8; i++) {
            listInts.put(i, new ArrayList<>());
        }

        for (int i = 0; i < 256; i++) {
            listInts.get(Integer.bitCount(i)).add(i);
        }


        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

        String s = read(in);
        int[] line = lineToInt(s, " ");
        int T = line[0];

        for (int i = 1; i <= T; ++i) {
            ASeDatAb solution = new ASeDatAb(in);
            solution.solve();
        }

    }


    private void solve() {
        int i = 1;
        print("00000000");
        int prev = Integer.parseInt(read(sc));
        for (; i < 300; i++) {
            if(prev == 0) return;
            String mostLikely = getMostLikely(prev);
            print(mostLikely);
            prev = Integer.parseInt(read(sc));
        }
        if(prev == 0) return;

        throw new RuntimeException();

    }

    public String getMostLikely(int bits){
        double maxProba = 0;
        int best = 1;
        for (int i = 0; i < 255; i++) {
            double proba = computeProba(i, bits);
            if(proba > maxProba){
                maxProba = proba;
                best = i;
            }
        }
        return String.format("%8s", Integer.toBinaryString(best)).replace(' ', '0');
    }

    private double computeProba(int i, int bits) {
        int difference = 0;
        for (int poss : listInts.get(bits)) {
            for (int shift = 0; shift < 8; shift++) {
                difference += bits - Integer.bitCount(Integer.rotateRight(i, shift) ^ poss);
            }
        }

        return ((double) difference)/(listInts.get(bits).size()*8);
    }


    public ASeDatAb(Scanner sc) {
        this.sc = sc;
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
