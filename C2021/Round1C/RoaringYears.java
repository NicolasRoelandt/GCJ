package C2021.Round1C;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class RoaringYears {



    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; i++) {
            int N = Integer.parseInt(in.nextLine());

            int j;
            for(j = N+1; !isRoaring(j); j++){}

            System.out.println(String.format("Case #%d: %d", i, j));
        }
    }
    public static boolean isRoaring(int i){
        return isRoaring(Integer.toString(i), new ArrayList<>());
    }

    public static boolean isRoaring(String s, List<Integer> before){
        if(s.length() == 0){
            return consecutive(before);
        }

        for (int i = 1; i < s.length()+1; i++) {
            if(i < s.length() && s.charAt(i) == '0') continue;
            String prefix = s.substring(0, i);
            before.add(Integer.parseInt(prefix));

            String suffix = s.substring(i);
            if(isRoaring(suffix, before)) return true;
            before.remove(before.size()-1);
        }

        return false;
    }

    public static boolean consecutive(List<Integer> list){
       if(list.size() <2 ) return false;

        Integer previous = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            Integer value = list.get(i);
            if(value != previous +1) return false;
            previous= value;
        }
        return true;
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