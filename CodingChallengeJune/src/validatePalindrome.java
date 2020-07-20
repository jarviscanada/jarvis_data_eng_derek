public class validatePalindrome {
    public static void main(String[] args) {
        validatePalindrome vp = new validatePalindrome();
        System.out.println(vp.isPalindrome("A man, a plan, a canal: Panama"));
    }

    public boolean isPalindrome(String s) {
        //method 1 two pointer
        String sLetterOnly = s.replaceAll("[\\W]", "").toLowerCase();
        for (int i = 0; i < sLetterOnly.length() / 2; i++) {
            if (sLetterOnly.charAt(i) != sLetterOnly.charAt(sLetterOnly.length() - 1 - i)) return false;
        }
        return true;
    }
}
//method 2 recursion
       /* String sLetterOnly = s.replaceAll("[\\W]","").toLowerCase();
        return validSubPalindrome(sLetterOnly, 0, sLetterOnly.length() - 1, false);
    }

    private boolean validSubPalindrome(String s, int lo, int hi, boolean used) {
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
*/
