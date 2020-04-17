package ca.jrvs.practice.codingChallenge;

import java.lang.reflect.Array;
import java.util.*;

public class duplicateCharactersSolution1 {
    public static void main(String[] args) {
        duplicateCharactersSolution1 solution = new duplicateCharactersSolution1();
        System.out.println(solution.findDuplicateCharacters("A black cat a b c "));
    }

    public ArrayList<String> findDuplicateCharacters(String input) {
//        //This method will show duplicate characters multiple times
//         //if the character appears three or more times
//        char[] c = input.toCharArray();
//        ArrayList<String> s = new ArrayList<String>();
//        for (int i = 0; i < input.length(); i++) {
//            for (int j = i + 1; j < input.length(); j++) {
//                if ((c[i] == c[j]) && (!Character.isWhitespace(c[i]))) {
//
//                    s.add(String.valueOf(c[j]));
//                }
//            }
//        }
//    return s;
//    }
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        ArrayList<String> s = new ArrayList<String>();
        char[] c = input.toCharArray();
        for (Character ch : c) {
            if ((map.containsKey(ch)) && !Character.isWhitespace(ch)) {
                int counter = map.get(ch);
                map.put(ch, ++counter);
            } else {
                map.put(ch, 1);

            }
        }
        Set<Character> keys = map.keySet();
        for (Character ch : keys) {
            if (map.get(ch) > 1) {
                s.add(String.valueOf(ch));
            }
        }
        return s;
    }
}
