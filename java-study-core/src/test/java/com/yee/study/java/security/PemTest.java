package com.yee.study.java.security;

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

    private static final String PLAIN_TEXT = "MANUTD is the most greatest club in the world.";

    @Test
    public void testCertByKeyStore() throws Exception
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
        System.out.println("密文长度:"+ cipher.length);
        System.out.println(PemUtil.byteArrayToString(cipher));
        System.out.println("明文长度:"+ plainText.length);
        System.out.println(PemUtil.byteArrayToString(plainText));
        System.out.println(new String(plainText));
    }
}
