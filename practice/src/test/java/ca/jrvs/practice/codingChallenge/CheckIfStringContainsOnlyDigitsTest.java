package ca.jrvs.practice.codingChallenge;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CheckIfStringContainsOnlyDigitsTest {
    CheckIfStringContainsOnlyDigits cs;
    @Before
    public void setUp() throws Exception {
        cs = new CheckIfStringContainsOnlyDigits();
    }

    @Test
    public void checkOnlyDigits() {
        String s = "123000";
        Boolean expected = true;
        Boolean actual = cs.checkOnlyDigits(s);
        assertEquals(expected,actual);
    }
}