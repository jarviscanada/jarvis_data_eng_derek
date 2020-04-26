package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;

public class mergeSortedArray {
    public static void main(String[] args) {
        mergeSortedArray solution = new mergeSortedArray();
        solution.merger(new int[]{1, 2, 0, 0, 0, 0, 0, 0}, 4, new int[]{1, 3, 4, 5}, 4);
    }

    public void merger(int[] nums1, int m, int[] nums2, int n) {
        int i1 = 0;
        int i2 = 0;
        int[] numsTemp = nums1.clone();
        for (int i = 0; i < m + n; i++) {
            if (i2 < n) {
                if (nums2[i2] < numsTemp[i1] || i1 >= m) {
                    nums1[i] = nums2[i2];
                    i2++;
                } else {
                    nums1[i] = numsTemp[i1];
                    i1++;
                }
            } else {
                nums1[i] = numsTemp[i1];
                i1++;
            }
        }
        System.out.println(Arrays.toString(nums1));
    }
}
