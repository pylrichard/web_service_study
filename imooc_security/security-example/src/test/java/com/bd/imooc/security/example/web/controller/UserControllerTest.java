package com.bd.imooc.security.example.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void whenQuerySuccess() throws Exception {
        String result = mockMvc.perform(
                /*
                    设置GET请求参数，组装为UserQueryCondition
                 */
                get("/user")
                        .param("username", "pyl").param("age", "18")
                        .param("ageTo", "60").param("xxx", "yyy")
                        //.param("size", "15")
                        //.param("page", "3")
                        //.param("sort", "age,desc")
                        //设置请求类型
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                /*
                    设置预期结果
                 */
                //设置预期返回状态码
                .andExpect(status().isOk())
                //设置预期返回值长度，$等符号意义见JsonPath Github
                .andExpect(jsonPath("$.length()").value(3))
                //显示响应结果
                .andReturn().getResponse().getContentAsString();

        System.out.println(result);
    }

    @Test
    public void whenGetInfoSuccess() throws Exception {
        String result = mockMvc.perform(get("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("pyl"))
                .andReturn().getResponse().getContentAsString();

        System.out.println(result);
    }

    @Test
    public void whenGetInfoFail() throws Exception {
        mockMvc.perform(get("/user/a")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is4xxClientError());
    }
}
