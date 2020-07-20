package ca.jrvs.practice.codingChallenge;

import java.util.HashSet;
import java.util.Set;

public class FindDuplicate {
    /**
     * O(n)
     * @param nums
     * @return
     */
   /* public int findDuplicate(int[] nums){
        Set<Integer> set = new HashSet<>();
        for(int num:nums){
            if(!set.add(num)){
                return num;
            }
        } return -1;
    }*/

   public int findDuplicate(int[] nums){
       Set<Integer> set = new HashSet<>();
       for(int num:nums){
           if(set.contains(num))
               return num;
           set.add(num);
       } return -1;
   }

}
