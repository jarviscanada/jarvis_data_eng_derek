package ca.jrvs.practice.codingChallenge;

import ca.jrvs.practice.dataStructure.map.HashJMap;

import java.util.HashMap;
import java.util.Map;

public class compareMapsImpEquals extends HashJMap {
    public static <K, V> boolean compareMapsImpEquals(HashJMap<K, V> m1, HashJMap<K, V> m2) {
        return m1.equals(m2);
    }

    public static void main(String[] args) {

            HashJMap m1 = new HashJMap();
            HashJMap m2 = new HashJMap();
            m1.put("key1", "Hello");
            m1.put("key2", "map");
            m2.put("key1", "Hello");
            m2.put("key2", "map");
        System.out.println(compareMapsImpEquals(m1,m2));
        }

    }




