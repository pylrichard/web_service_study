package com.bd.java_multithread_core_tech.chapter1.thread_safe;

public class LoginServlet {
    private static String userNameRef;
    private static String passwordRef;

    synchronized public static void doPost(String userName, String password) {
        try {
            userNameRef = userName;
            //此处发生睡眠后，切换到BLogin线程运行，静态变量userNameRef等于b
            if(userName.equals("a")) {
                Thread.sleep(5000);
            }

            passwordRef = password;
            System.out.println("userName = " + userNameRef + " password = " + passwordRef);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
