package ca.jrvs.practice.codingChallenge;

import java.util.Stack;

public class ImplementQueueUsingStack {
    Stack<Integer> s1 = new Stack<>();
    Stack<Integer> s2 = new Stack<>();
    private int front;

    public static void main(String[] args) {
        ImplementQueueUsingStack queue = new ImplementQueueUsingStack();
        System.out.println(queue.empty());
        queue.push(1);
        queue.push(2);
        queue.push(3);
        System.out.println(queue.empty());
        System.out.println(queue.peek());
        System.out.println(queue.pop());
        System.out.println(queue.peek());
        System.out.println(queue.pop());
        System.out.println(queue.peek());
        System.out.println(queue.pop());
    }
    /**
     * Two stacks
     * push: O(n)
     * pop: O(1)
     *
     * @param

    public void push(int x) {
    if (s1.empty()) front = x;
    while (!s1.isEmpty()) {
    s2.push(s1.pop());
    }
    s2.push(x);
    while (!s2.isEmpty()) {
    s1.push(s2.pop());
    }
    }

    public int pop() {
    front = s1.pop();
    return front;
    }

    public int peek() {
    return s1.peek();
    }

    public boolean empty() {
    return s1.isEmpty();
    }
     */
    /**
     * Two stacks
     * push O(1)
     * pop amortized O(1)
     */
    public void push(int x) {
        if (s1.empty()) front = x;
        s1.push(x);
    }

    public int pop() {
        if (s2.isEmpty()) {
            while (!s1.isEmpty()) {
                s2.push(s1.pop());
            }
              } return s2.pop();
    }
        public int peek () {
            if(!s2.isEmpty()){
                return s2.peek();
            }
            return front;
        }

        public boolean empty () {
            return s1.isEmpty() && s2.isEmpty();
        }
    }
