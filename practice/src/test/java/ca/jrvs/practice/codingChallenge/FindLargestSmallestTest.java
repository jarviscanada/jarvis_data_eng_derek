package ca.jrvs.practice.codingChallenge;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FindLargestSmallestTest {
    private FindLargestSmallest fls;
    @Before
    public void setUp() throws Exception {
        fls = new FindLargestSmallest();
    }

    @Test
    public void findLargestSmallest() {
        int[] arr = {2,1,7,4};
        int[] expected = {7,1};
        int[] actual = fls.findLargestSmallest(arr);
        assertArrayEquals(expected,actual);
    }
}