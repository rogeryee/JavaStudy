package com.yee.study.java.core.lang;

import lombok.extern.slf4j.Slf4j;

/**
 * System.arraycopy() 方法示例
 *
 * @author Roger.Yi
 */
@Slf4j
public class ArrayCopySample {

    private static void testPrimitiveArray() {
        int[] array1 = new int[]{1, 2, 3, 4, 5};
        int[] array2 = new int[5];
        System.arraycopy(array1, 0, array2, 0, 5);
        log.info("array1={}", array1);
        log.info("array2={}", array2);

        array2[0] = 10;
        array2[2] = 13;
        log.info("array1={}", array1);
        log.info("array2={}", array2);
    }

    private static void testObjectArray() {
        User[] array1 = new User[]{new User("Roger"), new User("Johnny"), new User("John")};
        User[] array2 = new User[3];
        System.arraycopy(array1, 0, array2, 0, 3);
        log.info("array1={}", array1);
        log.info("array2={}", array2);

        array2[0].setName(array2[0].getName() + " updated");
        log.info("array1={}", array1);
        log.info("array2={}", array2);
    }

    public static void main(String[] args) {
        testPrimitiveArray();
        testObjectArray();
    }

    static class User {
        private String name;

        public User(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
