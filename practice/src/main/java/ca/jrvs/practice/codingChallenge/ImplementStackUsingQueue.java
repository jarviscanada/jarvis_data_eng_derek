package ca.jrvs.practice.codingChallenge;

import java.util.LinkedList;
import java.util.Queue;

public class ImplementStackUsingQueue {
    Queue<Integer> q1 = new LinkedList<>();
    Queue<Integer> q2 = new LinkedList<>();
    int top;
    Queue<Integer> temp = new LinkedList<>();

    public static void main(String[] args) {
        ImplementStackUsingQueue isuq = new ImplementStackUsingQueue();
        System.out.println(isuq.empty());
        isuq.push(1);
        isuq.push(2);
        isuq.push(3);
        System.out.println(isuq.empty());
        //System.out.println(isuq.top());
        System.out.println(isuq.pop());
        System.out.println(isuq.top());
        System.out.println(isuq.pop());
        System.out.println(isuq.top());

    }

    /**
     * Two Queues
     * push O(1)
     * pop O(n)

     public void push(int x) {
     q1.add(x);
     top = x;
     }

     public int pop() {
     while (q1.size() > 1) {
     q2.add(q1.remove());
     }
     int output = q1.remove();
     Queue<Integer> temp = new LinkedList<>();
     temp = q1;
     q1 = q2;
     q2 = temp;
     return output;
     }

     public int top() {
     while (q1.size() > 1) {
     q1.remove();
     }
     top = q1.peek();
     return top;
     }

     public boolean empty() {
     return q1.isEmpty();
     }
     */

    /**
     * Two Queues
     * push O(n)
     * pop O(1)
     *
     public void push(int x) {
     q2.add(x);
     top = x;
     while (!q1.isEmpty()) {
     q2.add(q1.remove());
     }
     Queue<Integer> temp = q1;
     q1 = q2;
     q2 = temp;
     }

     public int pop() {
     int output = q1.remove();
     return output;
     }

     public int top() {
     if (!q1.isEmpty())
     top = q1.peek();
     return top;
     }

     public boolean empty() {
     return q1.isEmpty();
     }*/

    /**
     * One Queue
     * push: O(n)
     * pop: O(1)
     */
    public void push(int x) {
        q1.add(x);
        int size = q1.size();
        while (size > 1) {
            q1.add(q1.remove());
            size--;
        }
    }

    public int pop() {
        return q1.remove();
    }

    public int top() {
        top = q1.peek();
        return top;
    }

    public boolean empty() {
        return q1.isEmpty();
    }
}
