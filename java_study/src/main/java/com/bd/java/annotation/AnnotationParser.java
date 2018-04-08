package com.bd.java.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 修改Description的@Retention为(RetentionPolicy.CLASS)(编译时注解)，则以下代码不能查找到注解，见10.png
 */
public class AnnotationParser {
    public static void main(String[] args) {
        try {
            Class cls = Class.forName("com.bd.java.annotation.Child");
            Description desc = null;

            //查找类注解
            boolean isPresent = cls.isAnnotationPresent(Description.class);

            if (isPresent) {
                desc = (Description) cls.getAnnotation(Description.class);
                System.out.println(desc);
            }

            //查找方法注解
            Method[] methods = cls.getMethods();
            for (Method method:methods) {
                isPresent = method.isAnnotationPresent(Description.class);

                if (isPresent) {
                    desc = method.getAnnotation(Description.class);
                    System.out.println(desc);
                }
            }

            for (Method method:methods) {
                Annotation[] annotations = method.getAnnotations();

                for (Annotation annotation:annotations) {
                    if (annotation instanceof Description) {
                        System.out.println(((Description) annotation).desc());
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
