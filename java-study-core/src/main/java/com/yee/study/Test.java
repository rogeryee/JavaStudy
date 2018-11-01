package com.yee.study;

import com.yee.study.util.DateUtil;
import com.yee.study.util.MapUtil;
import com.yee.study.util.StringUtil;
import org.bouncycastle.util.encoders.Hex;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Roger.Yi
 */
public class Test
{
    public static void main(String[] args) {
        System.out.println(initApplyNo(1234567l));
        System.out.println(initApplyNo(123456789l));
    }

    private static String initApplyNo(Long loanApplyId)
    {
        StringBuilder sb = new StringBuilder("");

        String id = String.valueOf(loanApplyId);
        int length = id.length();
        if (length < 9)
        {
            for (int i = 0; i < 5 - length; i++)
            {
                sb.append("0");
            }
        }
        sb.append(id);
        String dateStr = (new SimpleDateFormat("yyyyMMdd")).format(new Date());
        return "021" + dateStr + sb.toString();
    }

    /**
     * 转换推送内容
     *
     * @param content
     * @param params
     * @return
     */
    public static String convertContentWithParams(String content, Map<String, String> params)
    {
        if (StringUtil.isBlank(content) || MapUtil.isEmpty(params))
            return content;

        String regex = "\\$\\{(.+?)\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        /*
         * sb用来存储替换过的内容，它会把多次处理过的字符串按源字符串序
         * 存储起来。
         */
        StringBuffer sb = new StringBuffer();
        while (matcher.find())
        {
            String name = matcher.group(1);//键名
            String value = params.get(name);//键值
            if (StringUtil.isBlank(value))
            {
                value = "";
            }
            else
            {
                value = value.replaceAll("\\$", "\\\\\\$");
            }
            matcher.appendReplacement(sb, value);
        }
        //最后还得要把尾串接到已替换的内容后面去，这里尾串为“，欢迎下次光临！”
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static void main1(String[] args)
    {
        List<String> list = new ArrayList<>();
        list.add("0");

        List<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");

        list.retainAll(list1);
        System.out.println(list);

        String s = "-3032829814";
        System.out.println(Integer.parseInt(s));

        String s1 = (String)null;
        Integer s2 = (Integer)null;

        String aa = "Hello World";
        byte[] bytes = aa.getBytes();
        
        System.out.println("[" + byteToHexString(bytes) + "]");
        System.out.println("[" + org.apache.commons.codec.binary.Hex.encodeHexString(bytes) + "]");
        System.out.println("[" + new String(Hex.decode(org.apache.commons.codec.binary.Hex.encodeHexString(bytes))) + "]");

        Date date = DateUtil.rollDate(new Date(), Calendar.DATE, 180);
        System.out.println("date = " + date);

        ArrayList a;
        String content = "Hi ${name}, welcome to ${city}. Hey ${name}!";
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", "Roger");
        params.put("city", "Shanghai");

        System.out.println(convertContentWithParams(content, params));

        content = "Hi ${name}, welcome to ${city}. Hey ${name2}!";
        System.out.println(convertContentWithParams(content, params));

        System.out.println(Boolean.parseBoolean(null));
        System.out.println(Boolean.parseBoolean("true"));
        System.out.println(Boolean.parseBoolean("false"));
        System.out.println(Boolean.parseBoolean("FalSe"));
        System.out.println(Boolean.parseBoolean("null"));
        System.out.println(Boolean.parseBoolean(""));
        System.out.println(Boolean.parseBoolean("   "));

        System.out.println(filterName(""));
        System.out.println(filterName(null));
        System.out.println(filterName("乔丽潘·马木提"));
        System.out.println(filterName("乔丽潘.马木提"));
        System.out.println(filterName("乔丽潘·马木.提"));
        System.out.println(filterName("乔丽潘112212马木提"));
    }

    private static String filterName(String origin)
    {
        if(StringUtil.isBlank(origin))
            return origin;

        return origin = origin.replaceAll("[\\.\\·]", "");
    }

    /**
     * 二进制转16进制
     *
     * @param bytes
     * @return
     */
    public static String byteToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String strHex = Integer.toHexString(bytes[i]);
            if (strHex.length() > 3) {
                sb.append(strHex.substring(6));
            } else {
                if (strHex.length() < 2) {
                    sb.append("0" + strHex);
                } else {
                    sb.append(strHex);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 十六进制转二进制
     *
     * @param s
     * @return
     */
    public static byte[] hexStringToByte(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                System.out.println("十六进制转byte发生错误！！！");
                e.printStackTrace();
            }
        }
        return baKeyword;
    }
}
