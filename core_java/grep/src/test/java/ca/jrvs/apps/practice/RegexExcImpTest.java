package ca.jrvs.apps.practice;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class RegexExcImpTest {

    private RegexExc rx;
    @Before
    public void setUp() throws Exception {
        rx = new RegexExcImp();
    }

    @After
    public void tearDown() throws Exception {
        rx = null;
    }

    @Test
    public void matchJpeg() {
        boolean expectedResult = true;
        boolean actualResult = rx.matchJpeg("image.jpeg");
        assertEquals(expectedResult,actualResult);
    }

    @Test
    public void matchIp() {
        boolean expectedResult = true;
        boolean actualResult = rx.matchIp("111.22.33.44");
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void isEmptyLine() {
        boolean expectedResult = true;
        boolean actualResult = rx.isEmptyLine("             ");
        assertEquals(expectedResult,actualResult);
    }
}