package C2021.Kickstart.RoundA.RabbitHouse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class QueueSolutionWithoutRemove {

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

            for (int j = 0; j < R; j++) {
                grid[j] = lineToInt(in.nextLine());
            }

            System.out.println(String.format("Case #%d: %d", i, solve()));
        }
    }

    private static long solve(){
        long count = 0;
        PriorityQueue<Cell> queue = new PriorityQueue<>(Comparator.comparing(Cell::getHeight).reversed());

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                Cell cell = new Cell(new Coord(i, j), grid[i][j]);
                queue.add(cell);
            }
        }

        while(!queue.isEmpty()){
            Cell peak = queue.poll();
            int h = peak.height;
            if (grid(peak.coord) != h) continue;

            for (Cell neighbor : neighbors(peak)) {
                if(h - neighbor.height > 1){
                    count += h-neighbor.height-1;
                    grid[neighbor.coord.i][neighbor.coord.j] = h-1;
                    Cell cell = new Cell(neighbor.coord, h - 1);
                    queue.add(cell);
                }
            }
        }

        return count;
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