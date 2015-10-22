package com.jarvis.cache_example.common.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.jarvis.cache.annotation.Cache;
import com.jarvis.cache.annotation.CacheDelete;
import com.jarvis.cache.annotation.CacheDeleteKey;
import com.jarvis.cache.type.CacheKeyType;
import com.jarvis.cache.type.CacheOpType;
import com.jarvis.cache_example.common.to.UserTO;

public class UserDAO {

    /**
     * 添加用户的同时，把数据放到缓存中
     * @param userName
     * @return
     */
    @Cache(expire=600, key="'user'+#retVal.id", opType=CacheOpType.WRITE)
    public UserTO addUser(String userName) {
        UserTO user=new UserTO();
        user.setName(userName);
        Random rand=new Random();
        // 数据库返回ID
        Integer id=rand.nextInt(100000);
        user.setId(id);
        System.out.println("add User:" + id);
        return user;
    }

    /**
     * 使用 hash 方法，将参数转为字符串
     * @param user
     * @return
     */
    @Cache(expire=600, key="'user'+$hash(#args)")
    public List<UserTO> getUserList(UserTO user) {
        List<UserTO> list=new ArrayList<UserTO>();
        return list;
    }

    /**
     * 使用自定义缓存Key，并在指定的条件下才进行缓存。
     * @param id
     * @return
     */
    @Cache(expire=600, autoload=true, key="'user'+#args[0]", condition="#args[0]>0")
    public UserTO getUserById(Integer id) {
        UserTO user=new UserTO();
        user.setId(id);
        user.setName("name" + id);
        System.out.println("getUserById from dao");
        return user;
    }

    @CacheDelete({@CacheDeleteKey(value="'user'+#args[0].id", keyType=CacheKeyType.DEFINED)})
    public void updateUserName(UserTO user) {
        System.out.println("update user name:" + user.getName());
        // save to db
    }

    // 注意：因为没有用 SpEL表达式，所以不需要用单引号
    @CacheDelete({@CacheDeleteKey(value="user*", keyType=CacheKeyType.DEFINED)})
    public void clearUserCache() {
        System.out.println("clearUserCache");
        // save to db
    }

    // ------------------------以下是使用默认生成Key的方法--------------------
    @Cache(expire=600, autoload=true, condition="#args[0]>0")
    public UserTO getUserById2(Integer id) {
        UserTO user=new UserTO();
        user.setId(id);
        user.setName("name" + id);
        System.out.println("getUserById from dao");
        return user;
    }

    @CacheDelete({@CacheDeleteKey(cls=UserDAO.class, method="getUserById2", argsEl={"#args[0].id"}, keyType=CacheKeyType.DEFAULT)})
    public void updateUserName2(UserTO user) {
        System.out.println("update user name:" + user.getName());
        // save to db
    }

    @CacheDelete({@CacheDeleteKey(deleteByPrefixKey=true, cls=UserDAO.class, method="getUserById2", keyType=CacheKeyType.DEFAULT)})
    public void clearUserCache2() {
        System.out.println("clearUserCache");
        // save to db
    }
}
