package com.yee.study.util;

import com.yee.study.util.BeanUtil.CopyPropModes;
import com.yee.study.util.ObjectUtilTest.Animal;
import com.yee.study.util.ObjectUtilTest.Person;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/**
 * BeanUtil测试类
 *
 * @author Roger.Yi
 */
public class BeanUtilTest
{
    /**
     * 测试getPropertyNames方法
     */
    @Test
    public void testGetPropertyNames()
    {
        // 1
        List<String> propNames = BeanUtil.getPropertyNames(BeanA.class);
        List<String> expPropNames = ArrayUtil.asList("p");

        Assert.assertEquals(expPropNames.size(), propNames.size());
        Assert.assertTrue(propNames.containsAll(expPropNames));

        // 2
        propNames = BeanUtil.getPropertyNames(BeanB.class);
        expPropNames = ArrayUtil.asList("p", "p0", "p1", "p2", "p3");

        Assert.assertEquals(expPropNames.size(), propNames.size());
        Assert.assertTrue(propNames.containsAll(expPropNames));

        // 3
        propNames = BeanUtil.getPropertyNames(BeanB.class, "p", "p2");
        expPropNames = ArrayUtil.asList("p0", "p1", "p3");

        Assert.assertEquals(expPropNames.size(), propNames.size());
        Assert.assertTrue(propNames.containsAll(expPropNames));

        // 4
        Assert.assertNull(BeanUtil.getPropertyNames(null, "p", "p2"));
    }

    /**
     * 测试getPropertyNamesWithDiffValue方法
     */
    @Test
    public void testGetPropertyNamesWithDiffValue()
    {
        Person p1 = new Person("Mr.X", 30);
        Person p2 = new Person("Mr.X", 60);
        Animal a1 = new Animal(30);
        Animal a2 = new Animal(10);

        // person vs person
        List<String> propNames = BeanUtil.getPropertyNamesWithDiffValue(p1, p1);
        Assert.assertEquals(0, propNames.size());

        propNames = BeanUtil.getPropertyNamesWithDiffValue(p1, p1, "age", "name", "sex"); // sex-ignored
        Assert.assertEquals(0, propNames.size());

        propNames = BeanUtil.getPropertyNamesWithDiffValue(p1, p2);
        Assert.assertEquals(1, propNames.size());
        Assert.assertEquals("age", propNames.get(0));

        propNames = BeanUtil.getPropertyNamesWithDiffValue(p1, p2, "name");
        Assert.assertEquals(0, propNames.size());

        propNames = BeanUtil.getPropertyNamesWithDiffValue(p1, p2, "age");
        Assert.assertEquals(1, propNames.size());
        Assert.assertEquals("age", propNames.get(0));

        propNames = BeanUtil.getPropertyNamesWithDiffValue(p1, p2, "sex"); // sex-ignored
        Assert.assertEquals(0, propNames.size());

        // animal vs animal
        propNames = BeanUtil.getPropertyNamesWithDiffValue(a1, a2);
        Assert.assertEquals(1, propNames.size());
        Assert.assertEquals("age", propNames.get(0));

        // person vs animal
        propNames = BeanUtil.getPropertyNamesWithDiffValue(p1, a1);
        Assert.assertEquals(0, propNames.size());

        propNames = BeanUtil.getPropertyNamesWithDiffValue(p1, a1, "name", "age", null); // name-ignored
        Assert.assertEquals(0, propNames.size());

        propNames = BeanUtil.getPropertyNamesWithDiffValue(p1, a2);
        Assert.assertEquals(1, propNames.size());
        Assert.assertEquals("age", propNames.get(0));

        // null
        propNames = BeanUtil.getPropertyNamesWithDiffValue(null, a1);
        Assert.assertEquals(0, propNames.size());

        propNames = BeanUtil.getPropertyNamesWithDiffValue(a1, null);
        Assert.assertEquals(0, propNames.size());

        propNames = BeanUtil.getPropertyNamesWithDiffValue(null, null);
        Assert.assertEquals(0, propNames.size());
    }

