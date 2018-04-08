package com.yee.study.java.security.samples.signature;

import org.apache.commons.codec.binary.Hex;

import java.security.*;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * DSA签名的示例
 *
 * @author Roger.Yi
 */
public class DSASample
{
    public static final String src = "hello world";

    public static void main(String[] args)
    {
        jdkDSA();
    }

    /**
     * 说明： 用java的jdk里面相关方法实现dsa的签名及签名验证
     */
    public static void jdkDSA()
    {
        try
        {
            // 1.初始化密钥
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
            keyPairGenerator.initialize(512);  //设置密钥的长度
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            DSAPublicKey dsaPublicKey = (DSAPublicKey) keyPair.getPublic(); //得到公钥
            DSAPrivateKey dsaPrivateKey = (DSAPrivateKey) keyPair.getPrivate(); //得到私钥

            // 2.私钥进行签名
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(dsaPrivateKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("DSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

            Signature signature = Signature.getInstance("SHA1withDSA");
            signature.initSign(privateKey);
            signature.update(src.getBytes());
            byte[] result = signature.sign();
            System.out.println("jdk dsa sign:" + Hex.encodeHexString(result));

            // 3.公钥进行验证
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(dsaPublicKey.getEncoded());
            keyFactory = KeyFactory.getInstance("DSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

            signature = Signature.getInstance("SHA1withDSA");
            signature.initVerify(publicKey);
            signature.update(src.getBytes());
            boolean bool = signature.verify(result);
            System.out.println("jdk dsa verify:" + bool);
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }
}
