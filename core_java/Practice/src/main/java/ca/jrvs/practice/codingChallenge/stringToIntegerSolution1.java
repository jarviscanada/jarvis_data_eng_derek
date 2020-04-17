package ca.jrvs.practice.codingChallenge;

public class stringToIntegerSolution1 {
    public static void main(String[] args) {
        stringToIntegerSolution1 solution = new stringToIntegerSolution1();
        System.out.println(solution.myAtoi("   03333333333333333333333333 k55"));
    }

    public int myAtoi(String str) {
        String str1 = str.trim();
        char[] c = str1.toCharArray();
        int result = 0;
        if (c[0] == '-' | c[0] == '+' | Character.isDigit(c[0])) {
            int i = 1;
            while (Character.isDigit(c[i])) {
                i++;
            }
            String str2 = str1.substring(0, i);

            try {
                result = Integer.parseInt(str2);
            } catch (Exception e) {
                if (str2.charAt(0) == '-') {
                    result = Integer.MIN_VALUE;
                } else return result = Integer.MAX_VALUE;
            }
            /* This way does not work
                if (result < Integer.MIN_VALUE) {
                  result = Integer.MIN_VALUE;
               } else  {
                  result = Integer.MAX_VALUE;
               }*/
        }
        return result;
    }
}
