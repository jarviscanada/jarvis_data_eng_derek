package ca.jrvs.practice.codingChallenge;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckIfStringContainsOnlyDigits {
    public boolean checkOnlyDigits(String s){
        //char[] ch = s.toCharArray();
        //int ascii;
        //for(char c:ch){
            //Character.isDigit()
            //if(!Character.isDigit(c)) return false;

            //ascii
            //ascii = (int) c;
            //if(ascii<48 || ascii>57) return false;

        //} return true;

        //regex
        /**
         * O(m), where m is length of string.
         */
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher matcher = pattern.matcher(s);
        //boolean matches = matcher.matches();
        return matcher.matches();
    }
}
