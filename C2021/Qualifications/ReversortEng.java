package C2021.Qualifications;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReversortEng {


    private static Scanner sc;


    public static void main(String[] args) {

        sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));


        String s = sc.nextLine();
        int[] line = lineToInt(s, " ");
        int t = line[0];// Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {

            ReversortEng solution = new ReversortEng();
            System.out.println(String.format("Case #%d: %s", i, solution.solve()));
        }

    }


    private String solve() {
        int[] line = lineToInt(sc.nextLine(), " ");
        int N = line[0];
        int C = line[1];

        if (C < N - 1 || C > N * (N + 1)/2 - 1) {
            return "IMPOSSIBLE";
        }

        int[] L = new int[N];
        for (int i = 1; i <= N; i++) {
            L[i - 1] = i;
        }

        int[] partition = createPartition(C, N);

        for (int i = 0; i < N - 1; i++) {
            reverse(L, i, i+partition[i]-1);
        }

        int[] result = new int[N];
        for (int i = 0; i < N ; i++) {
            result[L[i]-1] = i+1;
        }

        return Arrays.stream(result).mapToObj(Integer::toString).collect(Collectors.joining(" "));

    }

    private int[] createPartition(int C, int N) {
        int[] p = new int[N - 1];
        for (int i = 0; i < N - 1; i++) {
            p[i] = 1;
            C--;
        }

        for (int i = 0; i < N -1 ; i++) {
            int maxAdd = N-i -1;
            if(C > maxAdd){
                p[i] += maxAdd;
                C -= maxAdd;
            } else {
                p[i] += C;
                return p;
            }
        }

        return p;
    }

    private void reverse(int[] l, int i, int j) {
        for (int k = 0; k <= (j - i) / 2; k++) {
            swap(l, i + k, j - k);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }
}
