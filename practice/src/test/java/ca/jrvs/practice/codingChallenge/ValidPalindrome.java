package ca.jrvs.practice.codingChallenge;

public class ValidPalindrome {
    public Boolean validatePalindrome(String s) {
        /**
         * Approach 1: two pointers
         * O(n)
         * @param s
         * @return
         */
/*     if (s.isEmpty()) return true;
       String str = s.replaceAll("\\W","").toLowerCase();
        for(int i=0;i<str.length()/2;i++){
            if(str.charAt(i) != str.charAt(str.length()-i -1))
                return false;
        }return true;
    }*/
        /**
         * Approach 2: Recursion
         * O(n)
         */
        String sLetterOnly = s.replaceAll("[\\W]", "").toLowerCase();
        return validSubPalindrome(sLetterOnly, 0, sLetterOnly.length() - 1, false);
    }

    private Boolean validSubPalindrome(String s, int lo, int hi, boolean used) {
        if (lo >= hi) {
            return true;
        }

        if (s.charAt(lo) != s.charAt(hi)) {
            if (used == false) {
                if (validSubPalindrome(s, lo + 1, hi, true)) return true;
                return validSubPalindrome(s, lo, hi - 1, true);
            } else {
                return false;
            }
        }
        return validSubPalindrome(s, lo + 1, hi - 1, used);
    }
}

