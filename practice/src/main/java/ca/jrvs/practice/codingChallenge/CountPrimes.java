package ca.jrvs.practice.codingChallenge;

import java.math.BigInteger;

public class CountPrimes {
    public int countPrimes(int n) {
//        int count = 0;
//        if (n <= 1) return 0;
//        for (int i = 2; i <= n; i++) {
//            int flag = 1;
//            for (int j = 2; j <= i / 2; j++) {
//                if (i % j == 0) {
//                    flag = 0;
//                    break;
//                }
//            }
//            if (flag == 1) {
//                count++;
//            }
//        }
//        return count++;
//    }
        if (n <= 1) return 0;
        int count = 0;
        for (int i = 2; i <= n; i++) {
            BigInteger b = new BigInteger(String.valueOf(i));
            if (b.isProbablePrime(1)) count++;
        }
        return count;
    }
}