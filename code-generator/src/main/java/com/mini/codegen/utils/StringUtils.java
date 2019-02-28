package com.mini.codegen.utils;

public class StringUtils {

    /**
     * 首字母变小写
     * @param str
     * @return
     */
    public static String toLowerCaseFirstChar(String str) {
        if (Character.isLowerCase(str.charAt(0))) {
            return str;
        } else {
            char[] chars = str.toCharArray();
            chars[0] = Character.toLowerCase(chars[0]);
            return new String(chars);
        }
    }

    /**
     * 首字母变大写
     * @param str
     * @return
     */
    public static String toUpperCaseFirstOne(String str) {
        if (Character.isUpperCase(str.charAt(0))) {
            return str;
        } else {
            char[] chars = str.toCharArray();
            chars[0] = Character.toUpperCase(chars[0]);
            return new String(chars);
        }
    }
}
