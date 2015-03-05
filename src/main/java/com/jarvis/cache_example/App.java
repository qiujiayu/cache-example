package com.jarvis.cache_example;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jarvis.cache_example.dao.UserDAO;
import com.jarvis.cache_example.to.UserTO;

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
        Thread thread=new Thread(new Runnable(){

            @Override
            public void run() {
                while(true){
                for(int i=0;i<20;i++){
                    UserTO to=userDAO.getUserById(i);
                    System.out.println(to.getName());
                }
                try {
                    Thread.sleep(30000);
                } catch(InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                }
            }
            
        });
        thread.start();
    }
}
