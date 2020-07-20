package ca.jrvs.practice.codingChallenge;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidPalindromeTest {
    ValidPalindrome vp;
    @Before
    public void setUp() throws Exception {
        vp = new ValidPalindrome();
    }

    @Test
    public void validatePalindrome() {
        //String s = "A man, a Plan, a canal: Panama";
        //String s = "";
        String s = "AC b  ca";
        Boolean actual = vp.validatePalindrome(s);
        assertTrue(actual);
    }
}