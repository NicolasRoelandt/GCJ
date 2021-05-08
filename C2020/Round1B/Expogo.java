package C2020.Round1B;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.abs;


public class Expogo {

    enum Dir {
        N, E, S, W;
    }




    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int[] line = lineToInt(in.nextLine());
            int X = line[0];
            int Y = line[1];

            String result = solve(X, Y);


            System.out.println(String.format("Case #%d: %s", i, result));
        }

    }

    private static String solve(int X, int Y) {
        int[] x = toArray(X);
        int[] y = toArray(Y);
        int[] longest = x.length > y.length ? x:y;

        x = Arrays.copyOf(x, longest.length);
        y = Arrays.copyOf(y, longest.length);

        //System.out.println(Arrays.toString(y));
        if(X == 0 && Y == 0) return "";
        if(mismatch(x, y, 0)) return "IMPOSSIBLE";


        int i;
        for (i = 1; i < x.length-1 && i < y.length-1; i++) {
            boolean prevXisOne = abs(x[i-1]) == 1;

            if(mismatch(x,y,i)){
                if(prevXisOne){
                    switchBit(x,i);
                } else {
                    switchBit(y,i);
                }
            }
        }

//        int[] longest = x.length > y.length ? x:y;
//
//        for (; i< longest.length-1; i++){
//            if(longest[i] == 0) switchBit(longest, i);
//        }

        StringBuilder sb = new StringBuilder();

        x= trim(x);
        y = trim(y);

        for (int j = 0; j < Math.max(x.length, y.length); j++) {
            if(j < x.length && abs(x[j]) == 1) {
                int v = X < 0 ? -x[j] : x[j];
                sb.append(v == 1 ? 'E' : 'W');
            } else {
                int v = Y < 0 ? -y[j] : y[j];
                sb.append(v == 1 ? 'N' : 'S');
            }
        }

        return sb.toString();
    }

    private static int[] trim(int[] arr){
        int i = positionOfLastOne(arr);
        return Arrays.copyOf(arr, Math.max(0,i+1));
    }

    private static int positionOfLastOne(int[] arr) {
        int i = arr.length -1;
        while(i > 0 && arr[i] == 0) i--;
        return i;
    }

    private static void switchBit(int[] x, int i) {
        x[i-1] = -1;
        int j = i;
        while(x[j] == 1){
            x[j]=0;
            j++;
        }
        x[j] = 1;
    }

    private static boolean mismatch(int[] x, int[] y, int i) {
        return (x[i] ^ y[i]) == 0;
    }

    private static int[] toArray(int i){
        int abs = abs(i);
        String s = new StringBuilder(Integer.toBinaryString(abs)).reverse().toString();

        int[] result = s.chars().map(c -> c == '1' ? 1 : 0).toArray();
        return Arrays.copyOf(result, result.length+1);
    }

    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }

    public static int[] lineToInt(String line) {
        return lineToInt(line, " ");
    }
}