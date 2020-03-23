package ca.jrvs.apps.practice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexExcImp implements RegexExc {
    public boolean matchJpeg(String filename) {
        Pattern pattern = Pattern.compile("(.*\\.(jpg|jpeg))$");
        Matcher matcher = pattern.matcher(filename);
        if (matcher.matches()) {
            return true;
        } else return false;
    }

    public boolean matchIp(String ip) {
        Pattern pattern = Pattern.compile("([0-9]{1,3}\\.){3}([0-9]{1,3})");
        Matcher matcher = pattern.matcher(ip);
        if (matcher.matches()) {
            return true;
        } else return false;
    }

    public boolean isEmptyLine(String line) {
        Pattern pattern = Pattern.compile("^(\\t)*|(\\s)*$");
        Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
            return true;
        } else return false;
    }
}
