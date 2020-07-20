package ca.jrvs.practice.codingChallenge;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringToIntegerTest {
    StringToInteger sti;

    @Before
    public void setUp() throws Exception {
        sti = new StringToInteger();
    }

    @Test
    public void stringToInteger() {
        String s = "  aa  -230 aa ";
        int expected = -230;
        int actual = sti.stringToInteger(s);
        assertEquals(expected, actual);
    }
}