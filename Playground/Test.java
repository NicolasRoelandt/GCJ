package Playground;

import java.util.*;

public class Test {


    public static void main(String[] args) {
        int max = 100;
        int N = 50;
        Random r = new Random();

        int[] array = new int[50];
        for (int i = 0; i < 100000000; i++) {
            for (int j = 0; j < N; j++) {
                array[j] = r.nextInt(max);
            }

            double average = Arrays.stream(array).average().getAsDouble();

            int sum = 0;

            for (int j = 1; j < N; j++) {
                sum += Math.min(array[j-1], array[j]);
            }

            double value = sum/N;

//            System.out.println("average : " + average);
//            System.out.println("value : " + value);

            if(value > average){
                System.out.println("false");
                System.out.println(Arrays.toString(array));
            }
        }



//        //           0  0  1  1  2  2
//
//        int[] sum = precomputeSum(arr);
//        for (int i = 0; i < arr.length; i++) {
//            for (int j = i; j < arr.length; j++) {
//                if(getOnes(i,j, arr, sum) != getOnesBF(i,j, arr)){
//                    throw new RuntimeException();
//                }
//            }
//        }

//        System.out.println(Integer.toBinaryString(-1));
//        System.out.println(Integer.toBinaryString(-1));
////        System.out.println(Integer.toBinaryString(-1 >> 1));
////        System.out.println(Integer.toBinaryString(-1 >>> 1));
////        System.out.println(-1 >>> 1);

//        Set<Integer> a = new LinkedHashSet();
////
////        a.add(1);
////        a.add(2);
////
////        Set<Integer> b = new LinkedHashSet();
////
////        b.add(2);
////        b.add(1);
////
////        System.out.println(a.equals(b));
////
////        for (Integer integer : a) {
////            System.out.println("a "+ integer );
////        }
////
////        for (Integer integer : b) {
////            System.out.println("b "+ integer );
////        }

//        System.out.println(findBalancedExpression(")("));

    }

    static boolean findBalancedExpression(String str) {

        Stack<Character> st = new Stack<>();


        for (char ch : str.toCharArray()) {

            if (ch == ')') {
                if (st.isEmpty()) {
                    st.push(ch);
                } else if (st.peek() == '(') {
                    st.pop();
                }
            } else if (ch == '(') {
                st.push(ch);

            }
        } // end of loop

        return st.isEmpty() == true;
    }

    public static int[] precomputeSum(int[] a){
        int[] sumA = new int[a.length];

        int sum = 0;
        for(int i=0; i<a.length; i++){
            sum += a[i];
            sumA[i] = sum;
        }

        return sumA;

    }

    public static int getOnes(int i,int j, int[] a, int[] sum){
        int s1 = sum[i];
        int s2 = sum[j];

        return s2 - s1 + a[i];
    }

    public static int getOnesBF(int i, int j, int[] a){
        int sum = 0;
        for (int k = i; k <= j; k++) {
            sum += a[k];
        }

        return sum;
    }
}
