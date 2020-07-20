package ca.jrvs.practice.codingChallenge;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SwapTwoNumbersTest {
    SwapTwoNumbers stn;
    @Before
    public void setUp() throws Exception {
        stn = new SwapTwoNumbers();
    }

    @Test
    public void swapTwoNumbers() {
        int[] nums = {2,3};
        int[] expected = {3,2};
        int[] actual = stn.swapTwoNumbers(nums);
        assertArrayEquals(expected,actual);
    }
}