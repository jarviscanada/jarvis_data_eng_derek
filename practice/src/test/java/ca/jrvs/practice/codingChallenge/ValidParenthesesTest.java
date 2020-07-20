package ca.jrvs.practice.codingChallenge;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidParenthesesTest {
    ValidParentheses vp;
    @Before
    public void setUp() throws Exception {
        vp = new ValidParentheses();
    }

    @Test
    public void validParentheses() {
        String s = "([])";
        boolean actual = vp.validParentheses(s);
        assertEquals(true,actual);
    }
}