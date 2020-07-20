package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;
import java.util.Map;

public class ValidAnagram {
    public boolean validAnagram(String s, String t) {
        /**
         * Approach 1: Sorting
         * O(nlogn)
         */
        /*
        if(s.length()!=t.length()) return false;
        char[] ch1 = s.toCharArray();
        char[] ch2 = t.toCharArray();
        Arrays.sort(ch1);
        Arrays.sort(ch2);
        return Arrays.equals(ch1, ch2);*/
        /**
         * Approach 2: O(n)
         * count the frequency of each letter
         */
        if (s.length() != t.length()) return false;
        return stringToMap(s).equals(stringToMap(t));
    }

    public Map stringToMap(String str) {
        Map map = new HashMap();
        char[] ch = str.toCharArray();
        for (Character c : ch) {
            if (map.containsKey(c) && !Character.isWhitespace(c)) {
                int counter = (int) map.get(c);
                map.put(c, ++counter);
            } else {
                map.put(c, 1);
            }
        }
        return map;
    }
}
