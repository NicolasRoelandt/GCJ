package com.isograd.exercise.Nov2020;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Keys {
    static Scanner in;

    public static void main(String[] argv) throws Exception {
        in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int count = 0;

        int[] nm =  lineToInt(in.nextLine(), " ");
        int n = nm[0];
        int m = nm[1];

        int[] key = lineToInt(in.nextLine(), " ");

        int[] message =  new int[256];
        int[] preCalc = new int[n+1];

        for (int i = 1; i <= n; i++) {
            preCalc[i] = preCalc[i-1]^key[i-1];
        }


        for (int i = 0; i < m; i++) {
            int[] ags =  lineToInt(in.nextLine(), " ");
            int L = ags[0];
            int R = ags[1];
            int res = preCalc[L]^preCalc[R+1];
            message[res]++;
        }

        System.out.println(Arrays.stream(message).mapToObj(i -> i+"").collect(Collectors.joining(" ")));
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