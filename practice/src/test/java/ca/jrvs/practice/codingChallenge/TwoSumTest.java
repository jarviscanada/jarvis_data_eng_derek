package ca.jrvs.practice.codingChallenge;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class TwoSumTest {
    TwoSum ts;

    @Before
    public void setUp() throws Exception {
        ts = new TwoSum();
    }

    @Test
    public void twoSum() {
        int[] arr = {11, 13, 7, 2};
        int target = 9;
        int[] expected = {2, 7};
        int[] actual = ts.twoSum(arr, target);
        assertArrayEquals(expected, actual);
    }
}