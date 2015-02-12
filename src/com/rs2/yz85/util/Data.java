package com.rs2.yz85.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class Data {
    public static MessageDigest md5;
    public static final char xlateTable[] = { ' ', 'e', 't', 'a', 'o', 'i', 'h', 'n', 's', 'r', 'd', 'l', 'u', 'm', 'w', 'c', 'y', 'f', 'g', 'p', 'b', 'v', 'k', 'x', 'j', 'q', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ' ', '!', '?', '.', ',', ':', ';', '(', ')', '-', '&', '*', '\\', '\'', '@', '#', '+', '=', '\243', '$', '%', '"', '[', ']' };
    public static final char playerNameXlateTable[] = { '_', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    public static long playerNameToLong(String s) {
        long l = 0L;
        for(int i = 0; i < s.length() && i < 12; i++) {
            char c = s.charAt(i);
            l *= 37L;
            if(c >= 'A' && c <= 'Z') {
                l += (1 + c) - 65;
            } else if(c >= 'a' && c <= 'z') {
                l += (1 + c) - 97;
            } else if(c >= '0' && c <= '9') {
                l += (27 + c) - 48;
            }
        }
        while(l % 37L == 0L && l != 0L) {
            l /= 37L;
        }
        return l;
    }

    public static String chatDataToString(byte[] packedData) {
        char[] decodeBuf = new char[100];
        int idx = 0, highNibble = -1;
        for(int i = 0; i < packedData.length * 2; i++) {
            int val = packedData[i / 2] >> (4 - 4 * (i % 2)) & 0xf;
            if(highNibble == -1) {
                if(val < 13) {
                    decodeBuf[idx++] = xlateTable[val];
                } else {
                    highNibble = val;
                }
            } else {
                decodeBuf[idx++] = xlateTable[((highNibble << 4) + val) - 195];
                highNibble = -1;
            }
        }
        return new String(decodeBuf, 0, idx);
    }

    public static String convertTextToMD5(String text) {
        md5.reset();
        md5.update(text.getBytes());
        return new BigInteger(1, md5.digest()).toString(16);
    }

    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch(NoSuchAlgorithmException e) {
            Logger.err(e);
        }
    }

    public static String longToPlayerName(long l) {
        int i = 0;
        char ac[] = new char[12];
        while(l != 0L) {
            long l1 = l;
            l /= 37L;
            ac[11 - i++] = playerNameXlateTable[(int) (l1 - l * 37L)];
        }
        return new String(ac, 12 - i, i);
    }

    public static String optimizeText(String text) {
        char buf[] = text.toCharArray();
        boolean endMarker = true;
        for(int i = 0; i < buf.length; i++) {
            char c = buf[i];
            if(endMarker && c >= 'a' && c <= 'z') {
                buf[i] -= 0x20;
                endMarker = false;
            }
            if(c == '.' || c == '!' || c == '?') {
                endMarker = true;
            }
        }
        return new String(buf, 0, buf.length);
    }
}
