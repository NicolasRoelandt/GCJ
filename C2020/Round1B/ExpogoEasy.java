package C2020.Round1B;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ExpogoEasy {

    enum Dir {
        N, E, S, W;
    }




    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int[] line = lineToInt(in.nextLine());
            int X = line[0];
            int Y = line[1];

            List<Dir[]> result = new ArrayList<>();
            for (int j = 1; j <= 4; j++) {
                allPoss(new Dir[j], result, 0);
            }
//            result.stream().map(Arrays::toString).forEach(System.out::println);

//            result.add(new Dir[]{Dir.S, Dir.E, Dir.N});
            String interpret = interpret(result, line);


            System.out.println(String.format("Case #%d: %s", i, interpret));
        }

    }

    private static String interpret(List<Dir[]> result, int[] target) {
        for (Dir[] dirs : result) {
            int[] pos = {0,0};
            for (int i = 0; i < dirs.length; i++) {
                int value = 1<<i;
                switch (dirs[i]){
                    case N:
                        pos[1]+=value;
                        break;
                    case E:
                        pos[0]+=value;
                        break;
                    case S:
                        pos[1]-=value;
                        break;
                    case W:
                        pos[0]-=value;
                        break;
                }

                if(Arrays.equals(pos, target)){
                    return Arrays.stream(dirs).limit(i+1).map(Enum::toString).collect(Collectors.joining(""));
                }
            }
        }

        return "IMPOSSIBLE";
    }

    public static void allPoss(Dir[] current, List<Dir[]> result, int i) {
        if (i >= current.length) {
            result.add(Arrays.copyOf(current, current.length));
            return;
        }

        for (Dir value : Dir.values()) {
            current[i] = value;
            allPoss(current, result, i + 1);
        }
    }

    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }

    public static int[] lineToInt(String line) {
        return lineToInt(line, " ");
    }
}