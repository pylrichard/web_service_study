package com.bd.imooc.seckill.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bd.imooc.seckill.domain.SecKillUser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserUtil {
    private static void createUser(int count) throws Exception {
        List<SecKillUser> users = new ArrayList<>(count);
        /*
            生成秒杀用户
         */
        for (int i = 0; i < count; i++) {
            SecKillUser user = new SecKillUser();
            user.setId(13000000000L + i);
            user.setLoginCount(1);
            user.setNickname("user" + i);
            user.setRegisterDate(new Date());
            user.setSalt("1a2b3c");
            user.setPassword(Md5Util.inputPassToDbPass("Pyl123456", user.getSalt()));
            users.add(user);
        }
        /*
            登录生成token
         */
        String urlString = "http://localhost:8080/login/do_login";
        File file = new File("classpath:tokens.txt");
        if (file.exists()) {
            file.delete();
        }
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        file.createNewFile();
        randomAccessFile.seek(0);
        for (int i = 0; i < users.size(); i++) {
            SecKillUser user = users.get(i);
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            OutputStream outStream = connection.getOutputStream();
            String params = "mobile=" + user.getId() + "&password=" + Md5Util.inputPassToFormPass("Pyl123456");
            outStream.write(params.getBytes());
            outStream.flush();
            InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) >= 0) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            inputStream.close();
            byteArrayOutputStream.close();
            String response = new String(byteArrayOutputStream.toByteArray());
            JSONObject jsonObject = JSON.parseObject(response);
            String token = jsonObject.getString("data");
            String row = user.getId() + "," + token;
            randomAccessFile.seek(randomAccessFile.length());
            randomAccessFile.write(row.getBytes());
            randomAccessFile.write("\r\n".getBytes());
        }
        randomAccessFile.close();
    }
}
