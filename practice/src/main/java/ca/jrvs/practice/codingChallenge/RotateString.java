package ca.jrvs.practice.codingChallenge;

public class RotateString {
    /**
     * O(N^2), N is length of A
     * @param A
     * @param B
     * @return
     */
    public boolean rotateString(String A, String B) {
        if (A.length() == B.length() && (A + A).contains(B))
            return true;
        else return false;
    }
}
