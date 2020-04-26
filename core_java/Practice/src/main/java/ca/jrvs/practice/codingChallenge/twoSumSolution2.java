package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;

public class twoSumSolution2 {
    public int[] twoSum(int[] nums, int target){
        int[] arr = new int[0];
        int i = 0;
        int j = nums.length - 1;
        while(nums[i] + nums[j] != target){
            if(nums[i] + nums[j] > target)
                j--;
            else i ++;
        }
        return arr = new int[] {i,j};
    }
    public static void main(String[] args) {
        twoSumSolution2 solution = new twoSumSolution2();
        System.out.println(Arrays.toString(solution.twoSum(new int[]{1, 2, 5, 8, 9}, 7)));
    }
}
