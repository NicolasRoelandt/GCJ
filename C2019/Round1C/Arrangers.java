package C2019.Round1C;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Arrangers {



    private static boolean err = true;
    private final Scanner sc;
    public static final Set<Character> letters = createLetterSet();
//    private static final Set<String> allCombin = getAllCombin();
    private List<String> myComb;
    private int asked = 0;

    int i = 0;



    public static void main(String[] args) {

        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));



        String s = read(in);
        int[] line = lineToInt(s, " ");
        int t = line[0];// Scanner has functions to read ints, longs, strings, chars, etc.
//        int t = 10;
        for (int i = 1; i <= t; ++i) {
            Arrangers solution = new Arrangers(in);
            solution.solve();
        }

    }

//    public static Set<String> getAllCombin(){
//        char[] current = new char[5];
//        HashSet<String> set = new HashSet<>();
//        HashSet<Character> remaining = new HashSet<>(letters);
//
//
//        getAllCombin(set, current, 0, remaining);
//        return set;
//    }

    private static HashSet<Character> createLetterSet() {
       char[] letters = {'A','B','C', 'D', 'E'};
        HashSet<Character> remaining = new HashSet<>();
        for (char letter : letters) {
            remaining.add(letter);
        }
        return remaining;
    }
//
//    public static void getAllCombin(Set<String> combin, char[] current, int i, Set<Character> remaining){
//        if(i == 5){
//            combin.add(new String(current));
//            return;
//        }
//
//        HashSet<Character> copy = new HashSet<>(remaining);
//        for (char letter : copy) {
//            current[i] = letter;
//            remaining.remove(letter);
//            getAllCombin(combin, current, i+1, remaining);
//            remaining.add(letter);
//        }
//    }

    private void solve() {
        Set<Integer> remaining = IntStream.range(0, 119).boxed().collect(Collectors.toSet());
        char[] result = new char[5];

        Map.Entry<Character, Set<Integer>> entry = getNextSet(remaining, 24, 0);
        result[0] = entry.getKey();
        entry = getNextSet(entry.getValue(), 6, 1);
        result[1] = entry.getKey();
        entry = getNextSet(entry.getValue(), 2, 2);
        result[2] = entry.getKey();
        entry = getNextSet(entry.getValue(), 0, 3);
        result[3] = entry.getKey();

        result[4] = findMissing(result);

        char t = result[3];
        result[3] = result[4];
        result[4] = t;

//        print(new String("EDABC"));
        print(new String(result));

        if(read(sc).equals("N")){
            throw new RuntimeException("hoho");
        }
    }

    private Map.Entry<Character, Set<Integer>> getNextSet(Set<Integer> valuesToCheck, int expectedValue, int i) {
        Map<Character, Set<Integer>> map = new HashMap<>();

        for (Integer c : valuesToCheck) {
            print(1+ i+c * 5 + "");
            String result = read(sc);
            char letter = result.charAt(0);
            if (map.containsKey(letter)) {
                map.get(letter).add(c);
            } else {
                map.put(letter, new HashSet<>());
                map.get(letter).add(c);
            }
        }

        for (Map.Entry<Character, Set<Integer>> entry : map.entrySet()) {
            if(entry.getValue().size() != expectedValue){
                return entry;
            }
        }

        throw new RuntimeException();
    }

    private char findMissing(char[] team) {
        HashSet<Character> remaining = new HashSet<>(letters);
        for (int i = 0; i < 4; i++) {
            remaining.remove(team[i]);
        }
        return remaining.iterator().next();
    }


    public Arrangers(Scanner sc) {
//        Random rand = new Random();
//        int r = rand.nextInt(120);
//        myComb = new ArrayList<>(allCombin);
//        System.out.println(myComb.get(r));
//        myComb.remove(r);
        this.sc = sc;
    }



    private void print(String s) {
        System.out.println(s);
        if (err && i%2 ==0){
            System.err.println("out: " + s);
        }
        i++;
        System.out.flush();
//        asked = Integer.parseInt(s);
    }

    private static String read(Scanner sc) {
        String s = sc.nextLine();
        if (err){
//           System.err.println("in: " + s);
        }
        return s;
//        char c = myComb.get((asked - 1) / 5).charAt((asked - 1) % 5);
//        System.out.println(c);
//        return c + "";
    }

    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }
}
