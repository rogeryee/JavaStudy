package com.yee.study.java.security.samples.signature;

import org.apache.commons.codec.binary.Hex;

import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * ECDSA签名示例
 *
 * @author Roger.Yi
 */
public class ECDSASample
{
    public static final String src = "hello world";

    public static void main(String[] args)
    {
        jdkECDSA();

    }

    /**
     * 说明： 用java的jdk里面相关方法实现ECDSA的签名及签名验证,要jdk7.x以上，ECDSA：椭圆曲线数字签名算法
     */
    public static void jdkECDSA()
    {
        try
        {
            // 1.初始化密钥
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
            keyPairGenerator.initialize(256);  //设置密钥的长度
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            ECPublicKey ecPublicKey = (ECPublicKey) keyPair.getPublic(); //得到公钥
            ECPrivateKey ecPrivateKey = (ECPrivateKey) keyPair.getPrivate(); //得到私钥

            // 2.私钥进行加密
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(ecPrivateKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

            Signature signature = Signature.getInstance("SHA1withECDSA");
            signature.initSign(privateKey);
            signature.update(src.getBytes());//更新要签名的字符串
            byte[] result = signature.sign();
            System.out.println("jdk ecdsa sign:" + Hex.encodeHexString(result));

            // 3.公钥进行验证
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(ecPublicKey.getEncoded());
            keyFactory = KeyFactory.getInstance("EC");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            signature = Signature.getInstance("SHA1withECDSA");
            signature.initVerify(publicKey);
            signature.update(src.getBytes());
            boolean bool = signature.verify(result);
            System.out.println("jdk ecdsa verify:" + bool);
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }
}
