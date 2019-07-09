package com.yee.study.spring.boot.samples.cache.dao;

import com.yee.study.spring.boot.samples.cache.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Roger.Yi
 */
@Repository
public class BookDaoImpl implements BookDao, InitializingBean {

    public static final Logger logger = LoggerFactory.getLogger(BookDaoImpl.class);

    private Map<String, Book> map = new HashMap<>();

    @Cacheable(cacheNames = "books")
    public Book getByIsbn(String isbn) {
        try {
            logger.info("Get book[isbn=" + isbn + "] from database start...");
            Thread.sleep(3000L);
            logger.info("Get book[isbn=" + isbn + "] from database end...");
            return map.get(isbn);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    @CachePut(cacheNames = "books", key = "#result.isbn")
    public Book update(Book book) {
        try {
            logger.info("Save book[isbn=" + book.getIsbn() + "] to database start...");
            Thread.sleep(3000L);
            logger.info("Save book[isbn=" + book.getIsbn() + "] to database end...");
            this.map.put(book.getIsbn(), book);
            return book;
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    @CacheEvict(cacheNames = "books", key = "#isbn")
    public void delete(String isbn) {
        try {
            logger.info("Delete book[isbn=" + isbn + "] to database start...");
            Thread.sleep(3000L);
            logger.info("Delete book[isbn=" + isbn + "] to database end...");
            this.map.remove(isbn);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        map.put("0001", new Book("0001", "Book1"));
        map.put("0002", new Book("0002", "Book2"));
        map.put("0003", new Book("0003", "Book3"));
        map.put("0004", new Book("0004", "Book4"));
        map.put("0005", new Book("0005", "Book5"));
    }
}
