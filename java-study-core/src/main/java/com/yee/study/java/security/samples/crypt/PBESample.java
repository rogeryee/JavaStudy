package com.yee.study.java.security.samples.crypt;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.Key;
import java.security.SecureRandom;

/**
 * PBE加解密示例
 *
 * @author Roger.Yi
 */
public class PBESample
{
    public static final String src = "hello world";

    public static void main(String[] args)
    {
        jdkPBE();

    }

    /**
     * JDK实现
     */
    public static void jdkPBE()
    {
        try
        {
            // 初始化盐(加密的随机数)
            SecureRandom random = new SecureRandom();//产生随机数
            byte[] salt = random.generateSeed(8);

            // 口令与密钥
            String password = "zhangyaohui";//定义用户自己输入的口令
            PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray()); //将口令转换为KEY
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");//实例化转换为KEY的工厂
            Key key = factory.generateSecret(pbeKeySpec);


            // 加密
            PBEParameterSpec pbeParameterSpac = new PBEParameterSpec(salt, 100);//100为需要迭代的次数
            Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");
            cipher.init(Cipher.ENCRYPT_MODE, key, pbeParameterSpac);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("jdk pbe encrypt:" + Hex.encodeHexString(result));

            // 解密
            cipher.init(Cipher.DECRYPT_MODE, key, pbeParameterSpac);
            result = cipher.doFinal(result);
            System.out.println("jdk pbe decrypt:" + new String(result));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
