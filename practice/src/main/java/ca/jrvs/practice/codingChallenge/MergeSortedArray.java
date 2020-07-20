package ca.jrvs.practice.codingChallenge;

public class MergeSortedArray {
    public int[] mergeSortedArray(int[] nums1, int m, int[] nums2, int n) {
        if (nums1.length < m + n)
            throw new IllegalArgumentException("The size of nums1 is not big enough!");
        /* ArrayList<Integer> arrayList = new ArrayList();
        int[] nums = new int[m + n];
        for (int i = 0; i < m; i++) {
            nums[i] = nums1[i];
        }
        for (int i = 0; i < n; i++) {
            nums[m + i] = nums2[i];
        }
        Arrays.sort(nums);
        return nums;*/


/*        int j = 0;
        for (int i = 0; i < nums1.length; i++) {
            if (nums1[i] == 0) {
                nums1[i] = nums2[j];
                j++;
            }
        }
        Arrays.sort(nums1);
        return nums1;
    }*/
/**
 * Two pointers
 * O(n)
 */
        int i1 = 0;
        int i2 = 0;
        int[] numsTemp = nums1.clone();
        for (int i = 0; i < m + n; i++) {
            if (nums2[i2] < numsTemp[i1] || i1 >= m) {
                nums1[i] = nums2[i2];
                i2++;
            } else {
                nums1[i] = numsTemp[i1];
                i1++;
            }
        }
        return nums1;
    }
}
