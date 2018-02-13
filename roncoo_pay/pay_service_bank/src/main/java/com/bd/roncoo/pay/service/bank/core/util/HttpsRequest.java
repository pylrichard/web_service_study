package com.bd.roncoo.pay.service.bank.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpsRequest {
    private static final Logger log = LogFactory.getLog(HttpsRequest.class);

    public static String sendHttpsRequest(String Url) throws IOException {
        InputStream inputStream = getInputStreamByHttp(Url);
        byte[] buffer = new byte[1024];
        int len = 0;
        int data = 0;
        while ((data = inputStream.read()) != -1) {
            buffer[len] = (byte) data;
            len++;
        }
        inputStream.close();

        return new String(buffer, 0, len, "GBK");
    }

    /**
     * 通过https方式下载对账文件
     *
     * @param filePath 下载文件存放路径
     * @param Url      请求的Url
     */
    public static boolean sendHttpRequestForFile(String filePath, String Url) throws IOException {
        boolean flag = false;
        InputStream inputStream = getInputStreamByHttp(Url);
        if (inputStream != null) {
            BufferedInputStream inBuff = null;
            BufferedOutputStream outBuff = null;
            //新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(inputStream);
            //新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(filePath));
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inBuff.read(buffer)) != -1) {
                outBuff.write(buffer, 0, len);
            }
            //刷新缓冲输出流
            outBuff.flush();
            flag = true;
            outBuff.close();
            inBuff.close();
            inputStream.close();
        }

        return flag;
    }

    @SuppressWarnings("finally")
    private static InputStream getInputStreamByHttp(String Url) {
        URL url;
        InputStream in = null;
        try {
            url = new URL(Url);
            log.info("sendHttpsRequest begin : " + Url);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            //设置请求方法
            connection.setRequestMethod("POST");
            //设置请求超时时间，以毫秒为单位
            connection.setConnectTimeout(3000);
            X509TrustManager xtm = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                        throws CertificateException {
                }

                @Override
                public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                        throws CertificateException {
                }
            };
            TrustManager[] tm = {xtm};
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, tm, null);
            connection.setSSLSocketFactory(ctx.getSocketFactory());
            connection.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
            in = connection.getInputStream();
        } catch (Exception e) {
            log.error("连接通讯异常", e);
        } finally {
            return in;
        }
    }
}
