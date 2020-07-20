package ca.jrvs.practice.codingChallenge;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

public class ContainsDuplicate {
    /**
     * O(n)
     * @param nums
     * @return
     */
    /*public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < nums.length - 1; i++) {
            set.add(nums[i]);
        }
        if (set.size() <= nums.length) {
            return true;
        } else return false;
    }*/

    /**
     * O(n)
     * @param nums
     * @return
     */
/*    public boolean containsDuplicate(int[] nums){
        Set<Integer> set = new HashSet<>();
        for(int num: nums){
            if(set.contains(num))
                return true;
            else set.add(num);
        } return false;
    }*/

    /**
     * O(n)
     * @param nums
     * @return
     */
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (!set.add(num)) {
                return true;
            }
            //set.add(num);
        }
        return false;
    }
    /**
     * O(n^2)
     */
/*    public boolean containsDuplicate(int[] nums){
        for(int i = 1; i<nums.length;i++){
            for(int j = i - 1;j<nums.length; j++){
                if(nums[i]==nums[j]){
                    return true;
                }
            }
        }return false;
    }*/
     /**
     *O(nlogn)
     */
  /*  public boolean containsDuplicate(int[] nums){
        Arrays.sort(nums);
        for(int i=1;i<nums.length;i++){
            if(nums[i-1]==nums[i])
                return true;
        } return false;
    }*/
     public static void main(String[] args) {
         ContainsDuplicate cd = new ContainsDuplicate();
         System.out.println(cd.containsDuplicate(new int[]{1,2,3,1}));
     }
}
