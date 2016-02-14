package com.jarvis.cache_example.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jarvis.cache_example.common.dao.UserDAO;
import com.jarvis.cache_example.common.mapper.UserMapper;
import com.jarvis.cache_example.common.to.UserTO;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserTO getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    @Override
    public UserTO getUserById2(Integer id) {
        return userDAO.getUserById(id);
    }

    @Override
    public void updateUser(UserTO user) {
        userDAO.updateUserName(user);
    }

    @Override
    public List<UserTO> getUserList() {
        return userDAO.getUserList(new UserTO());
    }

}
