package Playground;

import java.util.Stack;

public class CandyCrush1D {
    public static void main(String[] args) {
        System.out.println(reduce("abccba"));
    }

    public static String reduce(String input){
        if(input.length() <1) return "";
        char[] s = input.toCharArray();
        Stack<Character> stack = new Stack<>();
        stack.push(s[0]);
        int i = 1;
        while(i < s.length) {

            if(stack.peek() == s[i]){
                while(i < s.length && stack.peek() == s[i]){
                    i++;
                }
                stack.pop();
            } else {
                stack.push(s[i]);
                i++;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (Character character : stack) {
            sb.append(character);
        }
        return sb.toString();
    }

    private static class Letter{
        int index;
        char letter;

        public Letter(int index, char letter) {
            this.index = index;
            this.letter = letter;
        }
    }
}
