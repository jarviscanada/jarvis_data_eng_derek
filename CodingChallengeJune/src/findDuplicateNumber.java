import java.util.HashSet;
import java.util.Set;

public class findDuplicateNumber {
    public static void main(String[] args) {
        findDuplicateNumber fdn = new findDuplicateNumber();
        System.out.println(fdn.findDuplicate(new int[]{1, 3, 4, 3, 2}));
    }

    //method 1
    public int findDuplicate(int[] nums) {
        Set set = new HashSet();
        int duplicateNumber = 0;
        for (int num : nums) {
            if (!set.add(num)) {
                return duplicateNumber = num;
            }
        }
        return -1;
    }
/*    public int findDuplicate(int[] nums) {
        Set<Integer> set2 = new HashSet<Integer>();
        for (int num : nums) {
            if (set2.contains(num)) {
                return num;
            }
            set2.add(num);
        }
        return -1;
    }*/
}
