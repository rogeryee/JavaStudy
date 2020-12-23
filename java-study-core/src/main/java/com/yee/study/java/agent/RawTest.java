package com.yee.study.java.agent;

import com.yee.study.util.ArrayUtil;
import com.yee.study.util.StringUtil;
import lombok.Data;
import org.apache.commons.jxpath.AbstractFactory;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.Pointer;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Roger.Yi
 */
public class RawTest {

    /**
     * 忽略属性集
     */
    private static Set<String> ignoreProperties = new HashSet<String>();

    static {
        ignoreProperties.add("class");
    }

    public static void main(String[] args) throws Exception {
        Map<String, AttributeBean> attrMap = parseAttr(RawReport.class);
        RawReport rawReport = new RawReport();
        rawReport.setAgent_code("testAgentCode");
        rawReport.setId(123L);
        rawReport.setOverall_rank(1001);
        rawReport.setOverall_score(new BigDecimal(45.5));

//        AgentReport report = new AgentReport();
//        JXPathContext context = JXPathContext.newContext(report);
//        context.setFactory(new AbstractFactory() {
//            @Override
//            public boolean createObject(JXPathContext context, Pointer pointer, Object parent, String name, int index) {
//                if(parent instanceof AgentReport && "overall".equals(name)) {
//                    ((AgentReport) parent).setOverall(new BehaviorInfo());
//                    return true;
//                }
//                return super.createObject(context, pointer, parent, name, index);
//            }
//        });
//
//        attrMap.entrySet().stream().forEach(entry -> {
//            AttributeBean attrBean = entry.getValue();
//            Object value = getFieldValue(rawReport, attrBean.getSrcField());
//            context.createPathAndSetValue(attrBean.getDesc(), value);
//        });
    }

    /**
     * 获取obj的字段定义
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Field getField(Object obj, String fieldName) {
        if (obj == null || fieldName == null) {
            return null;
        }

        Class<?> clazz = null;
        if (obj instanceof Class) {
            clazz = ((Class<?>) obj);
        } else {
            clazz = obj.getClass();
        }

        Field f = null;
        try {
            f = clazz.getDeclaredField(fieldName);
        } catch (Exception e) {
        }

        if (f == null) {
            return getField(clazz.getSuperclass(), fieldName);
        }

        return f;
    }

    public static Field getField(Class<?> clazz, String fieldName) {
        Field f = null;
        try {
            f = clazz.getDeclaredField(fieldName);
        } catch (Exception e) {
        }

        if (f == null) {
            return getField(clazz.getSuperclass(), fieldName);
        }

        return f;
    }

    public static <T> T getFieldValue(Object obj, Field field) {
        if (obj == null || field == null) {
            return null;
        }

        if (field.isAccessible() == false) {
            field.setAccessible(true);
        }

        try {
            return (T) field.get(obj);
        } catch (Exception e) {
            throw new RuntimeException(
                    String.format("Get field value error, obj=%s, fieldName=%s", obj, field.getName()), e);
        }
    }

    private static Map<String, AttributeBean> parseAttr(Class<?> clazz) throws Exception {
        Map<String, AttributeBean> attrMap = new HashMap<>();
        BeanInfo info = java.beans.Introspector.getBeanInfo(clazz);
        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            if (ignoreProperties.contains(pd.getName())) {
                continue;
            }

            Method m = pd.getReadMethod();
            if (m == null) {
                continue;
            }

            Field field = clazz.getDeclaredField(pd.getName());
//            if (field.isAnnotationPresent(Ignored.class)) {
//                continue;
//            }

            if (field.isAnnotationPresent(Attribute.class)) {
                Attribute attribute = field.getAnnotation(Attribute.class);
                AttributeBean attrBean = new AttributeBean();
//                attrBean.setDesc(attribute.dest());
//                attrBean.setSrc(StringUtil.isBlank(attribute.src()) ? field.getName() : attribute.src());
                attrBean.setSrcField(field);
                attrMap.put(attrBean.getSrc(), attrBean);
            }

            if (field.isAnnotationPresent(Nested.class)) {
                Nested attribute = field.getAnnotation(Nested.class);
                String baseDest = attribute.property();

                Stream.of(attribute.attrs()).forEach(mapper -> {
                    AttributeBean attrBean = new AttributeBean();
//                    attrBean.setDesc(baseDest + "/" + mapper.dest());
//                    attrBean.setSrc(StringUtil.isBlank(mapper.src()) ? field.getName() : mapper.src());
//                    attrBean.setSrcField(getField(RawReport.class, mapper.src()));
                    attrMap.put(attrBean.getSrc(), attrBean);
                });
            }
        }

        return attrMap;
    }
}

@Data
class RawReport {
    private Long id;

    private String agent_code;

    private BigDecimal overall_score;
    private Integer overall_rank;

    private BigDecimal sr_score;
    private Integer sr_rank;

    private Integer share_article_cnt;
    private BigDecimal share_article_cnt_score;

    private Integer share_poster_cnt;
    private BigDecimal share_poster_cnt_score;

    private Integer login_app_cnt;
    private BigDecimal login_app_cnt_score;

    private BigDecimal op_score;
    private Integer op_rank;
}

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Report {
    Class clazz();
}

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Attribute {
    String raw() default "";
    String property() default "";
}

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Nested {
    String property();

    Attribute[] attrs();
}

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NestedCollection {
    String group();

    String property();

    Attribute[] attrs();
}

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Mapper {
    String src() default "";

    String dest() default "";
}

@Data
class AttributeBean {

    private String src;

    private String desc;

    private Field srcField;
}