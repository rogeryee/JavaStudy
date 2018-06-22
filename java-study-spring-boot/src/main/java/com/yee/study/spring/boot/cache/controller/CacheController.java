package com.yee.study.spring.boot.cache.controller;

import com.yee.study.spring.boot.cache.dao.BookDao;
import com.yee.study.spring.boot.cache.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Roger.Yi
 */
@RestController
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    private BookDao bookDao;

    /**
     * 获取Book
     * curl 'http://localhost:8080/cache/get/0001'
     *
     * @param key
     * @return
     */
    @RequestMapping("/get/{isbn}")
    public Book get(@PathVariable String isbn) {
        return bookDao.getByIsbn(isbn);
    }

    /**
     * 更新Book
     * curl 'http://localhost:8080/cache/update/0006/book6'
     *
     * @param isbn
     * @param name
     * @return
     */
    @RequestMapping("/update/{isbn}/{name}")
    public String update(@PathVariable String isbn, @PathVariable String name) {
        Book book = new Book(isbn, name);
        bookDao.update(book);
        return "update success";
    }

    /**
     * 删除Book
     * curl 'http://localhost:8080/cache/del/0006'
     *
     * @param key
     * @return
     */
    @RequestMapping("/del/{isbn}")
    public String del(@PathVariable String isbn) {
        bookDao.delete(isbn);
        return "del success";
    }
}
