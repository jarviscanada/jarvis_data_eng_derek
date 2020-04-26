package ca.jrvs.practice.codingChallenge;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class twoSumSolution3Test {
    private twoSumSolution3 solution;
    @Before
    public void setUp() throws Exception {
    solution = new twoSumSolution3();
    }
    @Test
    public void twoSum() {
        int[] expectedResult = {0,3};
        int[] actualResult = solution.twoSum(new int[]{1, 2, 5, 8}, 9);
        assertArrayEquals(expectedResult,actualResult);
    }
}