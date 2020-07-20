package ca.jrvs.practice.codingChallenge;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.*;

public class CompareTwoMapsTest {
    CompareTwoMaps ctm;
    @Before
    public void setUp() throws Exception {
        ctm = new CompareTwoMaps();
    }

    @Test
    public void compareTwoMaps() {
        Map m1 = new HashMap();
        Map m2 = new HashMap();
        m1.put("1",1);
        m1.put("2",2);
        m2.put("1",1);
        m2.put("2",2);
        Boolean actual = ctm.compareTwoMaps(m1,m2);
        assertTrue(actual);
    }
}