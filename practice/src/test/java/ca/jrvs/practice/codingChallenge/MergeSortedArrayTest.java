package ca.jrvs.practice.codingChallenge;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MergeSortedArrayTest {
    MergeSortedArray msa;
    @Before
    public void setUp() throws Exception {
        msa = new MergeSortedArray();
    }

    @Test
    public void mergeSortedArray() {
        int[] nums1 = {1,2,3,0,0,0};
        int m = 3;
        int[] nums2 = {2,5,6};
        int n = 3;
        int[] expected = {1,2,2,3,5,6};
        int[] actual = msa.mergeSortedArray(nums1,m,nums2,n);
        assertArrayEquals(expected,actual);
    }
}