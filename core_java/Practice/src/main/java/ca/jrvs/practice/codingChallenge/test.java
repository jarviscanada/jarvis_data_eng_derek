package ca.jrvs.practice.codingChallenge;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class test {
    public static void main(String[] args) {
        System.out.println(IntStream.range(1,10).filter(i->i%2==0).boxed().collect(Collectors.toList()));
    }
}
