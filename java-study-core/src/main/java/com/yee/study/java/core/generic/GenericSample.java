package com.yee.study.java.core.generic;

/**
 * @author Roger.Yi
 */
public class GenericSample<T extends Person> {

    public T createPerson(T t) {
        t.setName("TestName");
        return t;
    }

    public static void main(String[] args) {

        GenericSample<Person> sample = new GenericSample<>();
        Person person = sample.createPerson(new Person());
        System.out.println(person.toString());
    }
}

class Person {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Man extends Person {
    private String gener;

    public String getGener() {
        return gener;
    }

    public void setGener(String gener) {
        this.gener = gener;
    }
}
