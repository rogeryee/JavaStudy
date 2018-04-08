package com.yee.study.java.security.samples.digest;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.digests.SHA224Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.MessageDigest;
import java.security.Security;

/**
 * SHA算法示例
 *
 * @author Roger.Yi
 */
public class SHASample
{
    public static final String src = "hello world";

    public static void main(String[] args) throws Exception
    {
        jdkSHA1();
        bcSHA1();
        bcSHA224();
        bcSHA224b();
        ccSHA1();
    }
    
    /**
     * JDK实现:SHA1
     */
    public static void jdkSHA1() throws Exception
    {
        // SHA-1的名称就是SHA
        MessageDigest md = MessageDigest.getInstance("SHA");
        md.update(src.getBytes());
        System.out.println("jdk sha-1:" + Hex.encodeHexString(md.digest()));
    }

    /**
     * bouncy castle实现:SHA1
     */
    public static void bcSHA1()
    {

        Digest digest = new SHA1Digest();
        digest.update(src.getBytes(), 0, src.getBytes().length);
        byte[] sha1Bytes = new byte[digest.getDigestSize()];
        digest.doFinal(sha1Bytes, 0);
        System.out.println("bc sha-1:" + new String(org.bouncycastle.util.encoders.Hex.encode(sha1Bytes)));
    }

    /**
     * bouncy castle实现:SHA224
     */
    public static void bcSHA224()
    {
        Digest digest = new SHA224Digest();
        digest.update(src.getBytes(), 0, src.getBytes().length);
        byte[] sha224Bytes = new byte[digest.getDigestSize()];
        digest.doFinal(sha224Bytes, 0);
        System.out.println("bc sha-224:" + new String(org.bouncycastle.util.encoders.Hex.encode(sha224Bytes)));
    }

    /**
     * bouncy castle + JDK实现:SHA1
     */
    public static void bcSHA224b() throws Exception
    {
        Security.addProvider(new BouncyCastleProvider());
        MessageDigest md = MessageDigest.getInstance("SHA224");
        md.update(src.getBytes());
        System.out.println("bc and JDK sha-224:" + Hex.encodeHexString(md.digest()));
    }

    /**
     * Apache Common实现:SHA1
     */
    public static void ccSHA1()
    {
        System.out.println("common codes SHA1 - 1 :" + DigestUtils.sha1Hex(src.getBytes()));
        System.out.println("common codes SHA1 - 2 :" + DigestUtils.sha1Hex(src));
    }
}
