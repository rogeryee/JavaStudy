package com.yee.study.spring.cloud.feign;

import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Roger.Yi
 */
public interface ICallbackService<T> {

    void onResponse(@RequestBody T t);
}
