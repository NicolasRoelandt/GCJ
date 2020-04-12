package C2020.Qualifications;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Scanner;
import java.util.stream.Stream;


public class Indicium {


    private static int[][] matrix;
    private static int N;
    private static int K;

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int c = 1; c <= t; ++c) {
                int[] line = lineToInt(in.nextLine(), " ");
            N = line[0];
            K = line[1];
            matrix = new int[N][N];

            System.out.println(Arrays.toString(printAllUniqueParts(K)));

            if(false){
                System.out.println( String.format("Case #%d: %s", c,"POSSIBLE"));
                for (int i = 0; i < matrix.length; i++) {
                    for (int j = 0; j < matrix[i].length; j++) {
                        System.out.print(matrix[i][j] + " ");
                    }
                    System.out.println();
                }
            } else {
                System.out.println( String.format("Case #%d: %s", c,"IMPOSSIBLE"));
            }
        }

    }

    // Function to generate all unique partitions of an integer
    static int[] printAllUniqueParts(int n)
    {
        int[] p = new int[n]; // An array to store a partition
        int k = 0;  // Index of last element in a partition
        p[k] = n;  // Initialize first partition as number itself

        // This loop first prints current partition then generates next
        // partition. The loop stops when the current partition has all 1s
        while (true)
        {
            // print current partition
            if(k+1 == N){
                return p;
            }

            // Generate next partition

            // Find the rightmost non-one value in p[]. Also, update the
            // rem_val so that we know how much value can be accommodated
            int rem_val = 0;
            while (k >= 0 && p[k] == 1)
            {
                rem_val += p[k];
                k--;
            }

            // if k < 0, all the values are 1 so there are no more partitions
            if (k < 0) return p;

            // Decrease the p[k] found above and adjust the rem_val
            p[k]--;
            rem_val++;


            // If rem_val is more, then the sorted order is violated.  Divide
            // rem_val in different values of size p[k] and copy these values at
            // different positions after p[k]
            while (rem_val > p[k])
            {
                p[k+1] = p[k];
                rem_val = rem_val - p[k];
                k++;
            }

            // Copy rem_val to next position and increment position
            p[k+1] = rem_val;
            k++;
        }
    }

    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }
}