package ca.jrvs.practice.codingChallenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class FindLargestSmallest {
    public int[] findLargestSmallest(int[] arr) {
        /**
         * Approach 1: exact one for loop
         * O(n)
         */
//       int min = arr[0];
//       int max = arr[0];
//       for(int i=1;i<arr.length;i++){
//            if(arr[i]>max) {
//                max = arr[i];
//            } if(arr[i]<min)
//                min = arr[i];
//        }
//       int[] out = {max,min};
//       return out;

        /**
         * Approach 2:Stream API
         *O(1)
         */
//        int max = IntStream.of(arr).max().getAsInt();
//        int min = IntStream.of(arr).min().getAsInt();
//        int[] out = {max, min};
//        return out;
        /**
         * Approach 3: Java built-in API
         * O()
         */
        ArrayList<Integer> list = new ArrayList<>();
        for(int i:arr){
            list.add(i);
        }
        int max = Collections.max(list);
        int min = Collections.min(list);
        return new int[]{max,min};
    }
}
