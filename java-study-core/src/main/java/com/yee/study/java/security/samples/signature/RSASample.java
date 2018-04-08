package com.yee.study.java.security.samples.signature;

import org.apache.commons.codec.binary.Hex;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA签名示例
 *
 * @author Roger.Yi
 */
public class RSASample
{
    public static final String src = "hello world";

    public static void main(String[] args)
    {
        jdkRSA();
    }

    /**
     * 说明： 用java的jdk里面相关方法实现rsa的签名及签名验证
     */
    public static void jdkRSA()
    {
        try
        {
            // 1.初始化密钥
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(512);  //设置KEY的长度
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();  //得到公钥
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate(); //得到私钥

            // 2.用私钥进行签名
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

            Signature signature = Signature.getInstance("MD5withRSA"); //声明签名的对象
            signature.initSign(privateKey);
            signature.update(src.getBytes());
            byte[] result = signature.sign();
            System.out.println("jdk rsa sign:" + Hex.encodeHexString(result));

            // 3.用公钥进行验证签名
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
            keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

            signature = Signature.getInstance("MD5withRSA");
            signature.initVerify(publicKey);
            signature.update(src.getBytes());
            boolean bool = signature.verify(result);
            System.out.println("jdk rsa verify:" + bool);
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }
}
