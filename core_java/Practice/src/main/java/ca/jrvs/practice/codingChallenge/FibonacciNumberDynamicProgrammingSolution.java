package ca.jrvs.practice.codingChallenge;

import java.lang.reflect.Array;

public class FibonacciNumberDynamicProgrammingSolution {
    public static void main(String[] args) {
        FibonacciNumberDynamicProgrammingSolution solution = new FibonacciNumberDynamicProgrammingSolution();
        System.out.println(solution.fib(6));
    }

    //    public int fib(int N) {
//        if (N <= 1) {
//            return N;
//        }
//        int fib = 1;
//        int previousFib = 1;
//        for (int i = 2; i < N; i++) {
//            int temp = fib;
//            fib += previousFib;
//            previousFib = temp;
//        }
//        return fib;
//    }
    public int fib(int N) {
        int[] arr = new int[N];
        int result = 0;
        arr[0] = 1;
        arr[1] = 1;
        if (N <= 1) {
            return arr[N];
        }
        for (int i = 2; i <N; i++) {
            arr[i] = arr[i - 1] + arr[i - 2];
            result = arr[i];
        }
        return result;
    }
}
