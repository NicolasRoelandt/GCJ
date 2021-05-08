package C2021.Qualifications;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Cheating {



    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        in.nextLine();
        for (int i = 1; i <= t; ++i) {
            int[][] matrix = new int[100][];
            for (int j = 0; j < 100; j++) {
                matrix[j] = lineToInt(in.nextLine(), "");
            }



            System.out.println(String.format("Case #%d: %d", i, solve(matrix)));
        }

    }

    public static int solve(int[][] matrix){
        List<Question> questions = new ArrayList<>();
        for (int j = 0; j < 10000; j++) {
            Question q = new Question(j, 0);
            for (int i = 0; i < 100; i++) {
                q.success+=matrix[i][j];
            }
            questions.add(q);
        }

        questions.sort(Comparator.comparing(Question::getSuccess));

        int numberHardestQuestions = 100;

        Set<Integer> hardestQuestionsIndex = new HashSet<>();

        for (int i = 0; i < numberHardestQuestions; i++) {
            hardestQuestionsIndex.add(questions.get(i).index);
        }


        int numberEasiestQuestions = 500;

        Set<Integer> easiestQuestionIndexes = new HashSet<>();

        for (int i = questions.size()-numberEasiestQuestions; i < questions.size()-1; i++) {
            easiestQuestionIndexes.add(questions.get(i).index);
        }

        List<Player> players = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Player p = new Player(i, 0);
            for (Integer index : hardestQuestionsIndex) {
                p.success += matrix[i][index];
            }
            players.add(p);
        }

        int numberBestPlayers = 5;

        players.sort(Comparator.comparing(Player::getSuccess).reversed());

        players = players.stream().limit(numberBestPlayers).collect(Collectors.toList());

        for (Player p : players) {
            int failures = 0;
            for (Integer index : easiestQuestionIndexes) {
                if(matrix[p.index][index] == 0){
                    failures++;
                }
            }
            p.success = failures;
        }

        players.sort(Comparator.comparing(Player::getSuccess).reversed());


        return  players.get(0).index+1;

    }

    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }

    private static class Question{
        int index, success;

        public int getSuccess() {
            return success;
        }

        public Question(int index, int success) {
            this.index = index;
            this.success = success;
        }
    }

    private static class Player {
        int index, success;

        public int getSuccess() {
            return success;
        }

        public Player(int index, int success) {
            this.index = index;
            this.success = success;
        }
    }

    public static int[] lineToInt(String line) {
        return lineToInt(line, " ");
    }
}