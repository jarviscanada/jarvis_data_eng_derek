public class printLetterWithNumber {
    public static void main(String[] args) {
        printLetterWithNumber pwn = new printLetterWithNumber();
        System.out.println(pwn.printLetterWithNumber("abceeE"));
    }

    public String printLetterWithNumber(String string) {
        char[] charArray = string.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < charArray.length; i++) {
            int ascii = charArray[i];
            int value = 0;
            if (Character.isUpperCase(charArray[i])) {
                value = ascii - 38;
            } else value = ascii - 96;
            stringBuilder.append(charArray[i]);
            stringBuilder.append(value);
        }

        return stringBuilder.toString();
    }
}
