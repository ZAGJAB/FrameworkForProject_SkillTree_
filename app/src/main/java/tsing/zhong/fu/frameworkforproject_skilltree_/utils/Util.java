package tsing.zhong.fu.frameworkforproject_skilltree_.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import tsing.zhong.fu.frameworkforproject_skilltree_.R;

/**
 * Created by fuzho on 2015/8/18.
 */
public class Util {
    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }
    public static int bgrd(int x) {
        switch (x % 7) {
            case 0:
                return R.drawable.card00;
            case 1:
                return R.drawable.card01;
            case 2:
                return R.drawable.card02;
            case 3:
                return R.drawable.card03;
            case 4:
                return R.drawable.card04;
            case 5:
                return R.drawable.card05;
            case 6:
                return R.drawable.card06;
            default:
                return R.drawable.card00;
        }
    }
}
