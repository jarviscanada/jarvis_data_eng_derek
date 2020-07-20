package ca.jrvs.practice.codingChallenge;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ContainsDuplicateTest {
    private ContainsDuplicate cd;

    @Before
    public void setUp() throws Exception {
        cd = new ContainsDuplicate();
    }

    @Test
    public void containsDuplicate() {
        boolean expected = false;
        int[] nums = {1, 2, 3, 11};
        boolean actual = cd.containsDuplicate(nums);
        assertEquals(expected, actual);
    }
}