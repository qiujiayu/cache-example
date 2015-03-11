package com.jarvis.cache_example.common.dao;

import com.jarvis.cache.annotation.Cache;
import com.jarvis.cache_example.common.to.UserTO;


public class UserDAO {
    
    @Cache(expire=600, autoload=true, key="'user'+#args[0]", condition="#args[0]>0")
    public UserTO getUserById(Integer id){
        UserTO user=new UserTO();
        user.setId(id);
        user.setName("name"+id);
        System.out.println("getUserById from dao");
        return user;
    }
}
