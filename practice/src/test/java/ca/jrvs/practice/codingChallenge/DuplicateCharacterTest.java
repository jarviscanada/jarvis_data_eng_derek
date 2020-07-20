package ca.jrvs.practice.codingChallenge;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class DuplicateCharacterTest {
    private DuplicateCharacter dc;

    @Before
    public void setUp() throws Exception {
        dc = new DuplicateCharacter();
    }

    @Test
    public void duplicateCharacter() {
        String s = "A black cat wat";
        Set expected = new HashSet();
        expected.add("a");
        expected.add("c");
        expected.add("t");
        Set actual = dc.duplicateCharacter(s);
        assertEquals(expected, actual);
    }
}