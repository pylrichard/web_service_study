package com.bd.java.annotation.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 见解析Java注解的14~16.png
 */
public class DAOTest {
    public static void main(String[] args) {
        User u1 = new User();
        User u2 = new User();
        User u3 = new User();
        Department dep = new Department();

        u1.setId(10);
        u1.setUserName(null);
        u1.setAge(0);
        u2.setUserName("pyl");
        u2.setAge(18);
        u3.setEmail("pyl@qq.com, pyl@163.com");
        String sql1 = query(u1);
        String sql2 = query(u2);
        String sql3 = query(u3);
        System.out.println(sql1);
        System.out.println(sql2);
        System.out.println(sql3);

        dep.setDepName("开发部");
        String sql4 = query(dep);
        System.out.println(sql4);
    }

    /**
     * 拼接得到sql，可通过JDBC/ORM框架将sql发送给DB
     *
     * @param obj 数据库表映射类
     * @return 拼接的sql
     */
    public static String query(Object obj) {
        StringBuilder builder = new StringBuilder();
        Class cls = obj.getClass();
        boolean isPresent = cls.isAnnotationPresent(Table.class);

        if (!isPresent) {
            return null;
        }
        Table table = (Table)cls.getAnnotation(Table.class);
        String tableName = table.value();
        builder.append("select * from ").append(tableName).append(" where 1 = 1");

        //遍历类的所有字段
        Field[] fields = cls.getDeclaredFields();
        for (Field field:fields) {
            /*
                处理每个字段对应的sql
             */
            isPresent = field.isAnnotationPresent(Column.class);

            if (!isPresent) {
                continue;
            }
            Column column = field.getAnnotation(Column.class);
            //注解的值，对应数据库表字段名
            String columnName = column.value();
            //获取字段名
            String fieldName = field.getName();
            //获取getXxx()方法名
            String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            //表字段有int类型，所以使用Object通用类型
            Object fieldValue = null;
            try {
                Method getMethod = cls.getMethod(getMethodName);
                //获取字段值
                fieldValue = getMethod.invoke(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }

            /*
                拼接sql
             */
            //不处理值为null/0的字段
            if (fieldValue == null || (fieldValue instanceof Integer && (Integer)fieldValue == 0)) {
                continue;
            }
            builder.append(" and ").append(columnName);
            if (fieldValue instanceof String) {
                //字段中包含逗号，对字段按逗号进行分割，拼接为in('xxx','xxx')的格式
                if (((String) fieldValue).contains(",")) {
                    String[] values = ((String) fieldValue).split(",");
                    builder.append(" in(");
                    for (String value:values) {
                        builder.append("'").append(value).append("'").append(",");
                    }
                    //删除最后1个逗号
                    builder.deleteCharAt(builder.length() - 1);
                    builder.append(")");
                }
                //字段不包含逗号，添加单引号
                else {
                    builder.append(" = ").append("'").append(fieldValue).append("'");
                }
            }
            if (fieldValue instanceof Integer) {
                builder.append(" = ").append(fieldValue);
            }
        }

        return builder.toString();
    }
}
