package com.yee.study.util.json;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JSONObject测试类。
 */
public class JSONObjectTest {
    public static class SecondBean {
        private String a;

        private String b;

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }
    }

    public static class TestBean {
        private String value1;

        private BigDecimal value2;

        private SecondBean value3;

        private Map<String, SecondBean> value4;

        private List<SecondBean> value5;

        public String getValue1() {
            return value1;
        }

        public void setValue1(String value1) {
            this.value1 = value1;
        }

        public BigDecimal getValue2() {
            return value2;
        }

        public void setValue2(BigDecimal value2) {
            this.value2 = value2;
        }

        public SecondBean getValue3() {
            return value3;
        }

        public void setValue3(SecondBean value3) {
            this.value3 = value3;
        }

        public Map<String, SecondBean> getValue4() {
            return value4;
        }

        public void setValue4(Map<String, SecondBean> value4) {
            this.value4 = value4;
        }

        public List<SecondBean> getValue5() {
            return value5;
        }

        public void setValue5(List<SecondBean> value5) {
            this.value5 = value5;
        }

    }

    @Test
    public void test() throws JSONException {
        TestBean bean = new TestBean();
        bean.setValue1("001");
        bean.setValue2(new BigDecimal("123.45"));
        SecondBean bean2 = new SecondBean();
        bean2.setA("1");
        bean2.setB("2");
        bean.setValue3(bean2);
        Map<String, SecondBean> map = new HashMap<String, SecondBean>();
        map.put("key1", bean2);
        SecondBean bean3 = new SecondBean();
        bean3.setA("01");
        bean3.setB("02");
        map.put("key2", bean3);
        bean.setValue4(map);
        List<SecondBean> list = new ArrayList<SecondBean>();
        list.add(bean2);
        list.add(bean3);
        bean.setValue5(list);

        Object obj = JSONObject.wrap("123");
        System.out.println(obj.toString());

        obj = JSONObject.wrap(bean);
        System.out.println(obj.toString());

        List<TestBean> args = new ArrayList<TestBean>();
        ;
        args.add(bean);
        args.add(bean);

        obj = JSONObject.wrap(args);
        System.out.println(obj.toString());

        Map<String, TestBean> mBean = new HashMap<String, TestBean>();
        mBean.put("k0", bean);
        mBean.put("k1", bean);

        obj = JSONObject.wrap(mBean);
        System.out.println(obj.toString());
    }

}
