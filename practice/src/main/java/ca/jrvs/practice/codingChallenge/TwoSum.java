package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSum {
    /**
     * Approach 1: two loops
     * O(n^2)
     * @param arr
     * @param target
     * @return
     */
//    public int[] twoSum(int[] arr, int target){
//        for(int i=0;i<arr.length;i++){
//            for(int j=i+1;j<arr.length;j++){
//                if (arr[i] + arr[j] == target){
//                    return new int[]{arr[i],arr[j]};
//                }
//            }
//        } return new int[]{};
//    }

    /**
     * Approach 2: Using Java built-in API, such as sorting
     * O()
     * @param arr
     * @param target
     * @return
     */
//    public int[] twoSum(int[] arr, int target){
//        int i = 0;
//        int j = arr.length -1;
//        Arrays.sort(arr);
//        while(arr[i] + arr[j] !=target){
//            if(arr[i] + arr[j] > target) j--;
//            else i++;
//        } return new int[] {arr[i], arr[j]};
//    }

    /**
     * Approach 3: one loop
     * O(n)
     * @param arr
     * @param target
     * @return
     */
    public int[] twoSum(int[] arr, int target){
        Map<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<arr.length;i++){
            map.put(arr[i],i);
            if(map.containsKey(target-arr[i]))
                return new int[]{arr[i], target - arr[i]};
        } throw new IllegalArgumentException("Cannot find two sums.");
    }
}
