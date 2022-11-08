package C2021.Kickstart.RoundG;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class DogsAndCats {



    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int[] ints = lineToInt(in.nextLine());
            int N = ints[0];
            int D = ints[1];
            int C = ints[2];
            int M = ints[3];

            String animals = in.nextLine();

            boolean canFeed = canFeed(D, C, M, animals);

            System.out.println(String.format("Case #%d: %s", i, canFeed ? "YES" : "NO"));
        }
    }

    private static boolean canFeed(long D, int C, int M, String animals) {
        StringBuilder sb = new StringBuilder(animals);

        while(sb.length() > 0){
            int end = sb.length() - 1;
            char c = sb.charAt(end);
            if(c == 'C') sb.deleteCharAt(end);
            else break;
        }

        for(char c : sb.toString().toCharArray()){
            if(c == 'C') {
                if(C <= 0) return false;
                C--;
            } else {
                if(D <= 0) return false;
                D--;
                C += M;
            }
        }
        return true;
    }

    private static int solve(){
        return 0;
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