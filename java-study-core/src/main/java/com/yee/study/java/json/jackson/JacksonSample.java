package com.yee.study.java.json.jackson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Jackson 使用示例
 *
 * @author Roger.Yi
 */
public class JacksonSample {

    private static final Logger logger = LoggerFactory.getLogger(JacksonSample.class);

    public static void main(String[] args) throws Exception {
        testSimpleObject();
        testMap();
        testAnnotation();
    }

    /**
     * 测试JavaBean的序列化
     *
     * @throws Exception
     */
    public static void testSimpleObject() throws Exception {
        // ObjectMapper对象，用于序列化和反序列化
        ObjectMapper mapper = new ObjectMapper();
        configure(mapper); // 配置

        Friend friend = new Friend("Roger", 25);

        // 写为字符串
        String text = mapper.writeValueAsString(friend);
        logger.info("object2jsonStr1 = " + text);

        // 写为字节流
        byte[] bytes = mapper.writeValueAsBytes(friend);
        logger.info("object2jsonStr2 = " + String.valueOf(bytes));

        // 从字符串中读取
        Friend newFriend = mapper.readValue(text, Friend.class);
        logger.info("jsonStr2Object1 = " + newFriend);

        // 从字节流中读取
        newFriend = mapper.readValue(bytes, Friend.class);
        logger.info("jsonStr2Object2 = " + newFriend);
    }

    /**
     * 测试Map的序列化
     *
     * @throws Exception
     */
    public static void testMap() throws Exception {
        // ObjectMapper对象，用于序列化和反序列化
        ObjectMapper mapper = new ObjectMapper();

        // 从集合Map写为字符串
        Map<String, Object> map = new HashMap<>();
        map.put("age", 26);
        map.put("name", "Phoebe");
        map.put("interests", new String[]{"pc games", "music"});

        String text = mapper.writeValueAsString(map);
        logger.info("map2jsonStr1 = " + text);

        // 从字符串写为集合Map，由于Java的类型擦除，所以类型需要用new TypeReference<T>来表示
        Map<String, Object> map2 = mapper.readValue(text, new TypeReference<Map<String, Object>>() {});
        logger.info("jsonStr2Map = " + map);

        JsonNode root = mapper.readTree(text);
        String name = root.get("name").asText();
        int age = root.get("age").asInt();
        logger.info("name=" + name + ", age=" + age);
    }

    /**
     * 测试使用Jackson Annotation
     *
     * @throws Exception
     */
    public static void testAnnotation() throws Exception {
        // 创建ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // 转换为字符串
        FriendDetail fd = new FriendDetail("Roger", 25, "", 0);
        String text = mapper.writeValueAsString(fd);
        logger.info("object2jsonStr1 = " + text);

        // 转换为对象
        FriendDetail fd2 = mapper.readValue(text, FriendDetail.class);
        logger.info("json2Object = " + fd2);
    }

    /**
     * 配置ObjectMapper
     *
     * @param mapper
     */
    public static void configure(ObjectMapper mapper) {
        // 美化输出
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // 允许序列化空的POJO类（否则会抛出异常）
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        // 把java.util.Date, Calendar输出为数字（时间戳）
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // 在遇到未知属性的时候不抛出异常
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // 强制JSON 空字符串("")转换为null对象值:
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        // 在JSON中允许C/C++ 样式的注释(非标准，默认禁用)
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);

        // 允许没有引号的字段名（非标准）
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        // 允许单引号（非标准）
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        // 强制转义非ASCII字符
        mapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);

        // 将内容包裹为一个JSON属性，属性名由@JsonRootName注解指定
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
    }
}

/**
 * 简单POJO
 */
class Friend {
    private String nickname;

    private int age;

    public Friend() {
    }

    public Friend(String nickname, int age) {
        this.nickname = nickname;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Friend{" + "nickname='" + nickname + '\'' + ", age=" + age + '}';
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

/**
 * 使用JacksonAnnotation的POJO
 */
@JsonRootName("FriendDetailObject")
@JsonIgnoreProperties({"uselessProp1"})
class FriendDetail {
    @JsonProperty("NickName")
    private String name;

    @JsonProperty("Age")
    private int age;

    private String uselessProp1;

    @JsonIgnore
    private int uselessProp2;

    public FriendDetail() {}

    public FriendDetail(String name, int age, String uselessProp1, int uselessProp2) {
        this.name = name;
        this.age = age;
        this.uselessProp1 = uselessProp1;
        this.uselessProp2 = uselessProp2;
    }

    @Override
    public String toString() {
        return "FriendDetail{" + "name='" + name + '\'' + ", age=" + age + ", uselessProp1='" + uselessProp1 + '\'' + ", uselessProp2=" + uselessProp2 + '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUselessProp1() {
        return uselessProp1;
    }

    public void setUselessProp1(String uselessProp1) {
        this.uselessProp1 = uselessProp1;
    }

    public int getUselessProp2() {
        return uselessProp2;
    }

    public void setUselessProp2(int uselessProp2) {
        this.uselessProp2 = uselessProp2;
    }
}
