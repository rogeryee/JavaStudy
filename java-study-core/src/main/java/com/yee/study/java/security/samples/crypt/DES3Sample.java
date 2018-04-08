package com.yee.study.java.security.samples.crypt;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.Key;
import java.security.Security;

/**
 * 3DES加解密示例
 *
 * @author Roger.Yi
 */
public class DES3Sample
{
    public static final String src = "hello world";

    public static void main(String[] args) throws Exception
    {
        jdk3DES();
        bc3DES();
    }

    /**
     * JDK实现
     */
    public static void jdk3DES() throws Exception
    {
        // 获取KEY生成器, 必须长度是：112或168
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
        keyGenerator.init(168); // 也可以用 keyGenerator.init(new SecureRandom()); 根据不同的算法生成默认长度的KEY

        // 产生并获取KEY
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] bytesKey = secretKey.getEncoded();

        // KEY转换
        DESedeKeySpec desKeySpec = new DESedeKeySpec(bytesKey);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
        Key convertSecretKey = factory.generateSecret(desKeySpec);

        // 加密
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
        byte[] result = cipher.doFinal(src.getBytes());
        System.out.println("jdk 3des encrypt:" + Hex.encodeHexString(result));

        // 解密
        cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
        result = cipher.doFinal(result);
        System.out.println("jdk 3des decrypt:" + new String(result));
    }

    /**
     * bouncy castle实现
     */
    public static void bc3DES() throws Exception
    {
        Security.addProvider(new BouncyCastleProvider());

        // 获取KEY生成器
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede", "BC");
        keyGenerator.getProvider();
        keyGenerator.init(168);

        // 产生并获取KEY
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] bytesKey = secretKey.getEncoded();

        // KEY转换
        DESedeKeySpec desKeySpec = new DESedeKeySpec(bytesKey);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
        Key convertSecretKey = factory.generateSecret(desKeySpec);

        // 加密
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
        byte[] result = cipher.doFinal(src.getBytes());
        System.out.println("bc 3des encrypt:" + Hex.encodeHexString(result));

        // 解密
        cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
        result = cipher.doFinal(result);
        System.out.println("bc 3des decrypt:" + new String(result));
    }
}
