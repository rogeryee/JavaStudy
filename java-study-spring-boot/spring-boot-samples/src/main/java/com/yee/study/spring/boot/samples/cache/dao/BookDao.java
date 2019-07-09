package com.yee.study.spring.boot.samples.cache.dao;

import com.yee.study.spring.boot.samples.cache.model.Book;

/**
 * Book Dao接口定义类
 * @author Roger.Yi
 */
public interface BookDao {

    /**
     * 获取Book
     * @param isbn
     * @return
     */
    Book getByIsbn(String isbn);

    /**
     * 更新Book
     * @param book
     */
    Book update(Book book);

    void delete(String isbn);
}
