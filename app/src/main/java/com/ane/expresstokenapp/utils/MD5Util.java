package com.ane.expresstokenapp.utils;

import java.security.MessageDigest;

/**
 * Created by Administrator on 2017/7/28 0028.
 */

public class MD5Util {

    public static String encryption(String plain) {
        String re_md5 = new String();
        try {
            MessageDigest e = MessageDigest.getInstance("MD5");
            e.update(plain.getBytes());
            byte[] b = e.digest();
            StringBuffer buf = new StringBuffer("");

            for(int offset = 0; offset < b.length; ++offset) {
                int i = b[offset];
                if(i < 0) {
                    i += 256;
                }

                if(i < 16) {
                    buf.append("0");
                }

                buf.append(Integer.toHexString(i));
            }

            re_md5 = buf.toString();
        } catch (Exception var7) {
            var7.printStackTrace();
        }
        return re_md5;
    }
}
