package C2019.Round1C;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class RobotsHard {

    private Move[] moves = new Move[6];
    private static Set<Move[]> opponents;
    private static int a;


    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            a = Integer.parseInt(in.nextLine());
            RobotsHard solution = new RobotsHard();
            opponents = new HashSet<>();

            for (int j = 0; j < a; j++) {
                String line = in.nextLine();
                Move[] moves = line.chars().mapToObj(Move::fromChar).toArray(Move[]::new);
                opponents.add(moves);
            }
            String result = solution.solve();

            System.out.println("Case #" + i + ": " + result);
        }

    }

    private String solve() {
        List<Move> result = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            Move move = oneRound(i);
            if (move == null) return "IMPOSSIBLE";
            result.add(move);
            if (opponents.size() == 0) break;
        }

        if (opponents.size() != 0) return "IMPOSSIBLE";

        return result.stream().map(Enum::name).collect(Collectors.joining());
    }

    private Move oneRound(int i) {
        Set<Move> set = new HashSet<>();

        for (Move[] opponent : opponents) {
            set.add(opponent[i % opponent.length]);
        }

        Move move = getMove(set);
        if(move == null) return null;

        Set<Move[]> nextOpponents = new HashSet<>();

        for (Move[] opponent : opponents) {
            int fight = move.fight(opponent[i % opponent.length]);
            if (fight == 0) {
                nextOpponents.add(opponent);
            } else if(fight == -1){
                throw new RuntimeException();
            }
        }
        opponents = nextOpponents;

        return move;
    }

    private Move getMove(Set<Move> set) {
        if (contains(set, "RPS")) return null;
        if(set.size() == 1) return set.iterator().next().opposite();
        if (contains(set, "RP")) {
            return Move.P;
        } else if (contains(set, "PS")) {
           return Move.S;
        } else if (contains(set, "SR")) {
            return Move.R;
        }
        throw new RuntimeException();
    }



    private static boolean contains(Set<Move> set, String s) {
        return s.chars().allMatch(c -> set.contains(Move.valueOf("" + ((char) c))));

    }


    private boolean wins(Move[] opponent) {
        int l = opponent.length;
        for (int i = 0; i < 6; i++) {
            int fight = moves[i].fight(opponent[i % l]);
            if (fight == 1) return true;
            if (fight == -1) return false;
        }
        return false;
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

    public int fight(Move other) {
        int result = this.value - other.value;
        if (result < -1) return 1;
        if (result > 1) return -1;
        return result;
    }

    public Move opposite(){
        switch(this){
            case R: return P;
            case P: return S;
            default: return R;
        }

    }

    public static Move fromChar(int c) {
        return Move.valueOf(((char) c) + "");
    }
}
}