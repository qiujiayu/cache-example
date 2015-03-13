package com.jarvis.cache_example.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jarvis.cache_example.common.dao.UserDAO;
import com.jarvis.cache_example.common.to.UserTO;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;
    
    @Override
    public UserTO getUserById(Integer id) {
        return userDAO.getUserById(id);
    }

    @Override
    public void updateUser(UserTO user) {
        userDAO.updateUserName(user);
    }

}
