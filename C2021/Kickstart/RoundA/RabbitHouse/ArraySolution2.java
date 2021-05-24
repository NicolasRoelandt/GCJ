package C2021.Kickstart.RoundA.RabbitHouse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class ArraySolution2 {

    private static int[][] grid;
    private static int C;
    private static int R;

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int[] line = lineToInt(in.nextLine());
            R = line[0];
            C = line[1];

            grid = new int[R][];
            int max = 0;

            for (int j = 0; j < R; j++) {
                grid[j] = lineToInt(in.nextLine());
                for (int k = 0; k < C; k++) {
                    max = Math.max(grid[j][k], max);
                }
            }

            System.out.println(String.format("Case #%d: %d", i, solve(max)));
        }
    }

    private static long solve(int max){
        long count = 0;
        int min = max-(R+C-2);
        max = max-min;
        Buckets buckets = new Buckets(max+1);

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                int h = grid[i][j];
                h -= min;
                if(h < 0) {
                    count -= h;
                    h = 0;
                }
                grid[i][j] = h;
                Cell cell = new Cell(new Coord(i, j), h);
                buckets.addTo(h, cell);
            }
        }

        for (int i = max; i >= 0; i--) {
            if(buckets.get(i) == null) continue;
            for (Cell peak : buckets.get(i)) {
                int h = peak.height;
                if (grid(peak.coord) != h) continue;

                for (Cell neighbor : neighbors(peak)) {
                    if(h - neighbor.height > 1){
                        count += h-neighbor.height-1;
                        grid[neighbor.coord.i][neighbor.coord.j] = h-1;
                        Cell cell = new Cell(neighbor.coord, h - 1);
                        buckets.addTo(h-1, cell);
                    }
                }
            }
        }

        return count;
    }

    private static class Buckets{
        Set<Cell>[] buckets;

        public Buckets(int size) {
            this.buckets = (Set<Cell>[]) new Set[size];;
        }

        private void addTo(int index, Cell cell){
            Set<Cell> bucket = buckets[index];
            if(bucket == null){
                bucket = new HashSet<>();
                buckets[index] = bucket;
            }
            bucket.add(cell);
        }

        private Set<Cell> get(int h){
            return buckets[h];
        }

    }

    private static int grid(Coord c){
        return grid[c.i][c.j];
    }

    private static List<Cell> neighbors(Cell cell){
        ArrayList<Cell> list = new ArrayList<>();
        int i = cell.coord.i;
        int j = cell.coord.j;

        Coord[] coords = {new Coord(i - 1, j), new Coord(i, j-1), new Coord(i+1, j), new Coord(i, j+1)};
        for (Coord coord : coords) {
            if(coord.isValid()){
                list.add(new Cell(coord, grid(coord)));
            }
        }

        return list;
    }

    private static class Coord{
        int i, j;

        public Coord(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public boolean isValid(){
            return i >= 0 && i < R && j >=0 && j < C;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coord coord = (Coord) o;
            return i == coord.i &&
                    j == coord.j;
        }

        @Override
        public int hashCode() {
            return Objects.hash(i, j);
        }
    }

    private static class Cell {
       Coord coord;

        public int getHeight() {
            return height;
        }

        int height;

        public Cell(Coord coord, int height) {
            this.coord = coord;
            this.height = height;
        }
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