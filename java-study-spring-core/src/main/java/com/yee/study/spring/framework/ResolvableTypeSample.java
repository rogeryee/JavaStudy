package com.yee.study.spring.framework;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.List;

/**
 * @author Roger.Yi
 */
public class ResolvableTypeSample {

    private static final Logger log = LoggerFactory.getLogger(ResolvableTypeSample.class);

    /**
     *
     */
    @Test
    public void testJavaType() throws Exception {
        Class clazz = TypeClass.class;
        logInterfaces(clazz.getSimpleName(), clazz.getInterfaces());
        logConstructors(clazz.getSimpleName(), clazz.getConstructors());
        logMethods(clazz.getSimpleName(), clazz.getDeclaredMethods());
        logFields(clazz.getSimpleName(), clazz.getDeclaredFields());
    }

    private void logInterfaces(String baseClassName, Class<?>[] interfaces) {
        int i = 0;
        for (Class<?> inter : interfaces) {
            log.info("{}.interfaces[{}] name={}", baseClassName, i++, inter.getSimpleName());
        }
    }

    private void logConstructors(String baseClassName, Constructor<?>[] constructors) {
        int i = 0;
        for (Constructor<?> constructor : constructors) {
            log.info("{}.constructors[{}] name={} param.count={}", baseClassName, i++, constructor.getParameters(), constructor
                    .getParameterCount());
        }
    }

    private void logMethods(String baseClassName, Method[] methods) {
        int i = 0;
        for (Method method : methods) {
            log.info("{}.methods[{}] name={} parameters={}", baseClassName, i++, method.getName(), method.getParameters());
        }
    }

    private void logFields(String baseClassName, Field[] fields) {
        int i = 0;
        for (Field field : fields) {
            Type type = field.getGenericType();
            log.info("{}.field[{}] name=[{}] {}", baseClassName, i++, field.getName(), typeInfo(type));
        }
    }

    private String typeInfo(Type type) {
        String typeInfo = "";
        if (type instanceof ParameterizedType) {
            ParameterizedType pdType = (ParameterizedType) type;
            typeInfo += "ParameterizedType typeName=" + pdType.getTypeName()
                    + " actualType={" + typesInfo("args", pdType.getActualTypeArguments()) + "}";
        } else if (type instanceof GenericArrayType) {
            GenericArrayType gaType = (GenericArrayType) type;
            typeInfo += "GenericArrayType{componentType={" + typeInfo(gaType.getGenericComponentType()) + "}}";
        } else if (type instanceof WildcardType) {
            WildcardType wdType = (WildcardType) type;
            typeInfo += "WildcardType typeName=" + wdType.getTypeName()
                    + " lowBounds={" + typesInfo("low", wdType.getLowerBounds())
                    + "} upperBounds={" + typesInfo("uppper", wdType.getUpperBounds()) + "}";
        } else if (type instanceof TypeVariable) {
            TypeVariable vaType = (TypeVariable) type;
            typeInfo += "TypeVariable{typeName=" + vaType.getTypeName()
                    + " bounds={" + typesInfo("bounds", vaType.getBounds()) + "}}";
        } else {
            typeInfo += "GeneralType{class=" + type.getClass()
                    .getSimpleName() + " typeName=" + type.getTypeName() + "}";
        }
        return typeInfo;
    }

    private String typesInfo(String base, Type[] types) {
        if (types == null || types.length == 0) {
            return "nil";
        }

        String template = "%s[%s]=%s";
        int i = 0;
        String ret = "";
        for (Type type : types) {
            ret += String.format(template, base, i++, typeInfo(type));
        }
        return ret;
    }
}

interface A {
}

interface B {
}

interface C extends B {
}

class D {
}

class E extends D {
    public String name;
}

class TypeClass<T> extends E implements A, C {

    public String id;

    // GenericArrayType
    public T[] a;

    // GenericArrayType
    public List<?>[] b;

    // WildcardType
    public List<? extends Integer> c;

    // Class
    private List d;

    // Variable
    protected T e;

    // ParameterizedType
    protected List<T> f;

    protected List<String> g;

    public TypeClass(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
