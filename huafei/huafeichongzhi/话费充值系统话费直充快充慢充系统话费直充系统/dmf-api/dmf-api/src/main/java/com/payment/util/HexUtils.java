package com.payment.util;

/**
 * @Author: Chand
 * @Date: 13-12-04
 * @Time: 下午2:30
 * @Description: to write something
 */
public class HexUtils {
    private static final String HEX_CHARS = "0123456789abcdef";

    public static String toHexString(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            sb.append(HexUtils.HEX_CHARS.charAt(b[i] >>> 4 & 0x0F));
            sb.append(HexUtils.HEX_CHARS.charAt(b[i] & 0x0F));
        }
        return sb.toString();
    }

    /**
     * Converts a hex string into a byte array.
     *
     * @param s - string to be converted
     * @return byte array converted from s
     */
    public static byte[] toByteArray(String s) {
        byte[] buf = new byte[s.length() / 2];
        int j = 0;
        for (int i = 0; i < buf.length; i++) {
            buf[i] = (byte) ((Character.digit(s.charAt(j++), 16) << 4) | Character.digit(s.charAt(j++), 16));
        }
        return buf;
    }
}
