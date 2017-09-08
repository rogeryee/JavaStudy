package com.yee.study;

/**
 * @author Roger.Yi
 */
public class Test
{
    public static void main(String[] args)
    {
        String certNo = "310104198102051223";
//        System.out.println(certNo.substring(6, 10));
//        System.out.println(certNo.substring(10, 12));
//        System.out.println(certNo.substring(16, 17));

        int gender = Integer.valueOf(certNo.substring(16, 17)) % 2;
        System.out.println(gender);
    }
}
