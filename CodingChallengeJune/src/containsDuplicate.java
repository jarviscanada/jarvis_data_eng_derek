import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class containsDuplicate {
    public static void main(String[] args) {
        containsDuplicate cd = new containsDuplicate();
        System.out.println(cd.containsDuplicate(new int[]{1,3,4,2,5}));
    }

    public boolean containsDuplicate(int[] nums) {
        //method 1
        /*Set<Integer> set = new HashSet<Integer>();
        for(int num:nums){
            if(!set.add(num)){
                return true;
            }
        }return false;
    }*/
        //method 2
        Set<Integer> setTwo = new HashSet<Integer>();
        for (int num : nums) {
            if (setTwo.contains(num)) return true;
            setTwo.add(num);
        }
        return false;
    }
       //method 3
    /*    Arrays.sort(nums);
        for(int i=1;i<nums.length;i++){
            if(nums[i]==nums[i-1]) return true;
        }return false;}*/

}
