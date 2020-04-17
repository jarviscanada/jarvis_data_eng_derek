package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;

public class findLargestSmallestSolution1 {
    public static void main(String[] args) {
        findLargestSmallestSolution1 solution = new findLargestSmallestSolution1();
        System.out.println(Arrays.toString(solution.findLargestSmallest(new int[]{2, 0, 3, 4})));
    }

//    public int[] findLargestSmallest(int[] nums) {
//        for (int i = 1; i < nums.length; i++) {
//            for (int j = i; j > 0; j--)
//                if (nums[j] < nums[j - 1]) {
//                    int temp = nums[j];
//                    nums[j] = nums[j - 1];
//                    nums[j - 1] = temp;
//                }
//        }
//        return new int[]{nums[nums.length - 1], nums[0]};
//    }
    public int[] findLargestSmallest(int[] nums){
        int min = nums[0];
        int max = nums[0];
        for(int i=1;i<nums.length;i++){
            if (nums[i] > max){
                max = nums[i];
            }
            if(nums[i] < min){
                min = nums[i];
            }
        }
        return new int[]{max,min};
    }
}

