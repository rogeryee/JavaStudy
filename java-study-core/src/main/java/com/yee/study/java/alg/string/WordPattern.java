package com.yee.study.java.alg.string;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Word Pattern 单词模式
 * <p>
 * Examples:
 * pattern = "abba", str = "dog cat cat dog" should return true.
 * pattern = "abba", str = "dog cat cat fish" should return false.
 * pattern = "aaaa", str = "dog cat cat dog" should return false.
 * pattern = "abba", str = "dog dog dog dog" should return false.
 *
 * @author Roger.Yi
 */
public class WordPattern {

    public static void main(String[] args) {
        System.out.println(solution1("abba", "dog cat cat dog", " "));
        System.out.println(solution1("abba", "dog cat dog cat", " "));
    }

    /**
     * 解决方式1
     *
     * 构建HashMap，字符 映射 字符串
     *
     * @param pattern
     * @param str
     * @return
     */
    public static boolean solution1(String pattern, String str, String delimiter) {
        Map<Character, String> map = new HashMap<Character, String>();
        Set<String> set = new HashSet<String>();

        String[] pieces = str.split(delimiter);

        // 模式和拆分的字符串长度不一致，则不合符该模式
        if (pieces.length != pattern.length()) {
            return false;
        }
        
        int i = 0;
        for (String s : pieces) {
            char p = pattern.charAt(i);
            System.out.println(p);

            // 如果该字符产生过映射
            if (map.containsKey(p)) {
                // 且映射的字符串和当前字符串不一样
                if (!s.equals(map.get(p)))
                    return false;
            } else {
                // 如果该字符没有产生过映射
                // 如果当前字符串已经被映射过了
                if (set.contains(s))
                    return false;

                // 否则新加一组映射
                map.put(p, s);
                set.add(s);
            }
            i++;
        }
        return true;
    }
}
