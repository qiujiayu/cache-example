package com.jarvis.cache_example.common.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.jarvis.cache.annotation.Cache;
import com.jarvis.cache.annotation.CacheDelete;
import com.jarvis.cache.annotation.CacheDeleteKey;
import com.jarvis.cache.type.CacheOpType;
import com.jarvis.cache_example.common.to.UserTO;

public class UserDAO {

    private static final String cacheName="user";

    private static final int expire=600;

    /**
     * 添加用户的同时，把数据放到缓存中
     * @param userName
     * @return
     */
    @Cache(expire=expire, key="'" + cacheName + "'+#retVal.id", opType=CacheOpType.WRITE)
    public UserTO addUser(String userName) {
        UserTO user=new UserTO();
        user.setName(userName);
        Random rand=new Random();
        // 数据库返回ID
        Integer id=rand.nextInt(100000);
        user.setId(id);
        System.out.println("add User:" + id);

        return getUserById(id);
    }

    /**
     * 使用 hash 方法，将参数转为字符串
     * @param user
     * @return
     */
    @Cache(expire=expire, key="'" + cacheName + "'+#hash(#args)")
    public List<UserTO> getUserList(UserTO user) {
        List<UserTO> list=new ArrayList<UserTO>();
        return list;
    }

    /**
     * 使用自定义缓存Key，并在指定的条件下才进行缓存。
     * @param id
     * @return
     */
    @Cache(expire=600, autoload=true, key="'user_dao_getUserById'+#args[0]", condition="#args[0]>0")
    public UserTO getUserById(Integer id) {
        UserTO user=new UserTO();
        user.setId(id);
        user.setName("name" + id);
        System.out.println("getUserById from dao");
        return user;
    }

    /**
     * 使用自定义缓存Key，并在指定的条件下才进行缓存。
     * @param id
     * @return
     */
    @Cache(expire=600, autoload=false, key="'user_dao_getUserById2'+#args[0]", condition="#args[0]>0")
    public UserTO getUserById2(Integer id) throws Exception {
        Thread thread=Thread.currentThread();
        System.out.println("thread:" + thread.getName() + ";getUserById2");
        try {
            // 模拟阻塞
            Thread.sleep(100);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        UserTO user=new UserTO();
        user.setId(id);
        user.setName("name" + id);
        // throw new Exception("test");// 异常情况测试
        return user;
    }

    // 注意：因为没有用 SpEL表达式，所以不需要用单引号
    @CacheDelete({@CacheDeleteKey(value="'user_dao_getUserById2'+#args[0]", condition="#args[0]>0")})
    public void clearUserById2Cache(Integer id) {
        System.out.println("clearUserById2Cache");
        // save to db
    }

    @CacheDelete({@CacheDeleteKey(value="'user'+#args[0].id", condition="null != #args[0]")})
    public void updateUserName(UserTO user) {
        if(null != user) {
            System.out.println("update user name:" + user.getName());
        } else {
            System.out.println("use is null");
        }
        // save to db
    }

    // 注意：因为没有用 SpEL表达式，所以不需要用单引号
    @CacheDelete({@CacheDeleteKey(value="user*")})
    public void clearUserCache() {
        System.out.println("clearUserCache");
        // save to db
    }

}
