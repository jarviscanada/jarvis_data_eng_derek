package ca.jrvs.practice.codingChallenge;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ImplementStackUsingQueueTest {
    ImplementStackUsingQueue test;

    @Before
    public void setUp() throws Exception {
        test = new ImplementStackUsingQueue();
    }

    @Test
    public void push() {
        test.push(1);
        test.push(2);
        System.out.println(test.top());
    }

    @Test
    public void pop() {
    }

    @Test
    public void top() {
    }

    @Test
    public void empty() {
    }
}