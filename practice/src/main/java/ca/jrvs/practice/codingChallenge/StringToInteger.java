package ca.jrvs.practice.codingChallenge;

public class StringToInteger {
    /**
     * Approach 1: Java Built-in parsing
     * O(1)
     */
//    public int stringToInteger(String s) {
//        String str = s.replaceAll("[a-zA-Z]", "").replaceAll("\\s", "");
//        int output = 0;
//        try {
//            output = Integer.parseInt(str);
//        } catch (Exception e) {
//            if (str.contains("-")) return Integer.MIN_VALUE;
//            else return Integer.MAX_VALUE;
//        }
//        return output;
//    }

    /**
     * Approach 2: ASCII
     */
    public int stringToInteger(String s) {
        String str = s.replaceAll("[a-zA-Z]", "").replaceAll("\\s", "");
        System.out.println(str);
        char[] ch = str.toCharArray();
        int multiplier = 1;
        int total = 0;

        if (ch[0] == 45) {
            try {
                for (int i = ch.length - 1; i > 0; i--) {
                    int c = ch[i] - 48;
                    total = total - c * multiplier;
                    multiplier = multiplier * 10;
                }
            } catch (Exception e) {
                return Integer.MIN_VALUE;
            }
        } else {
            for (int i = ch.length - 1; i >= 0; i--) {
                int c = ch[i] - 48;
                try {
                    total = total + c * multiplier;
                    multiplier = multiplier * 10;
                } catch (Exception e) {
                    return Integer.MAX_VALUE;
                }
            }
        }
        return total;
    }
}
