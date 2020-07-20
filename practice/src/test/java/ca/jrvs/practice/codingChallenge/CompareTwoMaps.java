package ca.jrvs.practice.codingChallenge;

import java.util.Map;

public class CompareTwoMaps {
    /**
     * O(n)
     * @param m1
     * @param m2
     * @return
     */
    public boolean compareTwoMaps(Map m1, Map m2) {
        if (m1.equals(m2)) return true;
        return false;
    }
}
