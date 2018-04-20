package com.bd.java.db;

import com.bd.java.db.jdbc.JdbcHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;

public class JoinSql {
    public static void main(String[] args) throws SQLException {
        Connection conn = JdbcHelper.getInstance().getConnection();
        /*
            拆分下面SQL语句
			SELECT
				e.*,
				s.salary,
				s.from_date
			FROM employees e
			JOIN salaries s ON e.emp_no = s.emp_no
			WHERE (s.emp_no, s.from_date) IN (
				SELECT
				    emp_no,
				    MAX(from_date) AS from_date
				FROM salaries
				GROUP BY emp_no
			)
			ORDER BY salary
			LIMIT 0, 10

			统计group by分组数量
			SELECT SUM(cnt)
            FROM (
                SELECT
                    emp_no,
                    MAX(from_date) AS from_date,
                    COUNT(emp_no)  AS cnt
                FROM salaries
                GROUP BY emp_no
            );
		*/
        String sql1 = "SELECT emp_no, MAX(from_date) AS from_date FROM salaries GROUP BY emp_no";
        PreparedStatement statement1 = conn.prepareStatement(sql1);
        ResultSet resultSet1, resultSet2, resultSet3;
        Map<String, String> sql1Map = new HashMap<>();
        Map<String, String> resultMap = new HashMap<>();
        Long startTime = System.currentTimeMillis();
        try {
            resultSet1 = statement1.executeQuery();
            while (resultSet1.next()) {
                sql1Map.put(resultSet1.getString("emp_no"), resultSet1.getString("from_date"));
            }
            StringBuilder sql2Builder = new StringBuilder();
            StringBuilder sql3Builder = new StringBuilder();
            /*
                构建动态SQL
             */
            for (int i = 0; i < sql1Map.size(); i++) {
                sql2Builder.append("?, ");
                sql3Builder.append("(?, ?), ");
            }
            sql2Builder.deleteCharAt(sql2Builder.length() - 1);
            sql2Builder.deleteCharAt(sql2Builder.length() - 1);
            sql3Builder.deleteCharAt(sql3Builder.length() - 1);
            sql3Builder.deleteCharAt(sql3Builder.length() - 1);
            String sql2 = "SELECT * FROM employees WHERE emp_no IN (" + sql2Builder.toString() + ")";
            //(emp_no, from_date)组合过滤得到emp_no的唯一一行记录，emp_no IN和from_date IN会得到emp_no相同的多行记录
            String sql3 = "SELECT emp_no, salary, from_date FROM salaries WHERE (emp_no, from_date) IN ("
                    + sql3Builder.toString() + ")";
            PreparedStatement statement2 = conn.prepareStatement(sql2);
            PreparedStatement statement3 = conn.prepareStatement(sql3);
            int index1 = 1;
            int index2 = 1;
            /*
                填充参数到动态SQL
             */
            for (Map.Entry<String, String> entry : sql1Map.entrySet()) {
                statement2.setString(index1, entry.getKey());
                statement3.setString(index2, entry.getKey());
                statement3.setString(index2 + 1, entry.getValue());
                index1++;
                index2 += 2;
            }
            resultSet2 = statement2.executeQuery();
            resultSet3 = statement3.executeQuery();
            while (resultSet2.next() && resultSet3.next()) {
                //行记录作为key保持唯一，如果salary作为key，Map的size会减少
                resultMap.put(resultSet2.getString("emp_no") + " "
                                + resultSet2.getString("birth_date") + " "
                                + resultSet2.getString("first_name") + " "
                                + resultSet2.getString("last_name") + " "
                                + resultSet2.getString("gender") + " "
                                + resultSet2.getString("hire_date") + " "
                                + resultSet3.getString("salary") + " "
                                + resultSet3.getString("from_date"),
                        resultSet3.getString("salary"));
            }
            List<Entry<String, String>> entryList = sortMapByValue(resultMap);
            Iterator<Entry<String, String>> iter = entryList.iterator();
            int count = 0;
            while (iter.hasNext()) {
                if (count == 10) {
                    break;
                }
                Entry<String, String> entry = iter.next();
                System.out.println(entry.getKey());
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        Long endTime = System.currentTimeMillis();
        System.out.println("SQL执行时间: " + (endTime - startTime) + " ms");
    }

    public static List<Entry<String, String>> sortMapByValue(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        List<Entry<String, String>> entryList = new ArrayList<>(map.entrySet());
        /*
            TODO 注意HashMap存储元素是无序的，想要获取有序结果要使用List
         */
        Collections.sort(entryList, new MapValueComparator());

        return entryList;
    }

    static class MapValueComparator implements Comparator<Entry<String, String>> {
        public int compare(Entry<String, String> me1, Entry<String, String> me2) {
            int value1 = Integer.parseInt(me1.getValue());
            int value2 = Integer.parseInt(me2.getValue());
            int retVal = 0;
            if (value1 > value2) {
                retVal = 1;
            }
            if (value1 == value2) {
                retVal = 0;
            }
            if (value1 < value2) {
                retVal = -1;
            }
            return retVal;
        }
    }
}
