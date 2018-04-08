package com.yee.study.java.security.samples.digest;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.digests.MD4Digest;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.MessageDigest;
import java.security.Security;

/**
 * MD消息摘要算法示例
 *
 * @author Roger.Yi
 */
public class MDSample
{
    public static final String TEXT = "hello world";

    public static void main(String[] args) throws Exception
    {
        jdkMD5();
        jdkMD2();

        bcMD4();
        bcMD5();

        bc2jdkMD4();

        ccMD5();
        ccMD2();
    }

    /**
     * JDK实现:MD5
     */
    public static void jdkMD5() throws Exception
    {
        MessageDigest md = MessageDigest.getInstance("MD5");// 得到MD5加密的对象
        byte[] md5Bytes = md.digest(TEXT.getBytes());
        System.out.println("JDK MD5:" + Hex.encodeHexString(md5Bytes));// Hex.encodeHexString()将byte[]数组转换成十六进制
    }

    /**
     * JDK实现:MD2
     */
    public static void jdkMD2() throws Exception
    {
        MessageDigest md = MessageDigest.getInstance("MD2");
        byte[] md2Bytes = md.digest(TEXT.getBytes());
        System.out.println("JDK MD2:" + Hex.encodeHexString(md2Bytes));
    }

    /**
     * bouncy castle实现:MD5
     */
    public static void bcMD5()
    {
        MD5Digest digest = new MD5Digest();
        digest.update(TEXT.getBytes(), 0, TEXT.getBytes().length);
        byte[] md5Bytes = new byte[digest.getDigestSize()];
        digest.doFinal(md5Bytes, 0);
        System.out.println("bouncy castle MD5:" + new String(org.bouncycastle.util.encoders.Hex.encode(md5Bytes)));
    }

    /**
     * bouncy castle实现:MD4
     */
    public static void bcMD4()
    {
        MD4Digest digest = new MD4Digest();
        digest.update(TEXT.getBytes(), 0, TEXT.getBytes().length);
        byte[] md4Bytes = new byte[digest.getDigestSize()];
        digest.doFinal(md4Bytes, 0);
        System.out.println("bouncy castle MD4:" + new String(org.bouncycastle.util.encoders.Hex.encode(md4Bytes)));
    }
    
    /**
     * bouncy castle + JDK实现:MD4
     */
    public static void bc2jdkMD4() throws Exception
    {
        Security.addProvider(new BouncyCastleProvider());
        MessageDigest md = MessageDigest.getInstance("MD4");
        byte[] md4Bytes = md.digest(TEXT.getBytes());
        System.out.println("bc and JDK MD4:" + Hex.encodeHexString(md4Bytes));
    }

    /**
     * Apache Common实现:MD5
     */
    public static void ccMD5()
    {
        System.out.println("common codes MD5:" + DigestUtils.md5Hex(TEXT.getBytes()));
    }

    /**
     * Apache Common实现:MD2
     */
    public static void ccMD2()
    {
        System.out.println("common codes MD2:" + DigestUtils.md2Hex(TEXT.getBytes()));
    }
}
