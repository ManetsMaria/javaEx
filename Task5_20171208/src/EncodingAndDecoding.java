public class EncodingAndDecoding {

    public static String decode(String text, String keyWord) {
        int n = text.length();
        byte[] textByte = text.getBytes();
        byte[] result = new byte[n];
        byte[] keyWordBytes = keyWord.getBytes();
        for(int i = 0; i < n; i++) {
            int keyWordIndex = i % keyWordBytes.length;
            result[i] = (byte) (textByte[i] ^ keyWordBytes[keyWordIndex]);
        }
        return new String(result);
    }
}
