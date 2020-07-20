package ca.jrvs.practice.codingChallenge;

public class FibonacciNumber {
    /**
     * Approach 1: recursive
     * O(2^n)
     * @param n
     * @return
     */
//    public int fibonacciNumber(int n) {
//              if (n <= 1) return n;
//       return fibonacciNumber(n - 2) + fibonacciNumber(n - 1);
//    }
    /**
     * Apporach 2: Dynamic programming
     * O()
     */
    public int fibonacciNumber(int n){
        int[] arr = new int[n+1];
        int result = 0;
        arr[0] = 0;
        arr[1] = 1;
        if (n <= 1) {
            return arr[n];
        }
        for (int i = 2; i <=n; i++) {
            arr[i] = arr[i - 1] + arr[i - 2];
            result = arr[i];
        }
        return result;
    }
}
