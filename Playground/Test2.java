package Playground;

import java.util.*;
public class Test2{

    public static void main(String []args){
        List<String> words = Arrays.asList("cat", "bat", "tab");
        List<Character> alphabet = Arrays.asList('c', 'b', 'a', 't');
        System.out.println(isSorted(alphabet, words));

    }

    public static boolean isSorted(List<Character> alphabet, List<String> words){
        Map<Character, Integer> map = getMap(alphabet);
        for(int i = 1; i < words.size(); i++){
            if(!isBeforeOrEqual(words.get(i-1), words.get(i), map)){
                return false;
            }
        }



        return true;
    }

    public static boolean isBeforeOrEqual(String a, String b, Map<Character, Integer> map){

        int l = Math.min(a.length(), b.length());
        for(int i = 0; i < l; i++){
            if(map.get(a.charAt(i)) == map.get(b.charAt(i))) continue;
            return map.get(a.charAt(i)) < map.get(b.charAt(i));
        }

        return a.length() <= b.length();
    }

    public static Map<Character, Integer> getMap(List<Character> alphabet){
        Map<Character, Integer> map = new HashMap<>();
        for(int i = 0; i < alphabet.size(); i++){
            map.put(alphabet.get(i), i);
        }
        return map;
    }

}