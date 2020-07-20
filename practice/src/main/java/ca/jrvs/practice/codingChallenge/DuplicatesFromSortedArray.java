package ca.jrvs.practice.codingChallenge;

public class DuplicatesFromSortedArray {
    /**
     * Two pointers
     * O(n)
     */
    public int duplicatesFromSortedArray(int[] nums) {
        if(nums.length==0) return 0;
        int j = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] != nums[i]) {
               j++;
               nums[j] = nums[i];
            }
        }
        for (int i = 0; i < nums.length; i++) System.out.println(nums[i]);
        return j + 1;
    }
}
