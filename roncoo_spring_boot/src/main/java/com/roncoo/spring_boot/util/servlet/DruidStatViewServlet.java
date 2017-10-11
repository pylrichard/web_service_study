package com.roncoo.spring_boot.util.servlet;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * 数据源状态监控
 */
@WebServlet(urlPatterns = { "/druid/*" },
        initParams = {
                //IP白名单(没有配置或者为空，则允许所有访问)
                @WebInitParam(name="allow",value="192.168.8.10,127.0.0.1"),
                //IP黑名单(deny优先于allow)
                @WebInitParam(name="deny",value="192.168.8.11"),
                @WebInitParam(name = "loginUsername", value = "roncoo"),
                @WebInitParam(name = "loginPassword", value = "roncoo")
        }
)
public class DruidStatViewServlet extends StatViewServlet {
    private static final long serialVersionUID = 1L;
}
