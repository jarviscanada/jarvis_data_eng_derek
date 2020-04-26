package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;

public class twoSumSolution1 {
    public int[] twoSum(int[] nums, int target){
       int[] arr = new int[0];
        for(int i=0;i<nums.length;i++){
            for(int j=i+1;j<nums.length;j++){
                //if(i==j){continue;}
                if (nums[j]==target - nums[i]){
                    return arr = new int[]{i, j};
                }
            }
        }
       // return arr;
        throw new IllegalArgumentException("no two sum solution");
      }

    public static void main(String[] args) {
        twoSumSolution1 solution = new twoSumSolution1();
        System.out.println(Arrays.toString(solution.twoSum(new int[]{1, 3, 5, 8}, 4)));
    }
}
