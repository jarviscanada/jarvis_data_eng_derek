package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;

public class MissingNumber {
    /**
     * O(nlogn)
     * @param nums
     * @return
     */
    public int missingNumber(int[] nums){
        Arrays.sort(nums);
        for(int i=1;i<nums.length;i++){
            if(nums[i]-nums[i-1] !=1){
                return nums[i] -1;
            }
        } return -1;
    }
}
