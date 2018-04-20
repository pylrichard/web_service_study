package com.bd.java.db.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class JdbcHelper {
    private static JdbcHelper instance = null;
    /**
     * 数据库连接池
     */
    private ConcurrentLinkedQueue<Connection> connectionPool = new ConcurrentLinkedQueue<>();

    static {
        try {
            String driver = ConfManager.getProperty(Constants.JDBC_DRIVER);
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JdbcHelper getInstance() {
        if (instance == null) {
            synchronized (JdbcHelper.class) {
                if (instance == null) {
                    instance = new JdbcHelper();
                }
            }
        }

        return instance;
    }

    private JdbcHelper() {
        int poolSize = ConfManager.getInteger(Constants.JDBC_CONNECTION_POOL_SIZE);
        for (int i = 0; i < poolSize; i++) {
            String url = ConfManager.getProperty(Constants.JDBC_URL);
            String user = ConfManager.getProperty(Constants.JDBC_USER);
            String password = ConfManager.getProperty(Constants.JDBC_PASSWORD);
            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                connectionPool.add(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized Connection getConnection() {
        while (connectionPool.size() == 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return connectionPool.poll();
    }

    public int executeUpdate(String sql, Object[] params) {
        int retVal = 0;
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            statement = conn.prepareStatement(sql);
            if (params != null && params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    statement.setObject(i + 1, params[i]);
                }
            }
            retVal = statement.executeUpdate();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                connectionPool.add(conn);
            }
        }

        return retVal;
    }

    public void executeQuery(String sql, Object[] params,
                             QueryCallback callback) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            statement = conn.prepareStatement(sql);
            if (params != null && params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    statement.setObject(i + 1, params[i]);
                }
            }
            rs = statement.executeQuery();
            callback.process(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                connectionPool.add(conn);
            }
        }
    }

    public int[] executeBatch(String sql, List<Object[]> paramsList) {
        int[] retVal = null;
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = getConnection();
            //取消自动提交
            conn.setAutoCommit(false);
            statement = conn.prepareStatement(sql);
            //加入批量的SQL参数
            if (paramsList != null && paramsList.size() > 0) {
                for (Object[] params : paramsList) {
                    for (int i = 0; i < params.length; i++) {
                        statement.setObject(i + 1, params[i]);
                    }
                    statement.addBatch();
                }
            }
            //执行批量的SQL语句
            retVal = statement.executeBatch();
            //提交批量的SQL语句
            conn.commit();
        } catch (Exception e) {
            System.out.println(sql);
            e.printStackTrace();
        } finally {
            if (conn != null) {
                connectionPool.add(conn);
            }
        }

        return retVal;
    }

    /**
     * 查询回调接口
     */
    public interface QueryCallback {
        /**
         * 处理查询结果
         */
        void process(ResultSet rs) throws Exception;
    }
}