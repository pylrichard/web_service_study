package com.bd.java_multithread_core_tech.chapter1.stop_throw_lock;

public class SyncObject {
    private String userName = "a";
    private String password = "aa";

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    synchronized public void printString(String userName, String password) {
        try {
            this.userName = userName;
            Thread.sleep(1000);
            this.password = password;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
