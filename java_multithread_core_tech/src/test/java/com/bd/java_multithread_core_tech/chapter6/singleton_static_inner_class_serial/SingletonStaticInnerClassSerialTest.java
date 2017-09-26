package com.bd.java_multithread_core_tech.chapter6.singleton_static_inner_class_serial;

import java.io.*;

public class SingletonStaticInnerClassSerialTest {
    public static void main(String[] args) {
        //序列化对象
        try {
            MyService service = MyService.getInstance();
            FileOutputStream fos = new FileOutputStream(new File("service.txt"));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(service);
            oos.close();
            fos.close();
            System.out.println(service.hashCode());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //反序列化对象
        try {
            FileInputStream fis = new FileInputStream(new File("service.txt"));
            ObjectInputStream ois = new ObjectInputStream(fis);
            //调用readResolve()
            MyService service = (MyService)ois.readObject();
            ois.close();
            fis.close();
            System.out.println(service.hashCode());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
