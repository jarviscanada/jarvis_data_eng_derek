package ca.jrvs.practice.codingChallenge;

public class RemoveElement {
    /**
     * Two pointers
     * O(n)
     * @param nums
     * @param val
     * @return
     */
    public int removeElement(int[] nums, int val) {
        int j = 0;
        for (int i = 0;i<nums.length;i++){
            if(nums[i]!=val){
                nums[j] = nums[i];
                j++;
            }
        }return j;
    }
}
