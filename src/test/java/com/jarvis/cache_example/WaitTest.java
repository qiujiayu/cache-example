package com.jarvis.cache_example;

import java.util.concurrent.CountDownLatch;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jarvis.cache_example.common.dao.UserDAO;
import com.jarvis.cache_example.common.to.UserTO;

public class WaitTest implements Runnable {

    private ApplicationContext applicationContext=null;

    private UserDAO userDAO;

    private CountDownLatch count;

    public static void main(String[] args) {
        String[] tmp=new String[]{"applicationContext.xml", "datasource-config.xml"};
        WaitTest t=new WaitTest();
        t.applicationContext=new ClassPathXmlApplicationContext(tmp);
        t.userDAO=t.applicationContext.getBean(UserDAO.class);
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e1) {
            e1.printStackTrace();
        }
        t.userDAO.clearUserById2Cache(100);
        int threadCnt=10;
        t.count=new CountDownLatch(threadCnt);
        for(int i=0; i < threadCnt; i++) {
            Thread thread=new Thread(t, "thread" + i);
            thread.start();
        }
        try {
            t.count.await();
        } catch(InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        t.userDAO.clearUserById2Cache(100);
    }

    @Override
    public void run() {
        UserTO user=this.userDAO.getUserById2(100);
        Thread thread=Thread.currentThread();
        System.out.println(thread.getName() + "     finished  "+user.getName());
        count.countDown();
    }

}
