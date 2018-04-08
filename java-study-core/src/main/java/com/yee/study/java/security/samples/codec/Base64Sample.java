package com.yee.study.java.security.samples.codec;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Base64的实现示例
 *
 * @author Roger.Yi
 */
public class Base64Sample
{
    public static final String TEXT = "hello world";

    public static void main(String[] args) throws Exception
    {
        jdkBase64();
        commonsCodesBase64();
        bouncyCastleBase64();
    }

    /**
     * JDK实现
     */
    public static void jdkBase64() throws Exception
    {
        System.out.println("------------- JDK -------------");
        BASE64Encoder encoder = new BASE64Encoder();
        String encode = encoder.encode(TEXT.getBytes());
        System.out.println("encode:" + encode);

        BASE64Decoder decoder = new BASE64Decoder();
        System.out.println("decode:" + new String(decoder.decodeBuffer(encode)));
    }

    /**
     * Apache的common codes实现
     */
    public static void commonsCodesBase64()
    {
        System.out.println("------------- Apache Common -------------");
        byte[] encodeBytes = Base64.encodeBase64(TEXT.getBytes());
        System.out.println("common codes encode:" + new String(encodeBytes));

        byte[] decodeBytes = Base64.decodeBase64(encodeBytes);
        System.out.println("common codes decode:" + new String(decodeBytes));
    }

    /**
     * bouncy castle实现
     */
    public static void bouncyCastleBase64()
    {
        System.out.println("------------- Bouncy Castle -------------");
        byte[] encodeBytes = org.bouncycastle.util.encoders.Base64.encode(TEXT.getBytes());
        System.out.println("bouncy castle encode:" + new String(encodeBytes));

        byte[] decodeBytes = org.bouncycastle.util.encoders.Base64.decode(encodeBytes);
        System.out.println("bouncy castle decode:" + new String(decodeBytes));
    }
}
