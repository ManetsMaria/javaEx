public class EncodingAndDecoding {

    public static byte[] encode(String text, String keyWord)
    {
        byte[] textBytes = text.getBytes();
        int n = textBytes.length;
        byte[] keyWordBytes = keyWord.getBytes();
        byte[] result = new byte[n];
        for(int i = 0; i< n; i++) {
            int keyWordIndex = i % keyWordBytes.length;
            result[i] = (byte) (textBytes[i] ^ keyWordBytes[keyWordIndex]);
        }
        return result;
    }

    public static String decode(byte[] text, String keyWord) {
        int n = text.length;
        byte[] result = new byte[n];
        byte[] keyWordBytes = keyWord.getBytes();
        for(int i = 0; i < n; i++) {
            int keyWordIndex = i % keyWordBytes.length;
            result[i] = (byte) (text[i] ^ keyWordBytes[keyWordIndex]);
        }
        return new String(result);
    }
}
