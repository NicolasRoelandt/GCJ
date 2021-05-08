package com.isograd.exercise.Nov2020;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class bots {
    static Scanner in;
    static long[] r = {1L, 31L, 961L, 29791L, 923521L, 28629151L, 887503681L, 1742810335L, 2487512833L, 4098453791L, 2498015937L, 129082719L, 4001564289L, 3789408671L, 1507551809L, 3784433119L, 1353309697L, 3297894943L, 3450495425L, 3886143071L, 211350913L, 2256911007L, 1244764481L, 4227960543L, 2217757953L, 31019807L, 961614017L, 4040230751L, 693101697L, 11316127L, 350799937L, 2284863455L, 2111290369L, 1025491999L, 1725480897L, 1950300255L, 329765761L, 1632803999L, 3372283713L, 1461579999L, 2359307009L, 124073247L, 3846270657L, 3270273375L, 2594226817L, 3111619999L, 1970939457L, 969581023L, 4287207937L, 4054427167L};


//    public static void main(String[] argv) throws Exception {
////
////        long[] r = new long[50];
////        r[0] = 1;
////        for (int i = 1; i < 50; i++) {
////            r[i] = r[i-1]*31 % 4294967296L;
////        }
////
////        long hash = computeHash("BigBoss");
////        long temp = hash;
////
////        char[] result = new char[7];
////        for (int i = 0; i < 7 ; i++) {
////            char mult = (char)(temp / r[6-i]);
////            result[i] = mult;
////            temp -= mult*r[6-i];
////        }
////
////        System.out.println(result);
////
////
////    }

    public static void main(String[] argv) throws Exception {
//
//        in = new Scanner(System.in);
//
////        long hash = computeHash("BigBoss");
//        long hash = computeHash(in.nextLine());
//
//
//        char[] hashResult = {4, 24, 62, 19, 29, 24, 4};
////        char[] hashResult = computeResult(4294967296L, 6);
////        System.out.println(computeHash(new String(hashResult)));
//
//
//       int size = 6;
//       char[]result = computeResult(hash, size);
//       while(!validateResult(result)){
//           result = add(result, hashResult);
////           System.out.println(computeHash(new String(result)));
//       }
//
//        System.out.println(new String(result));
////        System.out.println(computeHash(new String(result)));
//        char c = "".charAt(0);
        System.out.println("\uD83D\uDC7D");
    }


    public static char[] computeResult(long hash, int size){
        long temp = hash;
        char[] result = new char[size+1];
        for (int i = size; i >= 0 ; i--) {
            char mult = (char)(temp / r[i]);
//            if(mult >= 33 && mult <=126) {
                result[size-i] =mult;
//            } else {
//               continue;
//            }
            temp -= mult*r[i];
        }
        return result;
    }


    public static long computeHash(String s){
        char[] chars = s.toCharArray();
        int n = s.length();
        long hash = 0;
        for (int i = 0; i < n; i++) {
            hash = (hash+chars[i]*r[n-1-i])%4294967296L;
        }
        return hash;
    }


    public static char[] add(char[] chars1, char[] chars2){
        char[] result = new char[chars1.length+1];
        int remainder = 0;
        for (int i = 0; i < chars2.length; i++) {
            int sum = chars1[chars1.length-i-1] + chars2[chars2.length-i-1] + remainder;
            if(sum > 126){
                remainder = (sum-126)/31+1;
                sum -= 31*remainder;
            } else {
                remainder = 0;
            }
            result[result.length-i-1] = (char) sum;
        }

        if(result[0] ==0){
            result = Arrays.copyOfRange(result, 1, result.length);
        }else {
            System.out.println("ha");
        }
        return result;
    }

    public static boolean validateResult(char[] result){
        for (char c : result) {
            if(c <33 || c > 126){
                return false;
            }
        }
        return true;
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
