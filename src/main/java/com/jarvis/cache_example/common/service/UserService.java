package com.jarvis.cache_example.common.service;

import com.jarvis.cache_example.common.to.UserTO;


public interface UserService {
    UserTO getUserById(Integer id);
}
