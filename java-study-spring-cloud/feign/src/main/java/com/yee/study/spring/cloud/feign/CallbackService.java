package com.yee.study.spring.cloud.feign;

import com.yee.study.spring.cloud.feign.provider.model.User;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Roger.Yi
 */
@Callback
public class CallbackService implements ICallbackService<User> {

    public String url;

    @Override
    public void onResponse(@RequestBody User user) {

    }
}
