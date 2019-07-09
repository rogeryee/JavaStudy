package com.yee.study.spring.boot.samples.cache.model;

import java.io.Serializable;

/**
 * Book定义类
 *
 * @author Roger.Yi
 */
public class Book implements Serializable {

    private String isbn;

    private String name;

    public Book() {
    }

    public Book(String isbn, String name) {
        this.isbn = isbn;
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
