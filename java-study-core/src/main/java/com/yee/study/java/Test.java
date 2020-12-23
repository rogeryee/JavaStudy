package com.yee.study.java;

import com.yee.study.util.ArrayUtil;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Roger.Yi
 */
public class Test {
    public static void main(String[] args) {
        List<Integer> list = ArrayUtil.asList(1, 3, 0, -1, 10);
        List<Integer> newList = list.stream().map(i -> {
            return i >= 3 ? i : null;
        }).filter(Objects::nonNull).collect(Collectors.toList());

        System.out.println(newList);
    }
}
