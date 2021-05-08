package C2021.Qualifications;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.stream.Stream;

public class SolutionTemp {


    private static Scanner sc;

    private int X;
    private int Y;
    private char[] s;

    public SolutionTemp(int x, int y, char[] s) {
        X = x;
        Y = y;
        this.s = s;
    }

    public static void main(String[] args) {

        sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));


        int[] line = lineToInt(sc.nextLine(), " ");
        int t = line[0];// Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            String[] tokens = sc.nextLine().split(" ");
            int X = Integer.parseInt(tokens[0]);
            int Y = Integer.parseInt(tokens[1]);

            char[] s = tokens[2].toCharArray();

            SolutionTemp solution = new SolutionTemp(X, Y, s);
            System.out.println(String.format("Case #%d: %s", i, solution.solve()));
        }

    }


    private String solve() {


        char previous = 0;
        for (int i = 0; i < s.length; i++) {
            if(s[i] == '?'){
                int start = i;
                while(i < s.length && s[i] == '?'){
                    i++;
                }
                if(i == s.length){
                    handleCase(start, i-1, previous, (char)0);
                    break;
                } else {
                    handleCase(start, i-1, previous, s[i]);
                }
            }
            previous = s[i];
        }

        return "" + computeCost();

    }

    private int computeCost() {
        int cost = 0;
        for (int i = 1; i < s.length; i++) {
            char first = s[i-1];
            char second = s[i];
            if(first != second){
                if(first == 'C'){
                    cost += X;
                } else {
                    cost += Y;
                }
            }
        }
        return cost;
    }

    private void handleCase(int start, int end, char previous, char next){
        boolean negativeCost = X <0 || Y <0;
        if(previous == 0){  // ..?C ou ..?J
            if(!negativeCost) {
                fill(next, start, end);
            } else {
                if(next == 'J'){
//
                }
            }
            return;
        }


        fill(previous, start, end);
    }

    private void fill(char value, int i, int j){
        for (int k = i; k <= j; k++) {
            s[k] = value;
        }
    }


    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }
}
