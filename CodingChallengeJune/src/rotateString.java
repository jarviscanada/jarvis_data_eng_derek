public class rotateString {
    public boolean rotateString(String A, String B){


        return A.length()==B.length() && (A+A).contains(B);
        }

    public static void main(String[] args) {
        rotateString rs = new rotateString();
        String A = "abcde";
        String B = "cdeab";
        System.out.println(rs.rotateString(A,B));
        System.out.println(A+A);

    }
}


