package C2021.Qualifications;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MediansHard {


    private static boolean err = false;
    private final Scanner sc;
    private final int N;


    public static void main(String[] args) {

        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

        String s = read(in);
        int[] line = lineToInt(s, " ");
        int T = line[0];
        int N = line[1];

        for (int i = 1; i <= T; ++i) {
            MediansHard solution = new MediansHard(in, N);
            solution.solve();
        }

    }


    private void solve() {

        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2));
        for (int i = 3; i <= N; i++) {
            insert(list, i, 0, list.size()-1);
        }

        print(list.stream().map(Object::toString).collect(Collectors.joining(" ")));
        String read = read(sc);
        if(read.equals("-1")){
            throw new RuntimeException("wrong answer");
        }

    }

    // insert value between left of start and right of end
    private void insert(List<Integer> list, int value, int start, int end){
        if(start > end){
            list.add(start, value);
            return;
        }

        int lower_mid = (start + end)/2;
        int upper_mid = lower_mid+1;

        if(upper_mid == list.size()){
            // this means that we have only one element, we need to take one more on the left to compare
            insert(list, value, start-1, end);
            return;
        }

        int lm_value = list.get(lower_mid);
        int um_value = list.get(upper_mid);

        int median = askMedian(lm_value, um_value, value);
        if(median == value){
            insert(list, value, upper_mid, lower_mid);
        } else if(median == lm_value){
            insert(list, value, start, lower_mid-1);
        } else{
            insert(list, value, upper_mid+1, end);
        }



    }


    public MediansHard(Scanner sc, int n) {
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

    private int askMedian(int x1, int x2, int x3){
        int[] array = new int[]{x1, x2, x3};
        print(format(array));
        return Integer.parseInt(read(sc));
    }

    private static String format(int[] array){
        return Arrays.stream(array).mapToObj(Integer::toString).collect(Collectors.joining(" "));
    }



    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }
}
