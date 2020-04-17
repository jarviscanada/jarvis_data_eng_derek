package ca.jrvs.practice.codingChallenge;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FibonacciNumberDynamicProgrammingSolutionTest {

    private FibonacciNumberDynamicProgrammingSolution solution;
    @Before
    public void setUp() throws Exception {
        solution = new FibonacciNumberDynamicProgrammingSolution();
    }

    @Test
    public void fib() {
        int expectedResult = 13;
        int actualResult = solution.fib(7);
        assertEquals(expectedResult,actualResult);
    }
}