    /**
     * 测试asMap方法
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testAsMap()
    {
        BeanC bean = genBean();

        // asMap(obj, ignoredProps)
        Map<String, Object> propMap = BeanUtil.asMap(bean);
        Assert.assertEquals(13, propMap.size());
        Assert.assertEquals((BeanA) propMap.get("p"), bean.getP());
        Assert.assertEquals(((Boolean) propMap.get("p0")).booleanValue(), bean.p0);
        Assert.assertEquals(((Integer) propMap.get("p1")).intValue(), bean.p1);
        Assert.assertArrayEquals((byte[]) propMap.get("p2"), bean.p2);
        Assert.assertEquals((String) propMap.get("p3"), bean.p3);
        Assert.assertEquals((BigDecimal) propMap.get("p4"), bean.p4);
        Assert.assertEquals((Date) propMap.get("p5"), bean.p5);
        Assert.assertArrayEquals((Integer[]) propMap.get("p6"), bean.p6);
        Assert.assertEquals((Class<String>) propMap.get("p7"), bean.p7);
        Assert.assertEquals((List<BeanC>) propMap.get("p8"), bean.p8);
        Assert.assertEquals((Map<BeanC, BeanC>) propMap.get("p9"), bean.p9);
        Assert.assertEquals(((Byte) propMap.get("p10")).byteValue(), bean.p10);
        Assert.assertEquals(((Long) propMap.get("p11")).longValue(), bean.p11);

        // asMap(obj, excludedProps)
        propMap = BeanUtil.asMap(bean, "p0", "p1", "p2", "p3");
        Assert.assertEquals(9, propMap.size());
        Assert.assertEquals((BeanA) propMap.get("p"), bean.getP());
        Assert.assertEquals((BigDecimal) propMap.get("p4"), bean.p4);
        Assert.assertEquals((Date) propMap.get("p5"), bean.p5);
        Assert.assertArrayEquals((Integer[]) propMap.get("p6"), bean.p6);
        Assert.assertEquals((Class<String>) propMap.get("p7"), bean.p7);
        Assert.assertEquals((List<BeanC>) propMap.get("p8"), bean.p8);
        Assert.assertEquals((Map<BeanC, BeanC>) propMap.get("p9"), bean.p9);
        Assert.assertEquals(((Byte) propMap.get("p10")).byteValue(), bean.p10);
        Assert.assertEquals(((Long) propMap.get("p11")).longValue(), bean.p11);

        // asMap(obj, excludedProps)
        propMap = BeanUtil.asMap(bean, ArrayUtil.asList("p0", "p1", "p2", "p3"));
        Assert.assertEquals(9, propMap.size());
        Assert.assertEquals((BeanA) propMap.get("p"), bean.getP());
        Assert.assertEquals((BigDecimal) propMap.get("p4"), bean.p4);
        Assert.assertEquals((Date) propMap.get("p5"), bean.p5);
        Assert.assertArrayEquals((Integer[]) propMap.get("p6"), bean.p6);
        Assert.assertEquals((Class<String>) propMap.get("p7"), bean.p7);
        Assert.assertEquals((List<BeanC>) propMap.get("p8"), bean.p8);
        Assert.assertEquals((Map<BeanC, BeanC>) propMap.get("p9"), bean.p9);
        Assert.assertEquals(((Byte) propMap.get("p10")).byteValue(), bean.p10);
        Assert.assertEquals(((Long) propMap.get("p11")).longValue(), bean.p11);

        Assert.assertNull(BeanUtil.asMap(null));

        // asMapWithProps(obj, includedProps)
        propMap = BeanUtil.asMapWithProps(bean, "p0", "p1", "p2", "p3");
        Assert.assertEquals(4, propMap.size());
        Assert.assertEquals(((Boolean) propMap.get("p0")).booleanValue(), bean.p0);
        Assert.assertEquals(((Integer) propMap.get("p1")).intValue(), bean.p1);
        Assert.assertArrayEquals((byte[]) propMap.get("p2"), bean.p2);
        Assert.assertEquals((String) propMap.get("p3"), bean.p3);

        propMap = BeanUtil.asMapWithProps(bean, ArrayUtil.asList("p0", "p1", "p2", "p3"));
        Assert.assertEquals(4, propMap.size());
        Assert.assertEquals(((Boolean) propMap.get("p0")).booleanValue(), bean.p0);
        Assert.assertEquals(((Integer) propMap.get("p1")).intValue(), bean.p1);
        Assert.assertArrayEquals((byte[]) propMap.get("p2"), bean.p2);
        Assert.assertEquals((String) propMap.get("p3"), bean.p3);

        propMap = BeanUtil.asMapWithProps(bean);
        Assert.assertEquals(0, propMap.size()); // no-props

        propMap = BeanUtil.asMapWithProps(bean, new String[0]);
        Assert.assertEquals(0, propMap.size()); // no-props

        Assert.assertNull(BeanUtil.asMapWithProps(null));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCopyProperties()
    {
        // copyProperties(Map, Object, String[])
        BeanC rawBean = genBean();
        Map<String, Object> propMap = BeanUtil.asMap(rawBean);

        BeanC bean = new BeanC();
        Assert.assertSame(bean, BeanUtil.copyProperties(propMap, bean));

        Assert.assertEquals(13, propMap.size());
        Assert.assertEquals((BeanA) propMap.get("p"), bean.getP());
        Assert.assertEquals(((Boolean) propMap.get("p0")).booleanValue(), bean.p0);
        Assert.assertEquals(((Integer) propMap.get("p1")).intValue(), bean.p1);
        Assert.assertArrayEquals((byte[]) propMap.get("p2"), bean.p2);
        Assert.assertEquals((String) propMap.get("p3"), bean.p3);
        Assert.assertEquals((BigDecimal) propMap.get("p4"), bean.p4);
        Assert.assertEquals((Date) propMap.get("p5"), bean.p5);
        Assert.assertArrayEquals((Integer[]) propMap.get("p6"), bean.p6);
        Assert.assertEquals((Class<String>) propMap.get("p7"), bean.p7);
        Assert.assertEquals((List<BeanC>) propMap.get("p8"), bean.p8);
        Assert.assertEquals((Map<BeanC, BeanC>) propMap.get("p9"), bean.p9);
        Assert.assertEquals(((Byte) propMap.get("p10")).byteValue(), bean.p10);
        Assert.assertEquals(((Long) propMap.get("p11")).longValue(), bean.p11);

        bean = new BeanC();
        BeanUtil.copyProperties(propMap, bean, "p", "p0", "p1", "p2", "p3");

        Assert.assertNull(bean.getP());
        Assert.assertEquals(false, bean.p0);
        Assert.assertEquals(0, bean.p1);
        Assert.assertNull(bean.p2);
        Assert.assertNull(bean.p3);
        Assert.assertEquals((BigDecimal) propMap.get("p4"), bean.p4);
        Assert.assertEquals((Date) propMap.get("p5"), bean.p5);
        Assert.assertArrayEquals((Integer[]) propMap.get("p6"), bean.p6);
        Assert.assertEquals((Class<String>) propMap.get("p7"), bean.p7);
        Assert.assertEquals((List<BeanC>) propMap.get("p8"), bean.p8);
        Assert.assertEquals((Map<BeanC, BeanC>) propMap.get("p9"), bean.p9);
        Assert.assertEquals(((Byte) propMap.get("p10")).byteValue(), bean.p10);
        Assert.assertEquals(((Long) propMap.get("p11")).longValue(), bean.p11);

        // copyProperties(SOURCE_VALUE_NOT_NULL, Map, Object, String[])
        bean = new BeanC();
        bean.p2 = new byte[0];
        bean.p3 = "abc";

        propMap.put("p2", null);
        propMap.put("p3", null);

        BeanUtil.copyProperties(CopyPropModes.SOURCE_VALUE_NOT_NULL, propMap, bean, "p");

        Assert.assertNull(bean.getP());
        Assert.assertEquals(((Boolean) propMap.get("p0")).booleanValue(), bean.p0);
        Assert.assertEquals(((Integer) propMap.get("p1")).intValue(), bean.p1);
        Assert.assertArrayEquals(new byte[0], bean.p2); // no change
        Assert.assertEquals("abc", bean.p3); // no change
        Assert.assertEquals((BigDecimal) propMap.get("p4"), bean.p4);
        Assert.assertEquals((Date) propMap.get("p5"), bean.p5);
        Assert.assertArrayEquals((Integer[]) propMap.get("p6"), bean.p6);
        Assert.assertEquals((Class<String>) propMap.get("p7"), bean.p7);
        Assert.assertEquals((List<BeanC>) propMap.get("p8"), bean.p8);
        Assert.assertEquals((Map<BeanC, BeanC>) propMap.get("p9"), bean.p9);
        Assert.assertEquals(((Byte) propMap.get("p10")).byteValue(), bean.p10);
        Assert.assertEquals(((Long) propMap.get("p11")).longValue(), bean.p11);

        // copyProperties(TARGET_VALUE_NOT_SET, Map, Object, String[])
        bean = new BeanC();
        bean.p1 = 2;
        bean.p2 = new byte[0];
        bean.p3 = "abc";

        propMap.put("p2", new byte[1]);
        propMap.put("p3", "cba");

        BeanUtil.copyProperties(CopyPropModes.TARGET_VALUE_NOT_SET, propMap, bean, "p");

        Assert.assertNull(bean.getP());
        Assert.assertEquals(((Boolean) propMap.get("p0")).booleanValue(), bean.p0);
        Assert.assertEquals(2, bean.p1); // no change
        Assert.assertArrayEquals(new byte[0], bean.p2); // no change
        Assert.assertEquals("abc", bean.p3); // no change
        Assert.assertEquals((BigDecimal) propMap.get("p4"), bean.p4);
        Assert.assertEquals((Date) propMap.get("p5"), bean.p5);
        Assert.assertArrayEquals((Integer[]) propMap.get("p6"), bean.p6);
        Assert.assertEquals((Class<String>) propMap.get("p7"), bean.p7);
        Assert.assertEquals((List<BeanC>) propMap.get("p8"), bean.p8);
        Assert.assertEquals((Map<BeanC, BeanC>) propMap.get("p9"), bean.p9);
        Assert.assertEquals(((Byte) propMap.get("p10")).byteValue(), bean.p10); // changed-when-default-value
        Assert.assertEquals(((Long) propMap.get("p11")).longValue(), bean.p11); // changed-when-default-value

        // copyProperties(Object, Object, String[])
        bean = new BeanC();
        Assert.assertSame(bean, BeanUtil.copyProperties(rawBean, bean, "p", "p0", "p1", "p2", "p3"));

        Assert.assertNull(bean.getP());
        Assert.assertEquals(false, bean.p0);
        Assert.assertEquals(0, bean.p1);
        Assert.assertNull(bean.p2);
        Assert.assertNull(bean.p3);
        Assert.assertEquals(rawBean.p4, bean.p4);
        Assert.assertEquals(rawBean.p5, bean.p5);
        Assert.assertArrayEquals(rawBean.p6, bean.p6);
        Assert.assertEquals(rawBean.p7, bean.p7);
        Assert.assertEquals(rawBean.p8, bean.p8);
        Assert.assertEquals(rawBean.p9, bean.p9);
        Assert.assertEquals(rawBean.p10, bean.p10);
        Assert.assertEquals(rawBean.p11, bean.p11);

        bean = new BeanC();
        BeanUtil.copyProperties(rawBean, bean);

        Assert.assertEquals(rawBean.getP(), bean.getP());
        Assert.assertEquals(rawBean.p1, bean.p1);
        Assert.assertArrayEquals(rawBean.p2, bean.p2);
        Assert.assertEquals(rawBean.p3, bean.p3);
        Assert.assertEquals(rawBean.p4, bean.p4);
        Assert.assertEquals(rawBean.p5, bean.p5);
        Assert.assertArrayEquals(rawBean.p6, bean.p6);
        Assert.assertEquals(rawBean.p7, bean.p7);
        Assert.assertEquals(rawBean.p8, bean.p8);
        Assert.assertEquals(rawBean.p9, bean.p9);
        Assert.assertEquals(rawBean.p10, bean.p10);
        Assert.assertEquals(rawBean.p11, bean.p11);

        BeanB beanB = new BeanB();
        BeanUtil.copyProperties(rawBean, beanB, "p2", "p3"); // p2,p3-argument-type-mismatch

        Assert.assertEquals(rawBean.getP(), beanB.getP());
        Assert.assertEquals(rawBean.p1, beanB.p1.intValue());

        // copyProperties(SOURCE_VALUE_NOT_NULL, Object, Object, String[])
        bean = new BeanC();
        bean.p2 = new byte[0];
        bean.p3 = "123";

        BeanC bean2 = genBean();
        bean2.p2 = null;
        bean2.p3 = null;

        BeanUtil.copyProperties(CopyPropModes.SOURCE_VALUE_NOT_NULL, bean2, bean, "p");

        Assert.assertNull(bean.getP());
        Assert.assertEquals(bean2.p1, bean.p1);
        Assert.assertArrayEquals(new byte[0], bean.p2); // no change
        Assert.assertEquals("123", bean.p3); // no change
        Assert.assertEquals(bean2.p4, bean.p4);
        Assert.assertEquals(bean2.p5, bean.p5);
        Assert.assertArrayEquals(bean2.p6, bean.p6);
        Assert.assertEquals(bean2.p7, bean.p7);
        Assert.assertEquals(bean2.p8, bean.p8);
        Assert.assertEquals(bean2.p9, bean.p9);
        Assert.assertEquals(bean2.p10, bean.p10);
        Assert.assertEquals(bean2.p11, bean.p11);

        // copyProperties(SOURCE_VALUE_NOT_NULL, Object, Object, String[])
        bean = new BeanC();
        bean.p1 = 2;
        bean.p2 = new byte[0];
        bean.p3 = "abc";

        bean2.p2 = new byte[1];
        bean2.p3 = "cba";

        BeanUtil.copyProperties(CopyPropModes.TARGET_VALUE_NOT_SET, bean2, bean, "p");

        Assert.assertNull(bean.getP());
        Assert.assertEquals(2, bean.p1); // no change
        Assert.assertArrayEquals(new byte[0], bean.p2); // no change
        Assert.assertEquals("abc", bean.p3); // no change
        Assert.assertEquals(bean2.p4, bean.p4);
        Assert.assertEquals(bean2.p5, bean.p5);
        Assert.assertArrayEquals(bean2.p6, bean.p6);
        Assert.assertEquals(bean2.p7, bean.p7);
        Assert.assertEquals(bean2.p8, bean.p8);
        Assert.assertEquals(bean2.p9, bean.p9);
        Assert.assertEquals(bean2.p10, bean.p10); // changed-when-default-value
        Assert.assertEquals(bean2.p11, bean.p11); // changed-when-default-value
    }

    @Test
    public void testGetRWMethods() throws Exception
    {
        BeanC beanC = new BeanC();
        beanC.setP3("123");
        beanC.setP(beanC);

        // getReadMethod
        Method c_p_rm = BeanUtil.getReadMethod(BeanC.class, "p");
        Assert.assertNotNull(c_p_rm);
        Assert.assertSame(beanC, c_p_rm.invoke(beanC));

        Method c_p3_rm = BeanUtil.getReadMethod(BeanC.class, "p3");
        Assert.assertNotNull(c_p3_rm);
        Assert.assertEquals(beanC.getP3(), c_p3_rm.invoke(beanC));

        Method c_p100_rm = BeanUtil.getReadMethod(BeanC.class, "p100");
        Assert.assertNull(c_p100_rm);

        // getWriteMethod
        Method c_p_wm = BeanUtil.getWriteMethod(BeanC.class, "p");
        Assert.assertNotNull(c_p_wm);

        c_p_wm.invoke(beanC, (BeanC) null);
        Assert.assertNull(beanC.getP());
        Assert.assertNull(c_p_rm.invoke(beanC));

        Method c_p3_wm = BeanUtil.getWriteMethod(BeanC.class, "p3");
        Assert.assertNotNull(c_p3_wm);

        c_p3_wm.invoke(beanC, "abc");
        Assert.assertEquals("abc", beanC.getP3());
        Assert.assertEquals("abc", c_p3_rm.invoke(beanC));

        Method c_p100_wm = BeanUtil.getWriteMethod(BeanC.class, "p100");
        Assert.assertNull(c_p100_wm);
    }

    @Test
    public void testGetMethod()
    {
        final BeanA beanA = new BeanA();
        final BeanB beanB = new BeanB();
        final BeanC beanC = new BeanC();

        Assert.assertEquals("m1", BeanUtil.getMethod(beanA, "m1").getName());
        Assert.assertNull(BeanUtil.getMethod(beanA, "m2"));
        Assert.assertNull(BeanUtil.getMethod(beanA, "m3"));

        Assert.assertEquals("m1", BeanUtil.getMethod(beanB, "m1").getName());
        Assert.assertEquals("m2", BeanUtil.getMethod(beanB, "m2").getName());
        Assert.assertNull(BeanUtil.getMethod(beanB, "m3"));

        Assert.assertEquals("m1", BeanUtil.getMethod(beanC, "m1").getName());
        Assert.assertNull(BeanUtil.getMethod(beanC, "m2"));
        Assert.assertEquals("m3", BeanUtil.getMethod(beanC, "m3").getName());

        Assert.assertEquals(0, BeanUtil.getMethod(beanC, "m1", new Class[0]).getParameterTypes().length);
        Assert.assertEquals(1, BeanUtil.getMethod(beanC, "m1", new Class[]{String.class}).getParameterTypes().length);
        Assert.assertNull(BeanUtil.getMethod(beanC, "m1", new Class[]{Integer.class}));

        Assert.assertNull(BeanUtil.getMethod(beanA, null));
        Assert.assertNull(BeanUtil.getMethod(null, null));
    }

    @Test
    public void testInvokeMethod()
    {
        final BeanA beanA = new BeanA();
        final BeanB beanB = new BeanB();
        final BeanC beanC = new BeanC();

        Method m = BeanUtil.getMethod(beanA, "m1");
        Assert.assertEquals("BeanA.m1", BeanUtil.invokeMethod(beanA, m));
        Assert.assertEquals("BeanA.m1", BeanUtil.invokeMethod(beanA, "m1"));
        try
        {
            BeanUtil.invokeMethod(beanA, "m2"); // no such method
            Assert.fail("Expect exception");
        }
        catch (Exception ex)
        {
        }

        Assert.assertNull(BeanUtil.invokeMethodQuietly(beanC, "m2")); // sallow-exception

        Assert.assertEquals("BeanA.m1", BeanUtil.invokeMethod(beanB, "m1"));
        Assert.assertEquals("BeanB.m2.test", BeanUtil.invokeMethod(beanB, "m2", "test"));

        m = BeanUtil.getMethod(beanC, "m1", new Class[0]);
        Assert.assertEquals("BeanA.m1", BeanUtil.invokeMethod(beanC, m));

        try
        {
            BeanUtil.invokeMethod(beanC, m, "1"); // error arg num
            Assert.fail("Expect exception");
        }
        catch (Exception ex)
        {
        }

        Assert.assertNull(BeanUtil.invokeMethodQuietly(beanC, m, "1")); // sallow-exception

        m = BeanUtil.getMethod(beanC, "m1", new Class[]{String.class});
        Assert.assertEquals("BeanC.m1.test", BeanUtil.invokeMethod(beanC, m, "test"));
        Assert.assertEquals("BeanC.m3.test", BeanUtil.invokeMethod(beanC, "m3", "test"));

        Assert.assertNull(BeanUtil.invokeMethod(beanA, (String) null));
        Assert.assertNull(BeanUtil.invokeMethod(null, (String) null));

        Assert.assertNull(BeanUtil.invokeMethod(beanA, (Method) null));
        Assert.assertNull(BeanUtil.invokeMethod(null, (Method) null));
    }

    private BeanC genBean()
    {
        BeanC bean = new BeanC();
        bean.setP(bean);
        bean.p0 = true;
        bean.p1 = 1;
        bean.p2 = new byte[2];
        bean.p3 = "3";
        bean.p4 = new BigDecimal("4");
        bean.p5 = new Date();
        bean.p6 = new Integer[]{1, 2};
        bean.p7 = String.class;
        bean.p8 = new ArrayList<BeanC>();
        bean.p9 = new HashMap<BeanC, BeanC>();

        return bean;
    }

    static class BeanA
    {
        private BeanA p;

        public BeanA getP()
        {
            return p;
        }

        public void setP(BeanA p)
        {
            this.p = p;
        }

        public String m1()
        {
            return "BeanA.m1";
        }
    }

    static class BeanB extends BeanA
    {
        private Boolean p0;

        private Integer p1;

        private Byte[] p2;

        private BeanA p3;

        public Boolean getP0()
        {
            return p0;
        }

        public void setP0(Boolean p0)
        {
            this.p0 = p0;
        }

        public Integer getP1()
        {
            return p1;
        }

        public void setP1(Integer p1)
        {
            this.p1 = p1;
        }

        public Byte[] getP2()
        {
            return p2;
        }

        public void setP2(Byte[] p2)
        {
            this.p2 = p2;
        }

        public BeanA getP3()
        {
            return p3;
        }

        public void setP3(BeanA p3)
        {
            this.p3 = p3;
        }

        public String m2(String m)
        {
            return "BeanB.m2." + m;
        }
    }

    static class BeanC extends BeanA
    {
        private boolean p0;

        private int p1;

        private byte[] p2;

        private String p3;

        private BigDecimal p4;

        private Date p5;

        private Integer[] p6;

        private Class<?> p7;

        private Collection<?> p8;

        private Map<?, ?> p9;

        private byte p10;

        private long p11;

        public boolean isP0()
        {
            return p0;
        }

        public void setP0(boolean p0)
        {
            this.p0 = p0;
        }

        public int getP1()
        {
            return p1;
        }

        public void setP1(int p1)
        {
            this.p1 = p1;
        }

        public byte[] getP2()
        {
            return p2;
        }

        public void setP2(byte[] p2)
        {
            this.p2 = p2;
        }

        public String getP3()
        {
            return p3;
        }

        public void setP3(String p3)
        {
            this.p3 = p3;
        }

        public BigDecimal getP4()
        {
            return p4;
        }

        public void setP4(BigDecimal p4)
        {
            this.p4 = p4;
        }

        public Date getP5()
        {
            return p5;
        }

        public void setP5(Date p5)
        {
            this.p5 = p5;
        }

        public Integer[] getP6()
        {
            return p6;
        }

        public void setP6(Integer[] p6)
        {
            this.p6 = p6;
        }

        public Class<?> getP7()
        {
            return p7;
        }

        public void setP7(Class<?> p7)
        {
            this.p7 = p7;
        }

        public Collection<?> getP8()
        {
            return p8;
        }

        public void setP8(Collection<?> p8)
        {
            this.p8 = p8;
        }

        public Map<?, ?> getP9()
        {
            return p9;
        }

        public void setP9(Map<?, ?> p9)
        {
            this.p9 = p9;
        }

        public byte getP10()
        {
            return p10;
        }

        public void setP10(byte p10)
        {
            this.p10 = p10;
        }

        public long getP11()
        {
            return p11;
        }

        public void setP11(long p11)
        {
            this.p11 = p11;
        }

        public String m1(String m)
        {
            return "BeanC.m1." + m;
        }

        public String m3(String m)
        {
            return "BeanC.m3." + m;
        }
    }
}
