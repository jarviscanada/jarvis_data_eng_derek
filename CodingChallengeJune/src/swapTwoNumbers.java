public class swapTwoNumbers {
    public static void main(String[] args) {
        swapTwoNumbers stn = new swapTwoNumbers();
        System.out.println(stn.swapTwoNumbers(new int[]{2,5})[0]);
        System.out.println(stn.swapTwoNumbers(new int[]{2,5})[1]);
    }

    public int[] swapTwoNumbers(int[] array) {
        //method 1 bit-manipulation
        /*array[0] = array[0] ^ array[1];
        array[1] = array[0] ^ array[1];
        array[0] = array[0] ^ array[1];
        return array;*/

        //method 2 Using Arithmetic Operators
        array[0] = array[0] + array[1];
        array[1] = array[0] - array[1];
        array[0] = array[0] - array[1];
        return array;
    }
}
