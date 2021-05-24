package C2021.Kickstart.RoundB;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

// This is the implementation of the first part of the analysis, however it does TLE on the last test set
public class TruckDeliverySortingNotWorking {

    private static List<Road>[] roadsFrom;
    private static List<Query>[] queries;
    private static int Q;
    private static long[] result;
    private static int N;

    public static void main(String[] args) {

        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int[] line = lineToInt(in.nextLine());
            N = line[0];
            Q = line[1];

            Set<Integer> ends = new HashSet<>();

            roadsFrom = (List<Road>[]) new List[N +1];

            for (int j = 0; j < N - 1; j++) {
                long[] longs = lineToLong(in.nextLine());
                int X = (int) longs[0];
                int Y = (int) longs[1];
                int L = (int) longs[2];
                long A = longs[3];

                if(X != 1 && !ends.contains(X)){
                    int temp = X;
                    X = Y;
                    Y = temp;
                }

                Road road = new Road(X, Y, L, A);
                if(roadsFrom[X] == null){
                    roadsFrom[X] = new ArrayList<>();
                }
                roadsFrom[X].add(road);
                ends.add(Y);
            }

            queries =  (List<Query>[]) new List[N +1];

            for (int j = 0; j < Q; j++) {
                line = lineToInt(in.nextLine());
                int C = line[0];
                int W = line[1];

                Query query = new Query(C, W, j);

                if(queries[C] == null){
                    queries[C] = new ArrayList<>();
                }
                queries[C].add(query);
            }

            long[] result = solve();
            String s = Arrays.stream(result).mapToObj(Long::toString).collect(Collectors.joining(" "));

            System.out.println(String.format("Case #%d: %s", i, s));
        }
    }

    private static long[] solve() {
        result = new long[Q];



        Stack<Road> currentRoads = new Stack<>();
        visit(1, currentRoads);
        return result;
    }

    private static void visit(int city, Stack<Road> currentRoads) {
        if(queries[city] != null){
            solveForCity(queries[city].toArray(new Query[0]), currentRoads.toArray(new Road[0]));
        }
        List<Road> from = roadsFrom[city];
        if(from == null) return;

        for (Road road : from) {
            currentRoads.add(road);
            visit(road.Y, currentRoads);
            currentRoads.pop();
        }
    }
    private static void solveForCity(Query[] queries, Road[] roads) {
        Arrays.sort(queries);
        Arrays.sort(roads);

        long gcd = 0;

        int i = -1;
        for (Query query : queries) {
            while(i+1 < roads.length && roads[i+1].L <= query.W){
                i++;
                gcd = gcd(roads[i].A, gcd);
            }
            result[query.index] = gcd;
        }
    }


    private static class Road implements Comparable<Road>{
        int X;
        int Y;
        int L;
        long A;

        public Road(int x, int y, int l, long a) {
            X = x;
            Y = y;
            L = l;
            A = a;
        }


        @Override
        public int compareTo(Road o) {
            return L-o.L;
        }
    }

    private static class Query implements Comparable<Query>{
        int C;
        int W;
        int index;

        public Query(int c, int w, int index) {
            C = c;
            W = w;
            this.index = index;
        }

        @Override
        public int compareTo(Query o) {
            return W-o.W;
        }
    }

    public static long gcd(long n1, long n2) {
        if (n2 == 0) {
            return n1;
        }
        return gcd(n2, n1 % n2);
    }

    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }

    public static long[] lineToLong(String line) {
        return Stream.of(line.split(" ")).mapToLong(Long::parseLong).toArray();
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