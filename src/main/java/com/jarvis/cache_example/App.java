package com.jarvis.cache_example;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jarvis.cache_example.common.dao.UserDAO;
import com.jarvis.cache_example.common.to.UserTO;

/**
 * Hello world!
 */
public class App {

    private static ClassPathXmlApplicationContext context;

    private static UserDAO userDAO;

    /**
     * 初始化
     */
    private static void init() {
        context=new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
        userDAO=(UserDAO)context.getBean("userDAO");
    }

    public static void main(String[] args) {
        init();
        System.out.println("Hello World!");

        test3();

    }
    public static void test3(){
        String userName="testUser";
        UserTO user=userDAO.addUser(userName);
        System.out.println(user.getId());
        user=userDAO.getUserById(user.getId());
        System.out.println(user.getId());
    }

    public static void test1() {
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

            private void loadData() {
                for(int i=1; i < 20; i++) {
                    UserTO to=userDAO.getUserById(i);
                    System.out.println(to.getName());
                }
            }

        });
        thread.start();
    }

    public static void test2() {
        userDAO.getUserById2(100);

        UserTO user=new UserTO();
        user.setId(100);
        user.setName("name100");
        userDAO.updateUserName2(user);
        userDAO.getUserById2(100);
        userDAO.getUserById2(100);
        userDAO.clearUserCache2();
        userDAO.getUserById2(100);
        userDAO.getUserById2(100);
    }
}
