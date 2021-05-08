import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InteractiveTemplate {


    private static boolean err = false;
    private final Scanner sc;
    private final int N;

    public static void main(String[] args) {

        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

        String s = read(in);
        int[] line = lineToInt(s, " ");
        int T = line[0];
        int N = line[1];
        int Q = line[2];

        for (int i = 1; i <= T; ++i) {
            InteractiveTemplate solution = new InteractiveTemplate(in, N);
            solution.solve();
        }

    }


    private void solve() {
        int median = Integer.parseInt(read(sc));
        print("bla");
        String read = read(sc);
        if(read.equals("-1")){
            throw new RuntimeException("wrong answer");
        }
    }


    public InteractiveTemplate(Scanner sc, int n) {
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
