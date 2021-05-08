package C2021.Round1A;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class HackedExamEasy {


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
        List<String> set = new ArrayList<>();
        binaryStrings(Q).forEach(bs -> {
            String s = bs.replaceAll("1", "T").replaceAll("0", "F");

            if (Arrays.stream(students).allMatch(student -> isPossibleAnswer(s, student.answers, student.score))) {
                set.add(s);
            }
        });
        int l = set.size();
        StringBuilder sb = new StringBuilder();
        long count = 0;
        for (int i = 0; i < Q; i++) {
            int ts = countTs(set, i);
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

    private static int countTs(List<String> list, int i) {
        return list.stream().mapToInt(s -> s.charAt(i) == 'T' ? 1 : 0).sum();
    }

    private static boolean isPossibleAnswer(String answer, String student, int score) {
        int count = 0;
        for (int i = 0; i < answer.length(); i++) {
            if (answer.charAt(i) == student.charAt(i)) count++;
        }
        return count == score;
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

    private static String flip(String s) {
        StringBuilder sb = new StringBuilder(s.length());
        for (char c : s.toCharArray()) {
            if (c == 'F') sb.append('T');
            else sb.append('F');
        }
        return sb.toString();
    }

    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }

    public static int[] lineToInt(String line) {
        return lineToInt(line, " ");
    }

    public static Stream<String> binaryStrings(int n) {
        return LongStream.range(0, 1L << n).mapToObj(l -> getBinaryString(l, n));
    }

    public static String getBinaryString(long value, int size) {
        return Long.toBinaryString(1L << size | value).substring(1, size + 1);
    }
}