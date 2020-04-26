package ca.jrvs.practice.codingChallenge;

import java.io.UnsupportedEncodingException;

public class stringToIntegerSolution2 {
    public static void main(String[] args) throws UnsupportedEncodingException {
        stringToIntegerSolution2 solution = new stringToIntegerSolution2();
        System.out.println(solution.myAtoi("0123"));
    }

    public int myAtoi(String str) {

        String str1 = str.trim();
        char[] c = str1.toCharArray();
        int result = 0;

        if ((int) c[0] == 45 | (int) c[0] == 43 | (int) c[0] >= 48 && (int) c[0] <= 57) {
            int i = 1;
            while ((int) c[i] >= 48 && (int) c[i] <= 57) {
                i++;
            }
            String str2 = str1.substring(0, i);
            try {
                result = Integer.parseInt(str2);
            } catch (Exception e) {
                if ((int) c[0] == 45) {
                    result = Integer.MIN_VALUE;
                } else return result = Integer.MAX_VALUE;
            }
        }
        return result;
    }
}

