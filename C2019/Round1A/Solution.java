package C2019.Round1A;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Stream;


public class Solution {


    private final Set<String> words;
    private int totalCount;

    public Solution(Set<String> words) {
        this.words = words;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());  // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int N = Integer.parseInt(in.nextLine());
            Set<String> words = new HashSet<>(N);
            for (int j = 0; j < N; j++) {
                words.add(in.nextLine());
            }

            Solution solution = new Solution(words);


            System.out.println("Case #" + i + ": " + solution.solve());
        }

    }

    private int solve() {
        Tree root = new Tree(true);
        for (String word : words) {
            root.addWord(word);
        }



        //root.cleanUp();
        //return totalCount;
        return words.size() - root.compute();
    }

    private class Tree {
        int revIndex;
        Tree[] children;
        int count = 0;
        boolean root;

        public Tree(boolean root) {
            this.root = root;
            revIndex = 0;
        }

        public Tree(int revIndex) {
            this.revIndex = revIndex;
        }

        public void addWord(String word) {
            if (word.length() <= revIndex) count++;
            else {
                if (children == null) children = new Tree[26];
                int letter = word.charAt(word.length() - 1 - revIndex) - 'A';
                Tree child = children[letter];
                if (child == null) {
                    children[letter] = new Tree(revIndex + 1);
                    child = children[letter];
                }
                child.addWord(word);
            }
        }

        public int cleanUp() {
            if (children != null) {
                for (Tree child : children) {
                    if (child == null) continue;
                    count += child.cleanUp();
                }
            }
            if(root) return -1;
            if (count == 2){
                totalCount +=2;
                return 0;
            }
            if (count >= 2) {
                totalCount+=2;
                return count - 2;
            } else {
                return count;
            }

        }

        public int compute(){
            if(children == null){
                return 1;
            }

            int count = 0;
            for (Tree child : children) {
                if(child != null){
                    count += child.compute();
                }
            }
            if(this.count != 0) count++;
            if(!root && count >=2) count-=2;
            return count;
        }

    }


    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }
}