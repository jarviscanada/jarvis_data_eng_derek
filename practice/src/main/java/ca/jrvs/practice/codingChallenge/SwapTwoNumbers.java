package ca.jrvs.practice.codingChallenge;

public class SwapTwoNumbers {
    public int[] swapTwoNumbers(int[] nums){
        /**
         * Approach 1: bit-manipulation
         * O()
         */
        nums[0] = nums[0] ^ nums[1];
        nums[1] = nums[0] ^ nums[1];
        nums[0] = nums[0] ^ nums[1];
        return new int[] {nums[0], nums[1]};

        /**
         * Approach 2: Arithmetic Operators
         */
        /*nums[0] = nums[0] + nums[1];
        nums[1] = nums[0] - nums[1];
        nums[0] = nums[0] - nums[1];
        return new int[]{nums[0],nums[1]};*/
    }
}
