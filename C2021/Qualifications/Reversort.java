package C2021.Qualifications;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Stream;

public class Reversort {


    private static Scanner sc;


    public static void main(String[] args) {

        sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));


        String s = sc.nextLine();
        int[] line = lineToInt(s, " ");
        int t = line[0];// Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            Reversort solution = new Reversort();
            System.out.println(String.format("Case #%d: %d", i, solution.solve()));
        }

    }


    private int solve() {
        sc.nextLine();
        int[] L = lineToInt(sc.nextLine(), " ");
        int count = 0;

        for (int i = 0; i < L.length - 1; i++) {
            int j = findMinimumIndex(L, i);
            count += j-i +1;
            reverse(L, i,j);
        }

        return count;

    }

    private void reverse(int[] l, int i, int j) {
        for (int k = 0; k <= (j-i)/2; k++) {
            swap(l, i+k, j-k);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    private int findMinimumIndex(int[] L, int i){
        int j = -1;
        int min = Integer.MAX_VALUE;
        for (; i < L.length; i++) {
            if(L[i] < min){
                min = L[i];
                j = i;
            }
        }
        return j;
    }


    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }
}
