package com.yee.study.spring.boot.samples.test;

import org.springframework.stereotype.Service;

/**
 * @author Roger.Yi
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserInfo getUserByName(String name) {
        if (name == null)
            return null;

        if (name.equals("Roger"))
            return new UserInfo("Roger", 30);
        else if (name.equals("Phoebe"))
            return new UserInfo("Phoebe", 20);

        return null;
    }

    @Override
    public int getUserId(String name) {
        if (name == null)
            return 0;

        if (name.equals("Roger"))
            return 1;
        else if (name.equals("Phoebe"))
            return 2;

        return 0;
    }
}
