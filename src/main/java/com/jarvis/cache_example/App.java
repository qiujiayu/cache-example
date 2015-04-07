package com.jarvis.cache_example;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jarvis.cache_example.common.dao.UserDAO;
import com.jarvis.cache_example.common.to.UserTO;

/**
 * Hello world!
 */
public class App {

    private static ClassPathXmlApplicationContext context;

    /**
     * 初始化
     */
    private static void init() {
        context=new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
    }

    public static void main(String[] args) {
        init();
        System.out.println("Hello World!");
        final UserDAO userDAO=(UserDAO)context.getBean("userDAO");
        Thread thread=new Thread(new Runnable() {

            @Override
            public void run() {
                int cnt=0;
                while(true) {
                    loadData();
                    try {
                        Thread.sleep(1000);
                    } catch(InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    cnt++;
                    if(cnt > 5) {
                        userDAO.clearUserCache();
                        loadData();
                        break;
                    }
                }
            }
            
            private void loadData(){
                for(int i=1; i < 20; i++) {
                    UserTO to=userDAO.getUserById(i);
                    System.out.println(to.getName());
                }
            }

        });
        thread.start();
    }
}
