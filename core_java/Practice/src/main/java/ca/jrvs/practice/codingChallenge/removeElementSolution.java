package ca.jrvs.practice.codingChallenge;

public class removeElementSolution {
    public static void main(String[] args) {
        removeElementSolution solution = new removeElementSolution();
        System.out.println(solution.removeElement(new int[]{2, 2, 2, 2, 2, 2, 1}, 2));
    }

    public int removeElement(int[] nums, int val) {
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                //nums[j++] = nums[i];
                nums[j] = nums[i];
                j++;
            }
        }
        //for (int k = 0; k < nums.length; k++) {
        //  System.out.println(nums[k]);
        //}
        return j;
    }
}