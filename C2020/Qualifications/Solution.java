package C2020.Qualifications;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Stream;

public class Solution {


    private static boolean err = false;
    private final Scanner sc;
    private static int B;
    private int[] bits;
    private int countReads =0;
    private Test test;

    public static boolean isTest = true;
    private int equalIndex;
    private int equalValue;
    private int differentIndex;
    private int differentValue;

    enum Fluctuation {
        SWAP,
        COMPLEMENT,
        SWAP_AND_COMPLEMENT,
        NOTHING,
    }


    public static void main(String[] args) {

        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));


        String s = !isTest ? read(in) : "1 20";
        int[] line = lineToInt(s, " ");
        int t = line[0];
        B = line[1];

        for (int i = 1; i <= t; ++i) {
            Solution solution = new Solution(in);
            solution.solve();
        }

    }


    private void solve() {
        bits = new int[B];
        Arrays.fill(bits, -1);
        equalIndex = -1;
        equalValue = -1;
        differentIndex = -1;
        differentValue = -1;
        int i = 0;

        boolean firstLoop = true;

        while (i < B / 2) {
            if(!firstLoop) correctAfterFluctuation();
            for (int j = 0; j < (firstLoop ? 5: 4); i++, j++) {
                int b1 = readAndFill(i);
                int b2 = readAndFill(B - 1 - i);
                if (equalIndex < 0 && b1 == b2) {
                    equalIndex = i;
                    equalValue = b1;
                }
                if (differentIndex < 0 && b1 != b2) {
                    differentIndex = i;
                    differentValue = b1;
                }
            }
            firstLoop = false;
        }

        if(!isTest) {
            print(Arrays.toString(bits).replaceAll("[, \\[\\]]", ""));
            String result = read(sc);

            if (result.equals("N")) {
                System.exit(0);
            }
        } else {
            test.isAnswer(bits);
        }
    }

    private void correctAfterFluctuation() {
        Set<Fluctuation> fluctuations = new HashSet<>();
        fluctuations.add(Fluctuation.SWAP);
        fluctuations.add(Fluctuation.SWAP_AND_COMPLEMENT);
        fluctuations.add(Fluctuation.COMPLEMENT);
        fluctuations.add(Fluctuation.NOTHING);

        if (equalIndex >= 0) {
            int b = read(equalIndex);
            if (b == equalValue) {
                fluctuations.remove(Fluctuation.COMPLEMENT);
                fluctuations.remove(Fluctuation.SWAP_AND_COMPLEMENT);
            } else {
                fluctuations.remove(Fluctuation.NOTHING);
                fluctuations.remove(Fluctuation.SWAP);
                equalValue = b;
            }
        } else {
            read(0);
            fluctuations.remove(Fluctuation.SWAP_AND_COMPLEMENT);
            fluctuations.remove(Fluctuation.SWAP);
        }

        if (differentIndex >= 0) {
            int b = read(differentIndex);
            if (b == differentValue) {
                fluctuations.remove(Fluctuation.COMPLEMENT);
                fluctuations.remove(Fluctuation.SWAP);
            } else {
                fluctuations.remove(Fluctuation.NOTHING);
                fluctuations.remove(Fluctuation.SWAP_AND_COMPLEMENT);
                differentValue = b;
            }
        } else {
            read(0);
            fluctuations.remove(Fluctuation.SWAP_AND_COMPLEMENT);
            fluctuations.remove(Fluctuation.SWAP);
        }

        if (fluctuations.size() != 1) {
            throw new RuntimeException();
        }

        Fluctuation fluctuation = fluctuations.iterator().next();

        switch (fluctuation) {
            case COMPLEMENT:
                complementArray(bits);
                break;
            case SWAP:
                swapArray(bits);
                break;
            case SWAP_AND_COMPLEMENT:
                complementArray(bits);
                swapArray(bits);
        }
    }

    private static int invertBit(int b){
        return b == 0 ? 1 : 0;
    }

    private static void complementArray(int[] bits) {
        for (int i = 0; i < bits.length; i++) {
            int b = bits[i];
            if (b < 0) continue;
            bits[i] = invertBit(b);

        }
    }

    private static void swapArray(int[] bits) {
        for (int i = 0; i < bits.length/ 2; i++) {
            int b = bits[i];
            if (b < 0) continue;
            bits[i] = bits[bits.length - 1- i];
            bits[bits.length - 1-  i] = b;
        }
    }

    public int readAndFill(int i) {
        int b = read(i);
        bits[i] = b;
        return b;
    }

    private int read(int i) {
        countReads++;
        if (isTest) {
            return test.read(i);
        }
        print("" + (i + 1));
        return Integer.parseInt(read(sc));
    }

    public Solution(Scanner sc) {
        this.sc = sc;
        if (isTest) {
            this.test = new Test();
        }
    }


    private void print(String s) {
        System.out.println(s);
        if (err) {
            System.err.println("out: " + s);
        }
        System.out.flush();
    }

    private static String read(Scanner sc) {
        String s = sc.nextLine();
        if (err) {
            System.err.println("in: " + s);
        }
        return s;
    }

    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }

    private static class Test {
        int[] bits;
        int counter = 1;
        Random r = new Random();

        public Test() {
//            bits = new int[]{1, 0, 0, 1, 0, 0, 1, 1, 1, 1};
            this.bits = new int[B];


            for (int i = 0; i < B; i++) {
                this.bits[i] = r.nextBoolean() ? 1 : 0;
            }
        }

        public int read(int i) {
            if (counter % 10 == 1) {
                switch (r.nextInt(4)) {
                    case 0:
                        System.err.print(String.format("Swap  from %s to ", toString()));
                        swapArray(this.bits);
                        break;
                    case 1:
                        System.err.print(String.format("Complement from %s to ", toString()));
                        complementArray(this.bits);
                        break;
                    case 2:
                        System.err.print(String.format("Complement and swap from %s to ", toString()));
                        complementArray(this.bits);
                        swapArray(this.bits);
                        break;
                    case 3:
                        System.err.print("nothing : ");
                }

//                switch (counter /10 ) {
//                    case 1:
//                        System.err.print(String.format("Swap  from %s to ", toString()));
//                        swapArray(bits);
//                        break;
//                    case 0:
//                        System.err.print(String.format("Complement from %s to ", toString()));
//                        complementArray(bits);
//                        break;
//                    case 5:
//                        System.err.print(String.format("Complement and swap from %s to ", toString()));
//                        complementArray(bits);
//                        swapArray(bits);
//                        break;
//                    case 4:
//                        System.err.print("nothing : ");
//                }
                System.err.println(toString());
            }
            counter++;
            return this.bits[i];
        }

        @Override
        public String toString() {
            return Arrays.toString(this.bits);
        }

        public void isAnswer(int[] bits) {
            if (Arrays.equals(bits, this.bits)) {
                System.err.print(String.format("Found %s", toString()));
            } else {
                System.err.print(String.format("Answer given : %s but value %s", Arrays.toString(bits),toString()));
            }
        }
    }
}
