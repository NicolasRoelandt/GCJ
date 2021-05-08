package C2020.Round1B;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class Solution {


    private static boolean err = true;
    private final Scanner sc;
    private final static String CENTER = "CENTER";
    private final static String HIT = "HIT";
    private final static String MISS = "MISS";
    private final static long limit = 1_000_000_000;
    public static boolean isTest = false;
    private static Test test;
//    private final static long limit = 4;


    public static void main(String[] args) {

        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));


        String s = read(in);
        int[] line = lineToInt(s, " ");
        int t = line[0];// Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            Solution solution = new Solution(in);
            solution.solve();

        }

//        for (int i = -5; i <=5 ; i++) {
//            for (int j = -5; j < 5; j++) {
//                test = new Test(i,j);
//                Expogo solution = new Expogo(in);
//                solution.solve();
//            }
//
//        }

    }


    private void solve() {
        try {

            long[] inside = findInside();

            long start = binarySearch(-limit, inside[0], x -> isInside(x, inside[1]));

            long end = binarySearch(Math.min(start + 1, limit), limit, x -> !isInside(x, inside[1]));
            if (end != limit) end--;

            long centerX = (start + end) / 2;

            long bottomCenter = binarySearch(-limit, inside[1], y -> isInside(centerX, y));
            long topCenter = binarySearch(Math.min(bottomCenter + 1, limit), limit, y -> !isInside(centerX, y));
            if (topCenter != limit) topCenter--;

            long centerY = (topCenter + bottomCenter) / 2;

//        int[] d = new int[]{0, 1, -1, 2}; //, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10, -10, 11, -11, 12, -12, 13, -13};
//        for (int dx : d) {
//            for (int dy : d) {
            for (int i = 0; i < 200; i++) {
                long[] spiral = getXZForMap(i);
                long cx = centerX + spiral[0];
                long cy = centerY + spiral[1];
                if (isTest) {
                    if (test.isCenter(cx, cy)) return;
                } else {
                    print(String.format("%d %d", cx, cy));
                    if (read(sc).equals(CENTER)) return;
                }
            }
        }catch (RuntimeException e){
            if(e.getMessage().equals("a")) return;
            throw e;
        }
//
//            }
//        }

        throw new RuntimeException("here");
        //  System.exit(0);
    }

    private static long[] getXZForMap(int np)
    {
        // (dx, dy) is a vector - direction in which we move right now
        int dx = 0;
        int dy = 1;
        // length of current segment
        int segment_length = 1;

        // current position (x, y) and how much of current segment we passed
        int x = 0;
        int y = 0;
        int segment_passed = 0;
        if (np == 0){
            return new long[]{y,x};
        }
        for (int n = 0; n < np; ++n) {
            // make a step, add 'direction' vector (dx, dy) to current position (x, y)
            x += dx;
            y += dy;
            ++segment_passed;

            if (segment_passed == segment_length) {
                // done with current segment
                segment_passed = 0;

                // 'rotate' directions
                int buffer = dy;
                dy = -dx;
                dx = buffer;

                // increase segment length if necessary
                if (dx == 0) {
                    ++segment_length;
                }
            }
        }
        return new long[]{y,x};
    }

    private long[] findInside(){
        long[] l = new long[]{0, 0};
        if(isInside(l[0], l[1])){
            return l;
        }

        l = new long[]{-limit/2, -limit/2};
        if(isInside(l[0], l[1])){
            return l;
        }

        l = new long[]{-limit/2, limit/2};
        if(isInside(l[0], l[1])){
            return l;
        }

        l = new long[]{limit/2, -limit/2};
        if(isInside(l[0], l[1])){
            return l;
        }

        l = new long[]{limit/2, limit/2};
        if(isInside(l[0], l[1])){
            return l;
        }



        throw new RuntimeException();
    }

    private long binarySearch(long left, long right, Function<Long, Boolean> check) {
        if(check.apply(left)){
            return left;
        }
        while (left < right) {
            long m = left+ (right -left) / 2;

            if(!check.apply(m)){
                left = m+1;
            }
            else right = m;

        }

        return left;
    }

    private boolean isInside(long x, long y) {
        if(isTest){
            return test.isInside(x,y);
        }
        print(String.format("%d %d", x, y));
        String read = read(sc);
        if(read.equals(CENTER)) throw new RuntimeException("a");
        return read.equals(HIT);

    }

    public Solution(Scanner sc) {
        this.sc = sc;
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
        if(s.equals("WRONG")){
            System.exit(0);
        }
        return s;
    }

    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }

    private static class Test {
        public static long radius = limit - 5;
        public long cx, cy;
        int counter =0;

        public Test(long cx, long cy) {
            this.cx = cx;
            this.cy = cy;
        }


        public boolean isInside(long x, long y) {
            counter++;
            long dx = x - cx;
            long dy = y - cy;
            return dx * dx + dy * dy <= radius * radius;
        }


        public boolean isCenter(long x, long y) {
            if(counter > 300) throw new RuntimeException("passed 300");
            counter++;
            return x == cx && y == cy;
        }
    }
}
