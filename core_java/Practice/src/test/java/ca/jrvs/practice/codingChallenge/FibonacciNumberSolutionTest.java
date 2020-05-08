package ca.jrvs.practice.codingChallenge;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FibonacciNumberSolutionTest {

    private FibonacciNumberSolution solution;
    @Before
    public void setUp() throws Exception {
       solution = new FibonacciNumberSolution();
    }

    @Test
    public void fib() {
        int expectedResult = 8;
        int actualResult = solution.fib(6);
        assertEquals(expectedResult,actualResult);
    }
}