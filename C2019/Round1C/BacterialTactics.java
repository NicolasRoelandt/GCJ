package C2019.Round1C;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;


public class BacterialTactics {

    char[][] grid;
    int R, C;

    public BacterialTactics(char[][] grid, int r, int c) {
        this.grid = grid;
        R = r;
        C = c;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int[] line = lineToInt(in.nextLine(), " ");
            int R = line[0];
            int C = line[1];

            char[][] grid = new char[R][C];
            for (int j = 0; j < R; j++) {
                grid[j] = in.nextLine().toCharArray();
            }

            BacterialTactics solution = new BacterialTactics(grid, R, C);


            System.out.println("Case #" + i + ": " + solution.solve());
        }

    }

    private int solve() {
        int count = 0;
        List<Move> moves = getMoves(grid);
        for (Move move : moves) {
            char[][] copy = copyGrid(grid);
            if(place(move.value, copy, move.r, move.c)){
                if(beccaWins(false, copy)) count++;
            }
        }
        return count;

    }

    private boolean beccaWins(boolean beccasTurn, char[][] grid) {
        List<Move> moves = getMoves(grid);

        for (Move move : moves) {
            char[][] copy = copyGrid(grid);
            if(!place(move.value, copy, move.r, move.c)) continue;
            boolean b = beccaWins(!beccasTurn, copy);
            if(b && beccasTurn) return true;
            if(!b && !beccasTurn) return false;
        }

        return !beccasTurn;
    }

    private List<Move> getMoves(char[][] grid) {
        List<Move> moves = new ArrayList<>();


        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                if(grid[r][c] == '.'){
                    moves.add( new Move(r,c, 'H'));
                    moves.add( new Move(r,c, 'V'));
                }
            }
        }
        return moves;
    }

    private class Move{
        int r, c;
        char value;

        public Move(int r, int c, char value) {
            this.r = r;
            this.c = c;
            this.value = value;
        }
    }

    private char[][] copyGrid(char[][] grid){
        char[][] result = new char[R][C];
        for (int r = 0; r < R; r++) {
            result[r] = Arrays.copyOf(grid[r], C);
        }
        return result;
    }

    private boolean place(char value, char[][] grid, int r, int c){
        grid[r][c] = value;
        if(value == 'V'){
            for (int i = r-1; i >=0; i--) {
                if(grid[i][c] == '.') {
                    grid[i][c] = value;
                } else if(grid[i][c] == '#') return false;
                else break;
            }
            for (int i = r+1; i < R; i++) {
                if(grid[i][c] == '.') {
                    grid[i][c] = value;
                } else if(grid[i][c] == '#') return false;
                else break;
            }

            return true;
        } else if(value == 'H'){
            for (int j = c-1; j >=0; j--) {
                if(grid[r][j] == '.') {
                    grid[r][j] = value;
                } else if(grid[r][j] == '#') return false;
                else break;
            }
            for (int j = c+1; j < C; j++) {
                if(grid[r][j] == '.') {
                    grid[r][j] = value;
                } else if(grid[r][j] == '#') return false;
                else break;
            }
            return true;
        } else{
            throw new RuntimeException();
        }
    }

    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }
}