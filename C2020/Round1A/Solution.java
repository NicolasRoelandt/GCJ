package C2020.Round1A;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Stream;

public class Solution {

    public static List<int[]> list;
    static int[] pos;


    public static void main(String[] args) {

        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

        int[] line = lineToInt(in.nextLine(), " ");
        int t = line[0];

        for (int i = 1; i <= t; ++i) {
            solve(lineToInt(in.nextLine(), " ")[0]);

            System.out.println(String.format("Case #%d:", i));
            list.stream().forEach( a -> System.out.println(String.format("%d %d", a[0], a[1])));
        }

    }

    public static void solve(int n){
        list = new ArrayList<>();
        pos = new int[]{0,1};
        if(n <=30){
            for (int i = 0; i < n; i++) {
                down();
            }
        } else {
            String s = new StringBuilder(Integer.toBinaryString(n - 30)).reverse().toString();
            boolean leftSide = true;
            int extra = 30;
            for (int i = 0; i < s.length(); i++) {
                down();
                if(s.charAt(i) == '1'){
                    for (int j = 0; j < i; j++) {
                        if(leftSide) right(); else left();
                    }

                    if(i!=0) leftSide = !leftSide;
                } else extra--;
            }

            for (int i = 0; i <extra; i++) {
                down();
            }
        }
    }

    public static void down(){
        pos[0]++;
        if(pos[1] != 1) pos[1]++;
        addPos();
    }

    private static void addPos() {
        list.add(Arrays.copyOf(pos, 2));
    }

    public static void right(){
        pos[1]++;
        addPos();
    }

    public static void left(){
        pos[1]--;
        addPos();
    }




    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }


}
