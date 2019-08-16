package com.easy.rpc.common.utils;

/**
 * @author ljl
 * @since 2019/8/16
 */
public class StringUtils {

    public static boolean isBlank(String str){
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str){
        return !isBlank(str);
    }

}
