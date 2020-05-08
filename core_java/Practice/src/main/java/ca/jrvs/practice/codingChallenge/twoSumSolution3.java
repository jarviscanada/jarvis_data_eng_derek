package ca.jrvs.practice.codingChallenge;

import ca.jrvs.practice.dataStructure.map.HashJMap;
import org.w3c.dom.ls.LSOutput;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class twoSumSolution3 {
    public static void main(String[] args) {
        twoSumSolution3 solution = new twoSumSolution3();
        System.out.println(Arrays.toString(solution.twoSum(new int[]{1, 4, 5, 8}, 9)));
    }

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        for (int j = 0; j < nums.length; j++) {
            int end = target - nums[j];
            if (map.containsKey(end)) {
                return new int[]{j, map.get(end)};
            }
        }
        throw new IllegalArgumentException("No two sum solution");
    }
}
