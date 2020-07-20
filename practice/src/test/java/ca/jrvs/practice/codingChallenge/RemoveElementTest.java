package ca.jrvs.practice.codingChallenge;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RemoveElementTest {
    RemoveElement re;
    @Before
    public void setUp() throws Exception {
        re = new RemoveElement();
    }

    @Test
    public void removeElement() {
        int expected = 2;
        int actual = re.removeElement(new int[]{3,2,2,3}, 3);
        assertEquals(expected,actual);
    }
}