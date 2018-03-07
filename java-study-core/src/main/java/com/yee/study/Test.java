package com.yee.study;

import com.yee.study.util.MapUtil;
import com.yee.study.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Roger.Yi
 */
public class Test
{
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

    public static void main(String[] args)
    {
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
}
