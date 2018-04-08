package com.yee.study.java.security.samples.crypt;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;
import java.security.Security;

/**
 * DES加密示例
 *
 * @author Roger.Yi
 */
public class DESSample
{
    public static final String src = "hello world";

    public static void main(String[] args) throws Exception
    {
        jdkDES();
        bcDES();
    }

    /**
     * JDK实现
     */
    public static void jdkDES() throws Exception
    {
        // 获取KEY生成器
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        keyGenerator.init(56);

        // 产生并获取KEY
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] bytesKey = secretKey.getEncoded();

        // KEY转换
        DESKeySpec desKeySpec = new DESKeySpec(bytesKey);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");//getInstance()参数指定的加密方式
        Key convertSecretKey = factory.generateSecret(desKeySpec);//生成密钥
        System.out.println("jdk key : " + Base64.encodeBase64String(convertSecretKey.getEncoded()));

        // 加密
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");//加解密算法：DES，工作方式：ECB，填充方式：PKCS5Padding
        cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);//第一个参数是模式：加密模式，KEY:转换后的KEY
        byte[] result = cipher.doFinal(src.getBytes());
        System.out.println("jdk des encrypt:" + Hex.encodeHexString(result));

        // 解密
        cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
        result = cipher.doFinal(result);
        System.out.println("jdk des decrypt:" + new String(result));
    }

    /**
     * bouncy castle实现
     */
    public static void bcDES() throws Exception
    {
        Security.addProvider(new BouncyCastleProvider());

        // 获取KEY生成器
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES", "BC");
        keyGenerator.getProvider();
        keyGenerator.init(56);
        
        // 产生并获取获取KEY
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] bytesKey = secretKey.getEncoded();

        // KEY转换
        DESKeySpec desKeySpec = new DESKeySpec(bytesKey);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
        Key convertSecretKey = factory.generateSecret(desKeySpec);
        System.out.println("bc key : " + Base64.encodeBase64String(convertSecretKey.getEncoded()));

        // 加密
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding"); //加解密算法：DES，工作方式：ECB，填充方式：PKCS5Padding
        cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
        byte[] result = cipher.doFinal(src.getBytes());
        System.out.println("bc des encrypt:" + Hex.encodeHexString(result));

        // 解密
        cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
        result = cipher.doFinal(result);
        System.out.println("bc des decrypt:" + new String(result));
    }
}
