package com.yee.study.java.security.samples.crypt;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Security;

/**
 * AES加解密的示例
 *
 * @author Roger.Yi
 */
public class AESSample
{
    public static final String src = "hello world";

    public static void main(String[] args) throws Exception
    {
        jdkAES();
        bcAES();
    }

    /**
     * JDK实现
     */
    public static void jdkAES() throws Exception
    {
        // 获取KEY生成器
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);

        // 产生并获取KEY
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] keyBytes = secretKey.getEncoded();

        // KEY转换
        Key key = new SecretKeySpec(keyBytes, "AES");

        // 加密
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] result = cipher.doFinal(src.getBytes());
        System.out.println("jdk aes encrypt:" + Hex.encodeHexString(result));

        // 解密
        cipher.init(Cipher.DECRYPT_MODE, key);
        result = cipher.doFinal(result);
        System.out.println("jdk aes decrypt:" + new String(result));
    }

    /**
     * bouncy castle实现
     */
    public static void bcAES() throws Exception
    {
        Security.addProvider(new BouncyCastleProvider());

        // 获取KEY生成器
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", "BC");
        keyGenerator.getProvider();
        keyGenerator.init(128);

        // 产生并获取KEY
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] keyBytes = secretKey.getEncoded();

        // KEY转换
        Key key = new SecretKeySpec(keyBytes, "AES");

        // 加密
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] result = cipher.doFinal(src.getBytes());
        System.out.println("bc aes encrypt:" + Hex.encodeHexString(result));

        // 解密
        cipher.init(Cipher.DECRYPT_MODE, key);
        result = cipher.doFinal(result);
        System.out.println("bc aes decrypt:" + new String(result));
    }
}
