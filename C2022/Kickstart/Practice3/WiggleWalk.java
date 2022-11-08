package C2022.Kickstart.Practice3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class WiggleWalk {


    public static final String IMPOSSIBLE = "IMPOSSIBLE";
    private static String[] words;
    private static int r;
    private static int c;
    private static Set<Long> visited;
    private static int R;
    private static int C;

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int[] ints = lineToInt(in.nextLine());

            R = ints[1];
            C = ints[2];
            r = ints[3]-1;
            c = ints[4]-1;

            String instructions = in.nextLine();

            System.out.println(String.format("Case #%d: %s", i, solve(instructions)));
        }
    }

    private static String solve(String instructions){
        visited = new HashSet<>();
        visited.add(convert(r,c));
        for (char instruction : instructions.toCharArray()) {
            move(instruction);
        }
        return String.format("%d %d", r +1, c+1);

    }

    private static void move(char instruction){
        switch(instruction){
            case 'N':
                r--;
                break;
            case 'S':
                r++;
                break;
            case 'E':
                c++;
                break;
            case 'W':
                c--;
                break;
        }
        if(visited.contains(convert(r,c))) move(instruction);
        else visited.add(convert(r,c));
    }

    private static long convert(int r, int c){
        return r*C+c;
    }

    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }

    public static int[] lineToInt(String line) {
        return lineToInt(line, " ");
    }

    public static Stream<String> binaryStrings(int n) {
        return LongStream.range(0, 1L << n).mapToObj(l -> getBinaryString(l, n));
    }

    public static String getBinaryString(long value, int size) {
        return Long.toBinaryString(1L << size | value).substring(1, size + 1);
    }
}