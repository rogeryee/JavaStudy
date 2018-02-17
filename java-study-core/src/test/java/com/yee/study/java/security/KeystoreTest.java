package com.yee.study.java.security;

import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.List;

import static com.yee.study.java.security.KeystoreUtil.*;

/**
 * @author Roger.Yi
 */
public class KeystoreTest
{
    private static final String KEYSTORE_PATH = "classpath:security/keystore/myKeystore1.keystore";
    private static final String CERT_PATH = "classpath:security/keystore/myCert1.cer";

    private static final String KEYSTORE_PWD = "123456";
    private static final String ALIAS = "myCert1";
    private static final String CERT_TYPE = "X.509";
    
    private static final String PLAIN_TEXT = "MANUTD is the most greatest club in the world.";


    /**
     * 假设现在有这样一个场景 。A机器上的数据，需要加密导出，然后将导出文件放到B机器上导入。
     * 在这个场景中，A相当于服务器，B相当于客户端
     * @throws Exception
     */
    @Test
    public void testCertByKeyStore() throws Exception
    {
        /** A */
        File keystoreFile = ResourceUtils.getFile(KEYSTORE_PATH);
        KeyStore keyStore = getKeyStore(KEYSTORE_PWD, keystoreFile);
        PrivateKey privateKey = getPrivateKey(keyStore, ALIAS, KEYSTORE_PWD);
        X509Certificate certificate = getCertificateByKeystore(keyStore, ALIAS);

        /** 加密和签名 */
        byte[] encodedText = encode(PLAIN_TEXT.getBytes(), privateKey);
        byte[] signature = sign(certificate, privateKey, PLAIN_TEXT.getBytes());

        /** 现在B收到了A的密文和签名，以及A的可信任证书 */
        File certFile = ResourceUtils.getFile(CERT_PATH);
        X509Certificate receivedCertificate = getCertificateByCertPath(certFile, CERT_TYPE);
        PublicKey publicKey = getPublicKey(receivedCertificate);
        byte[] decodedText = decode(encodedText, publicKey);
        System.out.println("Decoded Text : " + new String(decodedText));
        System.out.println("Signature is : " + verify(receivedCertificate, decodedText, signature));
    }
}
