package ca.jrvs.practice.codingChallenge;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MissingNumberTest {
    private MissingNumber mn;
    @Before
    public void setUp() throws Exception {
        mn = new MissingNumber();
    }

    @Test
    public void missingNumber() {
        int[] nums = {9,8,7,6,5,4,3,0,1};
        int expected = 2;
        int actual = mn.missingNumber(nums);
        assertEquals(expected,actual);
    }
}