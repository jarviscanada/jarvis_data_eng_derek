package ca.jrvs.practice.codingChallenge;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidAnagramTest {
    ValidAnagram va;
    @Before
    public void setUp() throws Exception {
        va = new ValidAnagram();
    }

    @Test
    public void validAnagram() {
        String s = "anagram";
        String t = "ngaarma";
              boolean actual = va.validAnagram(s,t);
       assertTrue(actual);
    }
}