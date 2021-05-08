package com.isograd.exercise.Nov2020;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IsoContest {
    static Scanner in;

    static int[] nm;
    static int n;
    static int m;

    static int[] key;

    static int[] message;
    static int[] ags;

    public static void main(String[] argv)  {
        in = new Scanner(System.in);


         nm =  lineToInt(in.nextLine(), " ");
         n = nm[0];
         m = nm[1];


         key = lineToInt(in.nextLine(), " ");


         message =  new int[m];

        for (int i = 1; i < n; i++) {
            key[i] = key[i]^key[i-1];
        }


        for (int i = 0; i < m; i++) {
            ags =  lineToInt(in.nextLine(), " ");
            message[i] = (ags[0] == 0 ? 0 : key[ags[0] -1 ])^key[ ags[1]];
        }

        System.out.println(Arrays.toString(message).replaceAll("\\[|]|,", ""));
    }

    public static int[] lineToInt(String line, String regex) {
//        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
        String[] s = line.split(regex);
        int[] res= new int[s.length];
        for (int i = 0; i <s.length; i++) {
            res[i] = Integer.parseInt(s[i]);
        }
        return res;

    }


}
