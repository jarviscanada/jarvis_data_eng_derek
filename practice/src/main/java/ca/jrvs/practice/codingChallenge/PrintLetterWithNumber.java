package ca.jrvs.practice.codingChallenge;

public class PrintLetterWithNumber {
    /**
     * O(n)
     * @param s
     * @return
     */
    public String printLetterNumber(String s) {
        char[] ch = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        int ascii;
        for (char c : ch) {
            sb.append(c);
            if (Character.isLowerCase(c)) {
                ascii = (int) c - 96;
            } else ascii = (int) c - 38;
            sb.append(ascii);
        }
        return sb.toString();
    }
}
