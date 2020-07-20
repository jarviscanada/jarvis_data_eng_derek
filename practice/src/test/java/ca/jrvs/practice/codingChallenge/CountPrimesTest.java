package ca.jrvs.practice.codingChallenge;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CountPrimesTest {
    CountPrimes cp;
    @Before
    public void setUp() throws Exception {
        cp = new CountPrimes();
    }

    @Test
    public void countPrimes() {
        int expected = 4;
        int actual = cp.countPrimes(10);
        assertEquals(expected,actual);
    }
}