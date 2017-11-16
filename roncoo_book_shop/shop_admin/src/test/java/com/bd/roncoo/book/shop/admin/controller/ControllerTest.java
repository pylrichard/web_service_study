package com.bd.roncoo.book.shop.admin.controller;

import com.bd.roncoo.book.shop.admin.AdminApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class ControllerTest {
    @Autowired
    private WebApplicationContext context;

    /**
     * 本测试用例没有启动Tomcat中间件，通过MockMvc来模拟请求
     */
    private MockMvc mockMvc;

    /**
     * 注解@Before表示setup()会在测试用例执行之前先执行
     */
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void whenQuerySuccess() throws Exception {
        //以ContentType = APPLICATION_JSON_UTF8发送GET请求到/book
        mockMvc.perform(MockMvcRequestBuilders.get("/book").param("name", "tom and jerry")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                //预期返回200
                .andExpect(MockMvcResultMatchers.status().isOk())
                //预期返回json格式的集合，长度为3
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3));
    }
}
