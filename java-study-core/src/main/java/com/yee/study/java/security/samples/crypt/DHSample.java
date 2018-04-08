package com.yee.study.java.security.samples.crypt;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

/**
 * DH加解密示例
 *
 * @author Roger.Yi
 */
public class DHSample
{
    public static final String src = "hello world";

    public static void main(String[] args)
    {
        jdkDH();
    }

    /**
     * JDK示例
     */
    public static void jdkDH()
    {
        try
        {
            // 1.初始化发送方密钥
            KeyPairGenerator senderKeyPairGenerator = KeyPairGenerator.getInstance("DH");
            senderKeyPairGenerator.initialize(512);//设置长度
            KeyPair senderKeyPair = senderKeyPairGenerator.generateKeyPair();//生成keypair
            byte[] senderPublicKeyEnc = senderKeyPair.getPublic().getEncoded();

            // 2.初始化接收方密钥
            KeyFactory receiverKeyFactory = KeyFactory.getInstance("DH");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(senderPublicKeyEnc);
            PublicKey receiverPublicKey = receiverKeyFactory.generatePublic(x509EncodedKeySpec);
            DHParameterSpec dhParameterSpec = ((DHPublicKey) receiverPublicKey).getParams();

            KeyPairGenerator receiverKeyPairGenerator = KeyPairGenerator.getInstance("DH");
            receiverKeyPairGenerator.initialize(dhParameterSpec);
            KeyPair receiverKeypair = receiverKeyPairGenerator.generateKeyPair();
            PrivateKey receiverPrivateKey = receiverKeypair.getPrivate();
            byte[] receiverPublicKeyEnc = receiverKeypair.getPublic().getEncoded();

            // 3.密钥构建
            KeyAgreement receiverKeyAgreement = KeyAgreement.getInstance("DH");
            receiverKeyAgreement.init(receiverPrivateKey);
            receiverKeyAgreement.doPhase(receiverPublicKey, true);

            //生成接收方的本地密钥
            SecretKey receiverDesKey = receiverKeyAgreement.generateSecret("DES");

            /*
             * 发送方的操作
             */
            KeyFactory senderKeyFactory = KeyFactory.getInstance("DH");
            x509EncodedKeySpec = new X509EncodedKeySpec(receiverPublicKeyEnc);
            PublicKey senderPublicKey = senderKeyFactory.generatePublic(x509EncodedKeySpec);
            KeyAgreement senderKeyAgreement = KeyAgreement.getInstance("DH");
            senderKeyAgreement.init(senderKeyPair.getPrivate());
            senderKeyAgreement.doPhase(senderPublicKey, true);

            //生成发送方的本地密钥
            SecretKey senderDesKey = senderKeyAgreement.generateSecret("DES");

            if (Objects.equals(receiverDesKey, senderDesKey))
            {
                System.out.println("发送方和接收方密钥相同。");
            }
            else
            {
                System.out.println("发送方和接收方密钥不相同");
            }

            // 4.加密
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, senderDesKey);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("bc dh encrypt:" + Base64.encodeBase64String(result));

            // 5.解密
            cipher.init(Cipher.DECRYPT_MODE, receiverDesKey);
            result = cipher.doFinal(result);
            System.out.println("bc dh decrypt:" + new String(result));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
