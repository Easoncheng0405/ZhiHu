package com.jlu.chengjie.zhihu.util;

/*
 *@Author chengjie
 *@Date 2018-12-21
 *@Email chengjie.jlu@qq.com
 */

import android.util.Base64;

import java.nio.charset.StandardCharsets;


public class EncryptUtil {

    public static String base64(String str) {
       return Base64.encodeToString(str.getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP);
    }
}
