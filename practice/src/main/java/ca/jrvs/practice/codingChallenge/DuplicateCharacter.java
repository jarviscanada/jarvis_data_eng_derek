package ca.jrvs.practice.codingChallenge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

class DuplicateCharacter {
    /*    public ArrayList<String> duplicateCharacter(String input) {
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
     }*/
    /**
     * O(1)
     */
    public Set<String> duplicateCharacter(String input) {
        char[] ch = input.toCharArray();
        Set<String> set = new HashSet<>();
        Set<String> setTwo = new HashSet<>();
        for (char c : ch) {
            if (!set.add(String.valueOf(c))&&!Character.isWhitespace(c))
            setTwo.add(String.valueOf(c));
        }
        return setTwo;
    }
}