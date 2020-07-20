package ca.jrvs.practice.codingChallenge;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DuplicatesFromSortedArrayTest {
private DuplicatesFromSortedArray df;
    @Before
    public void setUp() throws Exception {
        df = new DuplicatesFromSortedArray();
    }

    @Test
    public void duplicatesFromSortedArray() {
        int[] nums = {0,0,1,1,1,2,2,3,3,4};
        int expected = 5;
        int actual = df.duplicatesFromSortedArray(nums);
        assertEquals(expected,actual);
    }
}