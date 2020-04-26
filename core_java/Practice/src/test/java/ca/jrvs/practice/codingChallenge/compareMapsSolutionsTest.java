package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class compareMapsSolutionsTest {

    private compareMapsSolutions cm;
    @org.junit.Before
    public void setUp() throws Exception {
        cm = new compareMapsSolutions();
    }

    @org.junit.Test
    public void compareMaps() {
        Map m1 = new HashMap();
        Map m2 = new HashMap();
        m1.put("key1", "Hello");
        m1.put("key2", "map");
        m2.put("key1", "Hello");
        m2.put("key2", "map");
        boolean expectedResult = true;
        boolean actualResult = cm.compareMaps(m1,m2);
        assertEquals(expectedResult,actualResult);
    }
}