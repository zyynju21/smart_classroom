package com.yiyue.common.util;



import com.yiyue.common.exception.CustomException;

import java.security.MessageDigest;
import java.util.Objects;
import java.util.UUID;

/**
 * @Author: XiaoQi
 * @Date: 2020/9/7 16:51
 */
public class TokenGenerator {

    private static final char[] HEX_CODE = "0123456789abcdef".toCharArray();

    public static String generateValue() {
        return generateValue(UUID.randomUUID().toString());
    }

    private static String toHexString(byte[] data) {
        if (Objects.isNull(data)) {
            return null;
        }
        StringBuilder r = new StringBuilder(data.length * 2);
        byte[] arrayOfByte = data;
        int j = data.length;
        for (int i = 0; i < j; i++) {
            byte b = arrayOfByte[i];
            r.append(HEX_CODE[(b >> 4 & 0xF)]);
            r.append(HEX_CODE[(b & 0xF)]);
        }
        return r.toString();
    }

    private static String generateValue(String param) {
        try {
            MessageDigest algorithm;
            algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(param.getBytes());
            byte[] messageDigest = algorithm.digest();
            return toHexString(messageDigest);
        } catch (Exception ex) {
            throw new CustomException(ExceptionUtil.getStackTrace(ex));
        }
    }
}
