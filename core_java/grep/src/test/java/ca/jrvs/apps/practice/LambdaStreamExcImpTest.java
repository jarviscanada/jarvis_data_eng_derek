package ca.jrvs.apps.practice;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class LambdaStreamExcImpTest {

    private LambdaStreamExc lse;

    @Before
    public void setUp() throws Exception {
        lse = new LambdaStreamExcImp();
    }

    @After
    public void tearDown() throws Exception {
        lse = null;
    }

    @Test
    public void createStrStream() {
        List<String> expectedResult = Arrays.asList("s1", "s2", "s3");
        List<String> actualResult = lse.createStrStream("s1", "s2", "s3").collect(Collectors.toList());
        assertEquals(expectedResult, actualResult);

    }

    @Test
    public void toUpperCase() {
        List<String> expectedResult = Arrays.asList("ABC", "DEF", "GHI");
        List<String> realResult = lse.toUpperCase("abc", "dEf", "Ghi").collect(Collectors.toList());
        assertEquals(expectedResult, realResult);
    }

    @Test
    public void filter() {
        List<String> expectedResult = Arrays.asList("bc", "def", "ghi");
        List<String> actualResult = lse.filter(lse.createStrStream("abc", "bc", "def", "ghia", "ghi"), "a").collect(Collectors.toList());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void createInStream() {
        List<Integer> expectedResult = Arrays.asList(123, 22, 3, 42, 5);
        List<Integer> actualResult = lse.createInStream(new int[]{123, 22, 3, 42, 5}).boxed().collect(Collectors.toList());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void toList() {
        /**
         * Both toList<Stream<E> stream> and toList<IntStream intStream> are tested as following:
         */
        /* List<String> expectedResult = Arrays.asList("hello", "test");
           List<String> actualResult = lse.toList(Stream.of("hello","test"));
           assertEquals(expectedResult,actualResult);*/
        List<Integer> expectedResult = Arrays.asList(12, 22, 3, 4);
        List<Integer> actualResult = lse.toList(Arrays.stream(new int[]{12, 22, 3, 4}));
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void createIntStream() {
        List<Integer> expectedResult = Arrays.asList(11,12,13,14,15);
        List<Integer> actualResult = lse.createIntStream(11,15).boxed().collect(Collectors.toList());
        assertEquals(expectedResult,actualResult);
    }

    @Test
    public void squareRootIntStream() {
        List<Double> expectedResult = Arrays.asList(1.0,2.0,3.0,4.0,5.0);
        List<Double> actualResult = lse.squareRootIntStream(Arrays.stream(new int[] {1,4,9,16,25})).boxed().collect(Collectors.toList());
        assertEquals(expectedResult,actualResult);
    }

    @Test
    public void getOdd() {
        List<Integer> expectedResult = Arrays.asList(1,3,5,7,9);
        List<Integer> actualResult = lse.getOdd(Arrays.stream(new int[] {1,2,3,4,5,6,7,8,9})).boxed().collect(Collectors.toList());
        assertEquals(expectedResult,actualResult);
    }

    @Test
    public void getLambdaPrinter() {
       Consumer<String> actualResult = lse.getLambdaPrinter("start>", "<end");
       actualResult.accept("Message body");
    }

    @Test
    public void printMessage() {
        String[] message = {"a","b","c"};
        Consumer<String> printer = lse.getLambdaPrinter("Letter: ", "!");
        lse.printMessage(message, printer);
    }

    @Test
    public void printOdd() {
        Consumer<String>  printer = lse.getLambdaPrinter("Odd number:", "!");
        lse.printOdd(lse.createInStream(new int[] {1,2,3,4,5}),printer);
    }

    @Test
    public void flatNestedInt() {
        List<Integer> expectedResult = Arrays.asList(1,4,9,16,25);
        List<Integer> list = Arrays.asList(1,2,3,4,5);
        List<Integer> actualResult = lse.flatNestedInt(Stream.of(list)).collect(Collectors.toList());
        assertEquals(expectedResult,actualResult);
    }
}
