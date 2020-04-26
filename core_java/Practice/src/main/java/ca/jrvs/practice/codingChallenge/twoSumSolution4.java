package ca.jrvs.practice.codingChallenge;

import ca.jrvs.practice.dataStructure.map.HashJMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class twoSumSolution4 {
    public static void main(String[] args) {
        twoSumSolution4 solution = new twoSumSolution4();
        System.out.println(Arrays.toString(solution.twoSum(new int[]{1, 2, 3, 8}, 9)));
    }

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            int end = target - nums[i];
            map.put(nums[i], i);
            if (map.containsKey(end)) {
                return new int[]{i, map.get(end)};
            }
        }
        throw new IllegalArgumentException("No two sum solutions");
    }
}