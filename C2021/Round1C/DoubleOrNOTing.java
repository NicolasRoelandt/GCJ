package C2021.Round1C;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class DoubleOrNOTing {


    static Pattern PATTERN = Pattern.compile("$0+");



    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            String[] line = in.nextLine().split(" ");

            String result = solve(line[0], line[1]);

            System.out.println(String.format("Case #%d: %s", i, result));
        }
    }

    public static String solve(String S, String E){
        Queue<String> Q = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        HashMap<String, Integer> distances = new HashMap<>();
        HashMap<String, String> previous = new HashMap<>();
        distances.put(S, 0);

        Q.add(S);
        visited.add(S);
        while (!Q.isEmpty()){
            String current = Q.poll();
            if(current.equals(E)) {
                return Integer.toString(distances.get(E));
            }
            for (String n : getNeigh(current)) {
                if(!visited.contains(n)){
                    distances.put(n,distances.get(current) + 1);
                    previous.put(n, current);
                    visited.add(n);
                    Q.add(n);
                }
            }
        }

        return "IMPOSSIBLE";
    }

    public static List<String> getNeigh(String s){
        ArrayList<String> list = new ArrayList<>();
//        System.out.println(PATTERN.matcher(s).find() ? PATTERN.matcher(s).group() : "");
//        Matcher matcher = PATTERN.matcher(s);
//
//        if(matcher.find()){
//            System.out.println(matcher.group());
//            if(matcher.group().length() <= 10){
//                list.add(s + "0");
//            }
//        } else{
//            list.add(s + "0");
//        }
        if(s.length() < 16) list.add(trim(s + "0"));
        list.add(comp(s));
        return list;
    }

    public static String comp(String s){
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            sb.append(c == '1' ? '0' : '1');
        }


        return trim(sb.toString());
    }

    public static String trim(String s){
        return s.replaceAll("^0+(?!$)", "");
    }

    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }

    public static class Node {
        public String value;
        public List<String> edges;
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