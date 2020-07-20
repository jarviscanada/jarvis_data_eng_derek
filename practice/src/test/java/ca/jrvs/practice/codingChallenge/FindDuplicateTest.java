package ca.jrvs.practice.codingChallenge;

import org.junit.Before;
import org.junit.Test;

import java.awt.peer.TextAreaPeer;

import static org.junit.Assert.*;

public class FindDuplicateTest {
    private FindDuplicate fd;
    @Before
    public void setUp() throws Exception {
        fd = new FindDuplicate();
    }

    @Test
    public void findDuplicate() {
        int[] nums = {1,3,4,2,2};
        int expected = 2;
        int actual = fd.findDuplicate(nums);
        assertEquals(expected,actual);
    }
}