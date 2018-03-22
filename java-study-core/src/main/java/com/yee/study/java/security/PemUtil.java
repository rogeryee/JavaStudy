package com.yee.study.java.security;

import com.yee.study.util.IOUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;

/**
 * @author Roger.Yi
 */
public class PemUtil
{
    private static Logger logger = LoggerFactory.getLogger(PemUtil.class);

    /**
     * 字节数据转字符串专用集合
     */
    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 从文件中输入流中加载公钥
     *
     * @param file 公钥输入流
     * @throws Exception 加载公钥时产生的异常
     */
    public static PublicKey getPublicKey(File file) throws Exception
    {
        try
        {
            InputStream inputStream = new FileInputStream(file);
            List<String> content = IOUtil.readLines(inputStream);
            StringBuffer sb = new StringBuffer("");
            for (String line : content)
            {
                if (line.charAt(0) == '-')
                {
                    continue;
                }
                else
                {
                    sb.append(line);
                    sb.append('\r');
                }
            }

            return getPublicKey(sb.toString());
        }
        catch (IOException e)
        {
            logger.error("Get public key meet error.", e);
            throw new RuntimeException("Get public key meet error.");
        }
    }


    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     */
    public static PublicKey getPublicKey(String publicKeyStr) throws Exception
    {
        try
        {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return keyFactory.generatePublic(keySpec);
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e)
        {
            logger.error("Get public key meet error.", e);
            throw new RuntimeException("Get public key meet error.");
        }
    }

    /**
     * 从文件中加载私钥
     *
     * @param file 私钥文件名
     * @return 是否成功
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(File file) throws Exception
    {
        try
        {
            InputStream inputStream = new FileInputStream(file);
            List<String> content = IOUtil.readLines(inputStream);
            StringBuffer sb = new StringBuffer("");
            for (String line : content)
            {
                if (line.charAt(0) == '-')
                {
                    continue;
                }
                else
                {
                    sb.append(line);
                    sb.append('\r');
                }
            }

            return getPrivateKey(sb.toString());
        }
        catch (IOException e)
        {
            logger.error("Get private key meet error.", e);
            throw new RuntimeException("Get private key meet error.");
        }
    }

    public static PrivateKey getPrivateKey(String content) throws Exception
    {
        try
        {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] buffer = base64Decoder.decodeBuffer(content);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e)
        {
            logger.error("get private key meet error.", e);
            throw new RuntimeException("get private key meet error.");
        }
    }

    /**
     * 加密过程
     *
     * @param key           密钥
     * @param plainTextData 明文数据
     * @return
     * @throws Exception 加密过程中的异常信息
     */
    public static byte[] encrypt(Key key, byte[] plainTextData) throws Exception
    {
        try
        {
            Cipher cipher = Cipher.getInstance(key.getAlgorithm(), new BouncyCastleProvider());
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] output = cipher.doFinal(plainTextData);
            return output;
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e)
        {
            throw new RuntimeException("encrypt meet error.");
        }
    }

    /**
     * 解密过程
     *
     * @param key        密钥
     * @param cipherData 密文数据
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static byte[] decrypt(Key key, byte[] cipherData) throws Exception
    {
        try
        {
            Cipher cipher = Cipher.getInstance(key.getAlgorithm(), new BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] output = cipher.doFinal(cipherData);
            return output;
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e)
        {
            throw new Exception("无此解密算法");
        }
    }

    /**
     * 字节数据转十六进制字符串
     *
     * @param data 输入数据
     * @return 十六进制内容
     */
    public static String byteArrayToString(byte[] data)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < data.length; i++)
        {
            //取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移
            stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);
            //取出字节的低四位 作为索引得到相应的十六进制标识符
            stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
            if (i < data.length - 1)
            {
                stringBuilder.append(' ');
            }
        }
        return stringBuilder.toString();
    }
}
