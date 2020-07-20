package ca.jrvs.practice.codingChallenge;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrintLetterWithNumberTest {
    private PrintLetterWithNumber pl;
    @Before
    public void setUp() throws Exception {
        pl = new PrintLetterWithNumber();
    }

    @Test
    public void printLetterNumber() {
        String s = "abceeAB";
        String expected = "a1b2c3e5e5A27B28";
        String actual = pl.printLetterNumber(s);
        assertEquals(expected,actual);
    }
}