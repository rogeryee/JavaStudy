package com.yee.study.java.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * Keystore工具类
 *
 * @author Roger.Yi
 */
public class KeystoreUtil
{
    private static Logger logger = LoggerFactory.getLogger(KeystoreUtil.class);

    /**
     * 加载密钥库，与Properties文件的加载类似，都是使用load方法
     *
     * @throws IOException
     */
    public static KeyStore getKeyStore(String keystorePwd, File keystore) throws IOException
    {
        InputStream inputStream = null;
        try
        {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            inputStream = new FileInputStream(keystore);
            keyStore.load(inputStream, keystorePwd.toCharArray());
            return keyStore;
        }
        catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e)
        {
            logger.error("get keystore meet error", e);
        }
        finally
        {
            if (inputStream != null)
            {
                inputStream.close();
            }
        }
        return null;
    }

    /**
     * 获取私钥
     *
     * @param keyStore
     * @param alias
     * @param password
     * @return
     */
    public static PrivateKey getPrivateKey(KeyStore keyStore, String alias, String password)
    {
        try
        {
            return (PrivateKey) keyStore.getKey(alias, password.toCharArray());
        }
        catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e)
        {
            logger.error("get private key meet error", e);
        }
        return null;
    }

    /**
     * 获取公钥
     *
     * @param certificate
     * @return
     */
    public static PublicKey getPublicKey(Certificate certificate)
    {
        return certificate.getPublicKey();
    }

    /**
     * 通过密钥库获取数字证书，不需要密码，因为获取到Keystore实例
     *
     * @param keyStore
     * @param alias
     * @return
     */
    public static X509Certificate getCertificateByKeystore(KeyStore keyStore, String alias)
    {
        try
        {
            return (X509Certificate) keyStore.getCertificate(alias);
        }
        catch (KeyStoreException e)
        {
            logger.error("get certificate meet error", e);
        }
        return null;
    }

    /**
     * 通过证书路径生成证书，与加载密钥库差不多，都要用到流。
     *
     * @param certFile
     * @param certType
     * @return
     * @throws IOException
     */
    public static X509Certificate getCertificateByCertPath(File certFile, String certType) throws IOException
    {
        InputStream inputStream = null;
        try
        {
            // 实例化证书工厂
            CertificateFactory factory = CertificateFactory.getInstance(certType);

            // 取得证书文件流
            inputStream = new FileInputStream(certFile);

            // 生成证书
            Certificate certificate = factory.generateCertificate(inputStream);

            return (X509Certificate) certificate;
        }
        catch (CertificateException | IOException e)
        {
            logger.error("get certificate meet error", e);
        }
        finally
        {
            if (null != inputStream)
            {
                inputStream.close();
            }
        }
        return null;

    }

    /**
     * 从证书中获取加密算法，进行签名
     * 如果要从密钥库获取签名算法的名称，只能将其强制转换成X509标准，JDK 6只支持X.509类型的证书
     *
     * @param certificate
     * @param privateKey
     * @param plainText
     * @return
     */
    public static byte[] sign(X509Certificate certificate, PrivateKey privateKey, byte[] plainText)
    {
        try
        {
            Signature signature = Signature.getInstance(certificate.getSigAlgName());
            signature.initSign(privateKey);
            signature.update(plainText);
            return signature.sign();
        }
        catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e)
        {
            logger.error("sign meet error", e);
        }

        return null;
    }

    /**
     * 验签，公钥包含在证书里面
     *
     * @param certificate
     * @param decodedText
     * @param receivedignature
     * @return
     */
    public static boolean verify(X509Certificate certificate, byte[] decodedText, final byte[] receivedignature)
    {
        try
        {
            Signature signature = Signature.getInstance(certificate.getSigAlgName());
            signature.initVerify(certificate); // 注意这里用到的是证书，实际上用到的也是证书里面的公钥
            signature.update(decodedText);
            return signature.verify(receivedignature);
        }
        catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e)
        {
            logger.error("verify meet error", e);
        }
        return false;
    }

    /**
     * 加密。注意密钥是可以获取到它适用的算法的。
     *
     * @param plainText
     * @param privateKey
     * @return
     */
    public static byte[] encode(byte[] plainText, PrivateKey privateKey)
    {
        try
        {
            Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return cipher.doFinal(plainText);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e)
        {
            logger.error("encode meet error", e);
        }

        return null;

    }

    /**
     * 解密，注意密钥是可以获取它适用的算法的。
     *
     * @param encodedText
     * @param publicKey
     * @return
     */
    public static byte[] decode(byte[] encodedText, PublicKey publicKey)
    {
        try
        {
            Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return cipher.doFinal(encodedText);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e)
        {
            logger.error("decode meet error", e);
        }

        return null;
    }
}
