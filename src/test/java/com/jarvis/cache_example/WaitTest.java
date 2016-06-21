package com.jarvis.cache_example;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.alibaba.fastjson.JSON;
import com.jarvis.cache_example.common.dao.UserDAO;
import com.jarvis.cache_example.common.to.UserTO;

public class WaitTest {

    private static ApplicationContext applicationContext=null;

    private static UserDAO userDAO;

    public static void main(String[] args) {
        String[] tmp=new String[]{"applicationContext.xml", "datasource-config.xml"};
        applicationContext=new ClassPathXmlApplicationContext(tmp);
        userDAO=applicationContext.getBean(UserDAO.class);
        test();
        try {
            Thread.sleep(80 * 1000);
        } catch(InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        test();
        try {
            Thread.sleep(120 * 1000);
        } catch(InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void test() {
        UserTO data=new UserTO();
        data.setId(1);
        data.setAge(20);
        data.setName("test1");
        List<UserTO> res1=userDAO.getUserList(data);
        System.out.println("res1==" + JSON.toJSONString(res1));
        data.setAge(30);
        data.setName("test2");
        List<UserTO> res2=userDAO.getUserList(data);
        System.out.println("res2==" + JSON.toJSONString(res2));
    }

    private static void testShardedJedisPool() {
        ShardedJedisPool shardedJedisPool=(ShardedJedisPool)applicationContext.getBean("shardedJedisPool");
        for(int i=0; i < 100; i++) {
            String key="key_" + i + ";";
            ShardedJedis shardedJedis=shardedJedisPool.getResource();
            Jedis jedis=shardedJedis.getShard(key);
            Client client=jedis.getClient();
            System.out.println(key + "---->" + client.getHost() + ":" + client.getPort());
        }
    }

    private static void countDownTest() {
        int threadCnt=10;
        userDAO.clearUserById2Cache(100);
        final CountDownLatch count=new CountDownLatch(threadCnt);
        for(int i=0; i < threadCnt; i++) {
            Thread thread=new Thread(new Runnable() {

                @Override
                public void run() {
                    UserTO user=null;
                    try {
                        user=userDAO.getUserById2(100);
                        Thread thread=Thread.currentThread();
                        System.out.println(thread.getName() + "     finished  " + user.getName());
                    } catch(Exception e) {
                        e.printStackTrace();
                    }

                    count.countDown();
                }
            }, "thread" + i);
            thread.start();
        }
        try {
            count.await();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        userDAO.clearUserById2Cache(100);
    }

}
