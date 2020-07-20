public class countPrimes {
    public static void main(String[] args) {
        countPrimes cp = new countPrimes();
        System.out.println(cp.countPrimes(10));
    }

    public int countPrimes(int n) {
        //method 1
        /* if (n <= 1) return 0;
        int count = 0;
        for (int i = 2; i < n + 1; i++) {
            BigInteger b = new BigInteger(String.valueOf(i));
            if (b.isProbablePrime(1)) count++;
        }
        return count;*/
        //method 2
        int count = 0;
        if (n <= 1) return 0;
        for (int i = 2; i <= n; i++) {
            int flag = 1;
            for (int j = 2; j <= i / 2; j++) {
                if (i % j == 0) {
                    flag = 0;
                    break;
                }
            }
            if (flag == 1) {
                count++;
            }
        }
        return count++;
    }
}