package C2019.Round1B;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Stream;


public class ManhattanEasy {


    private static int P;
    private static int Q;
    private static Person[] persons;

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int[] line = lineToInt(in.nextLine(), " ");
            P = line[0];
            Q = line[1];
            persons = new Person[P];
            for (int j = 0; j < P; j++) {
                persons[j] = new Person(in.nextLine());
            }

            int[] solve = solve();


            System.out.println("Case #" + i + ": " + solve[0] + " " + solve[1]);
        }

    }

    private static int[] solve() {
        int maxX = -1;
        int maxY = -1;
        int max = -1;
        for (int x = 0; x <= Q; x++) {
            for (int y = 0; y <= Q; y++) {
                int count = count(x,y);
                if(count > max){
                    maxX =x;
                    maxY = y;
                    max = count;
                }
            }
        }

        return new int[]{maxX, maxY};

    }

    private static int count(int x, int y) {
        return (int) Arrays.stream(persons).filter(p -> p.walksTo(x, y)).count();
    }


    private static class Person{
        int x, y;
        Direction direction;

        public Person(String s){
            String[] split = s.split(" ");
            this.x = Integer.parseInt(split[0]);
            this.y = Integer.parseInt(split[1]);
            this.direction = Direction.valueOf(split[2]);
        }

        public boolean walksTo(int x, int y) {
            switch (direction){
                case N:
                    return y > this.y;
                case S:
                    return y < this.y;
                case E:
                    return x > this.x;
                case W:
                    return x < this.x;
            }
            throw new IllegalStateException();
        }
    }

    private enum Direction{
        S,N,W,E;
    }

    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }
}