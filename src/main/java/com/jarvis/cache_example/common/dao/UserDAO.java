package com.jarvis.cache_example.common.dao;

import com.jarvis.cache.annotation.Cache;
import com.jarvis.cache.annotation.CacheDelete;
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
    
    @CacheDelete({"'user'+#args[0].id"})
    public void updateUserName(UserTO user){
        System.out.println("update user name:"+user.getName());
        // save to db
    }
    
    @CacheDelete({"user*"})// 注意：因为没有用 SpEL表达式，所以不需要用单引号
    public void clearUserCache(){
        System.out.println("clearUserCache");
        // save to db
    }
}
