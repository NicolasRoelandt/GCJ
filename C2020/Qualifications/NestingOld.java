package C2020.Qualifications;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class NestingOld {


    private static String s;

    public static final char LEFT = '(';
    public static final char RIGHT = ')';


    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            s = in.nextLine();

            System.out.println(solve(s));
        }

    }

    private static String solve(String s){
        int start, end;
        int level = 0;
        char neededLeft = 0;
        loop: for(start = 0; start < s.length(); start++){
            char c = s.charAt(start);
            switch(c){
                case LEFT:
                    level++;
                    break;
                case RIGHT:
                    level--;
                    break;
                default:
                    int value = Character.getNumericValue(c);
                    if(value == level) break;
                    if(value > level) neededLeft = LEFT;
                    else neededLeft = RIGHT;
                    break loop;
            }
        }


        char neededRight = 0;
        loop: for(end = s.length()-1; end >= start; end--){
            char c = s.charAt(end);
            switch(c){
                case LEFT:
                    level--;
                    break;
                case RIGHT:
                    level++;
                    break;
                default:
                    int value = Character.getNumericValue(c);
                    if(value == level) break;
                    if(value > level) neededRight = RIGHT;
                    else neededRight = LEFT;
                    break loop;
            }
        }

        if(neededRight == neededLeft && neededLeft == 0) return s;

        if(neededRight == neededLeft){
            throw new RuntimeException("pas bon");
        }

        String s2 = new StringBuilder(s).insert(end+1, neededRight).insert(start,neededLeft).toString();
        return solve(s2);

    }


    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }
}