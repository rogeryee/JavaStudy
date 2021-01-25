package com.yee.study.spring.framework.ioc.bean;

/**
 * @author Roger.Yi
 */
public class CycleBean {

    public static class CycleBeanA {
        private CycleBeanB b;

        public CycleBeanA() {}

        public CycleBeanA(CycleBeanB b) {
            this.b = b;
        }

        public CycleBeanB getB() {
            return b;
        }

        public void setB(CycleBeanB b) {
            this.b = b;
        }
    }

    public static class CycleBeanB {
        private CycleBeanC c;

        public CycleBeanB() {}

        public CycleBeanB(CycleBeanC c) {
            this.c = c;
        }

        public CycleBeanC getC() {
            return c;
        }

        public void setC(CycleBeanC c) {
            this.c = c;
        }
    }

    public static class CycleBeanC {
        private CycleBeanA a;

        public CycleBeanC() {}

        public CycleBeanC(CycleBeanA a) {
            this.a = a;
        }

        public CycleBeanA getA() {
            return a;
        }

        public void setA(CycleBeanA a) {
            this.a = a;
        }
    }

    public static class CycleBeanD {}
}
