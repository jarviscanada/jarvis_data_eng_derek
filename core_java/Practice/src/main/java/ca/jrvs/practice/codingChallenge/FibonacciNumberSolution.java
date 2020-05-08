package ca.jrvs.practice.codingChallenge;

public class FibonacciNumberSolution {
    public int fib(int N){
        if(N <=1){return N;}
        return fib(N-1) + fib(N-2);
    }

    public static void main(String[] args) {
        FibonacciNumberSolution solution = new FibonacciNumberSolution();
        System.out.println(solution.fib(30));
    }
}
