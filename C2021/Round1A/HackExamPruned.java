package C2021.Round1A;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;


public class HackExamPruned {


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
        if(students.length == 1){
            Student student = students[0];
            if(student.score > Q/2){
                return format(student.answers, student.score, 1);
            } else {
                return format(flip(student.answers), Q-student.score, 1);
            }
        }
        long[] counts = new long[Q];
        long l = buildPossibleAnswers(students, counts);
        StringBuilder sb = new StringBuilder();
        long count = 0;
        for (long ts : counts) {
            if (ts > l / 2) {
                sb.append('T');
                count += ts;
            } else {
                sb.append('F');
                count += l - ts;
            }
        }
        long gcd = BigInteger.valueOf(count).gcd(BigInteger.valueOf(l)).longValueExact();
        return format(sb.toString(), count / gcd, l / gcd);
    }

    private static String flip(String s){
        StringBuilder sb = new StringBuilder(s.length());
        for (char c : s.toCharArray()) {
            if(c == 'F') sb.append('T');
            else sb.append('F');
        }
        return sb.toString();
    }


    private static long buildPossibleAnswers(int index, int length, Data[] data, long[] countT) {
        if (index == length) {
            return 1;
        }
        long count = 0;
        if(isPossible(data, 'T', index)) {
            Data[] nd = nextData(data, 'T', index);
            count += buildPossibleAnswers(index+1, length, nd, countT);
            countT[index]+= count;
        }

        if(isPossible(data, 'F', index)) {
            Data[] nd = nextData(data, 'F', index);
            count += buildPossibleAnswers(index+1, length, nd, countT);
        }
        return count;
    }

//    private static long buildPossibleAnswers(int index, int length, Data[] data, long[] countT) {
//        if (index == length) {
//            return 1;
//        }
//        long count = 0;
//        if(isPossible(data, 'T', index)) {
//            Data[] nd = nextData(data, 'T', index);
//            count += buildPossibleAnswers(index+1, length, nd, countT);
//            countT[index]+= count;
//        }
//
//        if(isPossible(data, 'F', index)) {
//            Data[] nd = nextData(data, 'F', index);
//            count += buildPossibleAnswers(index+1, length, nd, countT);
//        }
//        return count;
//    }

    private static long buildPossibleAnswers(Student[] students, long[] countT) {
        Data[] data = Arrays.stream(students).map(student -> new Data(student.score, student.answers.toCharArray())).toArray(Data[]::new);
        return buildPossibleAnswers(0, students[0].answers.length(), data, countT);
    }

    private static boolean isPossible(Data[] data, char letter, int index){
        for (Data datum : data) {
            if(!datum.isPossible(letter, index)) return false;
        }
        return true;
    }

    private static Data[] nextData(Data[] data, char letter, int index){
        Data[] result = new Data[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = data[i].getNextData(letter, index);
        }
        return result;
    }




    private static class Data{
        int correctRem;
        char[] s;

        public Data(int correctRem, char[] s) {
            this.correctRem = correctRem;
            this.s = s;
        }

        public boolean isPossible(char letter, int index){
            int remainingLetters = s.length - index;
            if(correctRem == 0){
                return letter == opposite(s[index]);
            }

            if(remainingLetters == correctRem){
                return letter == s[index];
            }
            return true;
        }

        public Data getNextData(char letter, int index){
            return s[index] == letter ? new Data(correctRem-1, s) : new Data(correctRem, s);
        }
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