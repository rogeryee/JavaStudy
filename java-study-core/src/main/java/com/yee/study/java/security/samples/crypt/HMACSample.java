package com.yee.study.java.security.samples.crypt;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * HMAC算法示例
 *
 * @author Roger.Yi
 */
public class HMACSample
{
    public static final String src = "hello world";

    public static void main(String[] args) throws Exception
    {
        jdkHmacMD5();
        bcHmacMD5();
    }

    /**
     * JDK实现
     */
    public static void jdkHmacMD5() throws Exception
    {
        // 秘钥
        byte[] key = Hex.decodeHex(new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e'});

        // 还原密钥，HmacMD5是算法的名字
        SecretKey restoreSecretKey = new SecretKeySpec(key, "HmacMD5");
        Mac mac = Mac.getInstance(restoreSecretKey.getAlgorithm());
        mac.init(restoreSecretKey);
        
        // 执行消息摘要
        byte[] hmacMD5Bytes = mac.doFinal(src.getBytes());
        System.out.println("jdk hmacMD5:" + Hex.encodeHexString(hmacMD5Bytes));
    }

    /**
     * bouncy castle实现
     */
    public static void bcHmacMD5()
    {
        HMac hmac = new HMac(new MD5Digest());
        // 必须是16进制的字符，长度必须是2的倍数
        hmac.init(new KeyParameter(org.bouncycastle.util.encoders.Hex.decode("123456789abcde")));
        hmac.update(src.getBytes(), 0, src.getBytes().length);

        // 执行摘要
        byte[] hmacMD5Bytes = new byte[hmac.getMacSize()];
        hmac.doFinal(hmacMD5Bytes, 0);
        System.out.println("bc hmacMD5:" + new String(org.bouncycastle.util.encoders.Hex.encode(hmacMD5Bytes)));
    }
}
