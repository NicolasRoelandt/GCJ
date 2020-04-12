package C2019.Round1C;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class RobotsEasy {

    private Move[] moves = new Move[6];
    private Move[][] opponents;



    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int A = Integer.parseInt(in.nextLine());
            RobotsEasy solution = new RobotsEasy();
            Move[][] opponents = new Move[A][];

            for (int j = 0; j < A; j++) {
                String line = in.nextLine();
                Move[] moves = line.chars().mapToObj(Move::fromChar).toArray(Move[]::new);
                opponents[j] = moves;
            }

            solution.opponents = opponents;
            boolean comb = solution.comb(0);
            String result = comb ? Arrays.stream(solution.moves).map(Enum::name).collect(Collectors.joining()) : "IMPOSSIBLE";

            System.out.println("Case #" + i + ": " + result);
        }

    }


    private boolean comb(int i){
        if(i == 6){
            return winsAll();
        }

        for (Move value : Move.values()) {
            moves[i] = value;
            if(comb(i+1)) return true;
        }
        return false;
    }

    private boolean wins(Move[] opponent){
        int l = opponent.length;
        for (int i = 0; i < 6; i++) {
            int fight = moves[i].fight(opponent[i % l]);
            if(fight == 1) return true;
            if(fight == -1) return false;
        }
        return false;
    }

    private boolean winsAll(){
        return Arrays.stream(opponents).allMatch(this::wins);
    }



    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }

    public enum Move {
        R(1), P(2), S(3);

        private final int value;

        Move(int value) {
            this.value = value;
        }

        public int fight(Move other){
            int result = this.value - other.value;
            if(result < -1) return 1;
            if(result > 1) return -1;
            return result;
        }

        public static Move fromChar(int c){
            return Move.valueOf(((char)c)+"");
        }
    }
}