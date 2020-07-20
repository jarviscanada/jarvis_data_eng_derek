import java.util.HashSet;
import java.util.Set;

public class removeDuplicatesFromSortedArray {
    public Set removeDuplicates(int[] nums){
        Set set = new HashSet();
        /*for(int i=0;i<nums.length;i++){
            set.add(nums[i]);
        }*/
        for(int num:nums){
            set.add(num);
        }
        return set;
    }

    public static void main(String[] args) {
        removeDuplicatesFromSortedArray rd = new removeDuplicatesFromSortedArray();
        System.out.println(rd.removeDuplicates(new int[]{0,0,1,1,2}));
    }
}
