package com.bd.imooc.seckill.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DbUtil {
    private static Properties props;

    static {
        try {
            InputStream in = DbUtil.class.getClassLoader()
                    .getResourceAsStream("imooc_seckill/src/main/resources/application.properties");
            props = new Properties();
            props.load(in);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {
        String url = props.getProperty("spring.datasource.url");
        String username = props.getProperty("spring.datasource.username");
        String password = props.getProperty("spring.datasource.password");
        String driver = props.getProperty("spring.datasource.driver-class-name");
        Class.forName(driver);

        return DriverManager.getConnection(url, username, password);
    }
}
