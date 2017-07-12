package com.yee.study.java;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: RogerYee
 */
public class Test
{
    public static void main(String[] args)
    {
//        new HashMap();
//        new ConcurrentHashMap<>();

//        new Hashtable<>();
        System.out.println(indexFor(496, 16));

        String string = "";
        String[] arg = string.split(",");
        for(String a:arg)
        {
            System.out.println("");
        }
    }

    static int indexFor(int h, int length) {
        return h & (length-1);
    }

}
