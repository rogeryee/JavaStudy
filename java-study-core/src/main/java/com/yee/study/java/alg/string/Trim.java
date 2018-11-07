package com.yee.study.java.alg.string;

/**
 * 对字符串进行 trim的操作
 * 
 * @author Roger.Yi
 */
public class Trim {

    public static void main(String[] args) {
        System.out.println("[" + doTrim("   it is test   ") + "]");
    }

    public static String doTrim(String str) {

        char[] chars = str.toCharArray();

        int start = 0;
        for(; start < chars.length ; start++) {
            if(chars[start] != ' ') {
                break;
            }
        }

        int end = chars.length;
        for(; end > 0 ; end--) {
            if(chars[end - 1] != ' ') {
                break;
            }
        }

        return str.substring(start, end);
    }
}
