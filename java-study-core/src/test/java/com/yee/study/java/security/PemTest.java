package com.yee.study.java.security;

import com.yee.study.util.StringUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

import static org.junit.Assert.assertNotNull;

/**
 * @author Roger.Yi
 */
public class PemTest
{
    private static final String PRIVATE_KEY_PATH = "classpath:security/pem/rsa_private_key_pkcs8.pem";
    
    private static final String PUBLIC_KEY_PATH = "classpath:security/pem/rsa_public_key.pem";

    private static final String PLAIN_TEXT = "Manchester United FC is the most greatest club in the world.";

    @Test
    public void testCrypt() throws Exception
    {
        File publicKeyFile = ResourceUtils.getFile(PUBLIC_KEY_PATH);
        PublicKey publicKey = PemUtil.getPublicKey(publicKeyFile);

        File privateKeyFile = ResourceUtils.getFile(PRIVATE_KEY_PATH);
        PrivateKey privateKey = PemUtil.getPrivateKey(privateKeyFile);

        assertNotNull(publicKey);
        assertNotNull(privateKey);

        //加密
        byte[] cipher = PemUtil.encrypt(publicKey, PLAIN_TEXT.getBytes());
        
        //解密
        byte[] plainText = PemUtil.decrypt(privateKey, cipher);
        System.out.println("密文长度:" + cipher.length);
        System.out.println(PemUtil.byteArrayToString(cipher));
        System.out.println("明文长度:" + plainText.length);
        System.out.println(PemUtil.byteArrayToString(plainText));
        System.out.println(new String(plainText));
    }

    @Test
    public void testCryptBySplit() throws Exception
    {
        File publicKeyFile = ResourceUtils.getFile(PUBLIC_KEY_PATH);
        PublicKey publicKey = PemUtil.getPublicKey(publicKeyFile);

        File privateKeyFile = ResourceUtils.getFile(PRIVATE_KEY_PATH);
        PrivateKey privateKey = PemUtil.getPrivateKey(privateKeyFile);

        assertNotNull(publicKey);
        assertNotNull(privateKey);
        
        byte[] PLAIN_DATA = PLAIN_TEXT.getBytes();
        System.out.println("Before encrypt text = [" + PLAIN_TEXT + "], length = " + PLAIN_TEXT.length());

        // 加密，加密后报文为128位，所以需要将原有密文分片（每片不能超过128 - 11 = 117），当前分片为100
        byte[] encryptData = null;
        for (int i = 0; i < PLAIN_DATA.length; i += 100)
        {
            String temp = StringUtil.subString(PLAIN_TEXT, i, i + 100);
            byte[] cipher = PemUtil.encrypt(privateKey, temp.getBytes());
            System.out.println("EncryptTemp = [" + temp + "], cipher length = " + cipher.length);
            encryptData = ArrayUtils.addAll(encryptData, cipher);
        }

        String cipherTextBase64 = Base64.encodeBase64String(encryptData);
        byte[] cipherTextArray = Base64.decodeBase64(cipherTextBase64);
        System.out.println("After encrypt, binary data length = " + cipherTextArray.length);

        //解密
        String decryptText = new String(PemUtil.decrypt(publicKey, cipherTextArray));
        System.out.println("After decrypt text = [" + decryptText + "], length = " + decryptText.length());
    }
}
