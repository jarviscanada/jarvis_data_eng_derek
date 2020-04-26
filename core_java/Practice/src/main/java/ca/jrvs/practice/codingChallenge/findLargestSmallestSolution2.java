package ca.jrvs.practice.codingChallenge;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class findLargestSmallestSolution2 {
    public static void main(String[] args) {
        findLargestSmallestSolution2 solution = new findLargestSmallestSolution2();
        System.out.println(Arrays.toString(solution.findLargestSmallest2(new int[]{-1,3,11,2,5})));

    }
//    public int[] findLargestSmallest(int[] nums){
//       Arrays.sort(nums);
//      return new int[]{nums[0],nums[nums.length-1]};
//
//    }
    public int[] findLargestSmallest2(int[] nums){
        List<Integer> list = IntStream.of(nums).boxed().collect(Collectors.toList());
//        Integer i = (Integer) Collections.max(list,null);
//        int j = i.intValue();
        Collections.sort(list);
        int l = list.get(nums.length-1);
        int k = list.get(0);
        return new int[]{l,k};
    }
}
