package C2021.Round1A;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.stream.Stream;


public class HackedExamMid {


    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int[] line = lineToInt(in.nextLine());
            int N = line[0];
            int Q = line[1];
            Student[] students = new Student[N];

            for (int j = 0; j < N; j++) {
                String[] lines = in.nextLine().split(" ");
                students[j] = new Student(lines[0], Integer.parseInt(lines[1]));
            }

            String result = solve(Q, students);

            System.out.println(String.format("Case #%d: %s", i, result));
        }

    }

    public static String solve(int Q, Student[] students) {
        Student student = getBestStudent(students, Q);
        if (student.score > Q / 2) {
            return format(student.answers, student.score, 1);
        } else {
            return format(flip(student.answers), Q - student.score, 1);
        }
    }

    private static String flip(String s) {
        StringBuilder sb = new StringBuilder(s.length());
        for (char c : s.toCharArray()) {
           sb.append(opposite(c));
        }
        return sb.toString();
    }

    private static Student getBestStudent(Student[] students, int Q) {
        int minDist = Q;
        Student best = null;
        for (Student student : students) {
            int dist = Math.min(student.score, Q - student.score);
            if (dist < minDist) {
                minDist = dist;
                best = student;
            }
        }
        return best;
    }

    private static char opposite(char c) {
        return c == 'T' ? 'F' : 'T';
    }

    private static class Student {
        String answers;
        int score;

        public Student(String answers, int score) {
            this.answers = answers;
            this.score = score;
        }
    }

    public static String format(String answer, long z, long w) {
        return String.format("%s %d/%d", answer, z, w);
    }

    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }

    public static int[] lineToInt(String line) {
        return lineToInt(line, " ");
    }
}