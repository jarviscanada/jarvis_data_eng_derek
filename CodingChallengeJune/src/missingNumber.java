import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class missingNumber {
    public int missingNumber(int[] nums){
        Set<Integer> set = new HashSet<Integer>();
        for(int num:nums){
            set.add(num);
        }
        int exptectedNumCount = nums.length +1;
        Set<Integer> setTwo = new HashSet<Integer>();
        for(int i=0;i<exptectedNumCount;i++){
            setTwo.add(i);
        }
        setTwo.removeAll(set);
        Iterator<Integer> iter = setTwo.iterator();
        return iter.next().intValue();

    }

    public static void main(String[] args) {
         missingNumber mn = new missingNumber();
        System.out.println(mn.missingNumber(new int[]{3,0,1,2,5,6,7,4,9}));
    }
}
