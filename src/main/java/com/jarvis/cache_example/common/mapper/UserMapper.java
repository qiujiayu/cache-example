package com.jarvis.cache_example.common.mapper;

import com.jarvis.cache.annotation.Cache;
import com.jarvis.cache.annotation.CacheDelete;
import com.jarvis.cache.annotation.CacheDeleteKey;
import com.jarvis.cache_example.common.to.UserTO;


public interface UserMapper {
    
    @Cache(expire=600, autoload=true, key="'user_mapper_getUserById_'+#args[0]", condition="#args[0]>0")
    UserTO getUserById(Integer id);
    
    int addUser(UserTO user);
    
    @CacheDelete({@CacheDeleteKey(value="'user_mapper_getUserById_'+#args[0].id")})
    int incAge(UserTO user);

}
