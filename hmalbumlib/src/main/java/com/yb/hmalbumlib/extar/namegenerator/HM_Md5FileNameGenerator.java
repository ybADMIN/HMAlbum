//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yb.hmalbumlib.extar.namegenerator;

import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HM_Md5FileNameGenerator implements HM_FileNameGenerator {
    private static final String HASH_ALGORITHM = "MD5";
    private static final int RADIX = 36;

    public HM_Md5FileNameGenerator() {
    }

    public String generate(String imageUri) {
        byte[] md5 = this.getMD5(imageUri.getBytes());
        BigInteger bi = (new BigInteger(md5)).abs();
        return bi.toString(36);
    }

    private byte[] getMD5(byte[] data) {
        byte[] hash = null;

        try {
            MessageDigest e = MessageDigest.getInstance("MD5");
            e.update(data);
            hash = e.digest();
        } catch (NoSuchAlgorithmException var4) {
            Log.e("HM_Md5FileNameGenerator",var4.getMessage());
        }

        return hash;
    }
}
