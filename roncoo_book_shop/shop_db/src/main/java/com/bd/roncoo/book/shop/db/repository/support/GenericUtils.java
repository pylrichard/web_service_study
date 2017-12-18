package com.bd.roncoo.book.shop.db.repository.support;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 泛型工具
 */
public class GenericUtils {
    /**
     * 获取目标class的第一个泛型参数的类型
     */
    @SuppressWarnings("rawtypes")
    public static Class getGenericClass(Class clazz) {
        return getGenericClass(clazz, 0);
    }

    /**
     * 获取目标class的指定位置的泛型参数的类型
     *
     * @param index 泛型参数的位置，第一个参数为0
     */
    @SuppressWarnings("rawtypes")
    public static Class getGenericClass(Class clazz, int index) {
        Type t = clazz.getGenericSuperclass();

        if (t instanceof ParameterizedType) {
            Type[] params = ((ParameterizedType) t).getActualTypeArguments();

            if (params[index] instanceof ParameterizedType) {
                return ((ParameterizedType) params[index]).getRawType().getClass();
            } else {
                return (Class) params[index];
            }
        }

        throw new RuntimeException("can't get parameter type");
    }

}
