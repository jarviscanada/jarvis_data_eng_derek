package ca.jrvs.practice.codingChallenge;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class FibonacciNumberTest {
    FibonacciNumber fn;
    @Before
    public void setUp() throws Exception {
        fn = new FibonacciNumber();
    }

    @Test
    public void fibonacciNumber() {
        int n = 6;
        int expected = 8;
        int actual = fn.fibonacciNumber(n);
        assertEquals(expected,actual);
    }
}