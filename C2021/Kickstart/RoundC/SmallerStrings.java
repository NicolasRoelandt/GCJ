package C2021.Kickstart.RoundC;

import java.io.BufferedReader;
    import java.io.InputStreamReader;
    import java.math.BigInteger;
    import java.util.Scanner;
    import java.util.stream.LongStream;
    import java.util.stream.Stream;

    import static java.math.BigInteger.ONE;
    import static java.math.BigInteger.ZERO;


    public class SmallerStrings {
        static final BigInteger MOD = BigInteger.valueOf(1_000_000_007);
        private static int k;
        private static BigInteger K;


        public static void main(String[] args) {
            Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
            int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
            for (int i = 1; i <= t; ++i) {
                int[] line = lineToInt(in.nextLine());
                k = line[1];
    //        k = 5;
                K = BigInteger.valueOf(k);

                String s = in.nextLine();

    //        for (int i = 0; i < k; i++) {
    //            for (int j = 0; j < k; j++) {
    //                for (int l = 0; l < k; l++) {
    //                    for (int m = 0; m < k; m++) {
    //
    //
    //                        String s = new String(new char[]{(char) (i + 97), (char) (j + 97), (char) (l + 97), (char) (m + 97)});
    //                        long resultBF = solveFB(s);
    //                        long result = solve(s);
    //
    //                        if (resultBF != result) {
    //                            System.out.println(String.format("%s, r %d, rbd %d", s, result, resultBF));
    //                        }
    //                    }
    //                }
    //            }
    //        }
                long result = solve(s).mod(MOD).longValueExact();

                System.out.println(String.format("Case #%d: %d", i, result));
            }
        }

        private static BigInteger solve(String s){
            BigInteger total = ZERO;
            int letters = (s.length() + 1) / 2;
            int[] limits = new int[letters];
            for (int i = 0; i < letters; i++) {
                    char left = s.charAt(i);
                    limits[i]= left -96;
            }

            for (int i = 0; i < letters; i++) {
                BigInteger mult = BigInteger.valueOf(limits[i]-1).multiply(K.modPow(BigInteger.valueOf(letters- i-1), MOD)).mod(MOD);
                total = total.add(mult).mod(MOD);
            }

            return canAddOne(s, letters, limits) ? total.add(ONE) : total;
        }

        private static long solveFB(String s){
            long count = 0;

            int l = s.length();
            int[] o = new int[l];

            for (int i = 0; i < l; i++) {
                o[i] = s.charAt(i) -96;
            }

          return solveFB(new int[l], 0, o);
        }

        private static long solveFB(int[] s, int index, int[] o){
            if(index == s.length) {
                return isSmaller(s, o) && isPalindrome(s) ? 1 : 0;
            }
            long count = 0;
            for (int i = 1; i <= k; i++) {
                s[index] = i;
                count += solveFB(s, index+1, o);
            }
            return count;
        }

        private static boolean isPalindrome(int[] s){
            int l = s.length;
            for (int i = 0; i < l/2; i++) {
                if(s[i] != s[l-1-i]) return false;
            }
            return true;
        }

        private static boolean isSmaller(int[] s, int[] o){
            int l = s.length;
            for (int i = 0; i < l; i++) {
               if(s[i] < o[i]) return true;
               if(s[i] > o[i]) return false;
            }
            return false;
        }

        private static boolean canAddOne(String s, int letters, int[] limit) {
            int l = s.length();
            int[] c = new int[l];
            for (int i = 0; i < letters; i++) {
                c[l -1-i] = limit[i];
                c[i] = limit[i];
            }

            int[] o = new int[l];

            for (int i = 0; i < l; i++) {
                o[i] = s.charAt(i) -96;
            }

            return isSmaller(c, o);


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