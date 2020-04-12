package C2019.Round1B;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;



public class ManhattanHard {


    private static int P;
    private static int Q;
    private static List<Person> personsNS;
    private static List<Person> personsWE;

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int[] line = lineToInt(in.nextLine(), " ");
            P = line[0];
            Q = line[1];
            personsNS = new ArrayList<>();
            personsWE = new ArrayList<>();
            for (int j = 0; j < P; j++) {
                Person person = new Person(in.nextLine());
                if (person.direction == Direction.S || person.direction == Direction.N) {
                    personsNS.add(person);
                } else {
                    personsWE.add(person);
                }
            }

            int[] solve = solve();


            System.out.println("Case #" + i + ": " + solve[0] + " " + solve[1]);
        }

    }

    private static int[] solve() {

        int[] views = new int[Q+1];
        int total = 0;
        for (Person person : personsWE) {
            if(person.direction == Direction.E){
                views[person.x]++;
            } else {
                views[person.x-1]--;
                total++;
            }
        }

        int maxX = 0;
        int max = total;
        for (int i = 0; i <= Q; i++) {
            if(total > max){
                max = total;
                maxX = i;
            }
            total += views[i];
        }

        views = new int[Q+1];
        total = 0;
        for (Person person : personsNS) {
            if(person.direction == Direction.N){
                views[person.y]++;
            } else {
                views[person.y-1]--;
                total++;
            }
        }

        int maxY = 0;
        max = total;
        for (int i = 0; i <= Q; i++) {
            if(total > max){
                max = total;
                maxY = i;
            }
            total += views[i];
        }

        return new int[]{maxX, maxY};

    }


    private static class Person {
        int x, y;
        Direction direction;

        public Person(String s) {
            String[] split = s.split(" ");
            this.x = Integer.parseInt(split[0]);
            this.y = Integer.parseInt(split[1]);
            this.direction = Direction.valueOf(split[2]);
        }

        public boolean walksTo(int x, int y) {
            switch (direction) {
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

    public enum Direction {
        S, N, W, E;
    }

    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }
}