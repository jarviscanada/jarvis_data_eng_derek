package ca.jrvs.practice.codingChallenge;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RotateStringTest {
    private RotateString rs;

    @Before
    public void setUp() throws Exception {
        rs = new RotateString();
    }

    @Test
    public void rotateString() {
        String A = "abcde";
        String B = "cdeab";
        Boolean actual = rs.rotateString(A, B);
        assertTrue(actual);
    }
}