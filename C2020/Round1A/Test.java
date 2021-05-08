package C2020.Round1A;

import java.util.List;

public class Test {
    static int count = 0;

    public static void main(String[] args) {
        combinations("123456789", 100, "");
        System.out.println(count);
    }

    public static void combinations(String s, int target, String result){
        if(s.isEmpty()){
            if(target ==0){
                String toDisplay = result.charAt(0) == '+' ? result.substring(1) : result;
                System.out.println(toDisplay);
            }
        } else {

            for (int i = 1; i <= s.length(); i++) {
                String prefix = s.substring(0, i);
                int computed = Integer.parseInt(prefix);

                String remainder = s.substring(i);

                combinations(remainder, target + computed, result + "-" + prefix);
                combinations(remainder, target - computed, result + "+" + prefix);
            }
        }
        count++;
    }
}

