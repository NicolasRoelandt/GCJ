package C2021.Round1B;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

public class DigitBlocks {


    private static boolean err = false;
    private final Scanner sc;
    private final int N;
    private int B;
    private int[] towers;

    public static void main(String[] args) {

        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

        String s = read(in);
        long[] line = lineToLong(s);
        int T = (int) line[0];
        int N = (int) line[1];
        int B = (int) line[2];
        for (int i = 1; i <= T; ++i) {
            DigitBlocks solution = new DigitBlocks(in, N, B);
            solution.solve();
        }

        int result = Integer.parseInt(read(in));
        if(result == -1){
            throw new RuntimeException();
        }

    }


    private void solve() {
        for (int exchanges = 0; exchanges < N*B; exchanges++) {
            int i = Integer.parseInt(read(sc));
            if(i == -1){
                System.err.println(Arrays.toString(towers));
                throw new RuntimeException();
            }
            if (i == 9) {
               placeOnTower(firstTower());
            } else {
                placeOnTower(firstUnwaitingTower());
            }
        }

    }

    private int firstTower(){
        for (int i = 0; i < N; i++) {
            if(towers[i] != B) return i;
        }
        throw new RuntimeException();
    }

    private int firstUnwaitingTower(){
        for (int i = 0; i < N; i++) {
            if(towers[i] < B-1) return i;
        }
        // all full
        return firstTower();
    }

    private void placeOnTower(int i){
        print(""+(i+1));
        towers[i]++;
    }


    public DigitBlocks(Scanner sc, int n, int B) {
        this.sc = sc;
        N = n;
        this.B = B;
        towers = new int[N];
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

    public static long[] lineToLong(String line) {
        return Stream.of(line.split(" ")).mapToLong(Long::parseLong).toArray();
    }

}
