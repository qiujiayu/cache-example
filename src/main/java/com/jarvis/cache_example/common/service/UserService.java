package com.jarvis.cache_example.common.service;

import java.util.List;

import com.jarvis.cache_example.common.to.UserTO;

public interface UserService {

    UserTO getUserById(Integer id);

    UserTO getUserById2(Integer id);

    List<UserTO> getUserList();

    void updateUser(UserTO user);
}
