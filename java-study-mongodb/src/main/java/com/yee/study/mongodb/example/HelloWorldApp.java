package com.yee.study.mongodb.example;

import com.yee.study.mongodb.spring.MongoDataSource;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloWorld启动器
 *
 * @author Roger.Yi
 */
@SpringBootApplication
@RestController
public class HelloWorldApp {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(HelloWorldApp.class);
        application.run(args);
    }

    @Autowired
    private MongoDataSource dataSource;

    /**
     * http://localhost:8081/hello
     * @param
     * @return
     */
    @RequestMapping("/hello")
    public String helloWorld() {
        Iterable<Document> iter = dataSource.getDatabase().getCollection("test_collection").find();
        return iter.iterator().next().toJson();
    }
}
