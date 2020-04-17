package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;
import java.util.Map;

public class compareMapsSolutions {
    public <K, V> boolean compareMaps(Map<K, V> m1, Map<K, V> m2) {
        return m1.equals(m2);
    }

    public static void main(String[] args) {
        Map m1 = new HashMap();
        Map m2 = new HashMap();
        m1.put(1,"hello");
        m2.put(1,"hello");
        compareMapsSolutions solution = new compareMapsSolutions();
        boolean isEqual = solution.compareMaps(m1,m2);
        System.out.println(isEqual);
    }
}
