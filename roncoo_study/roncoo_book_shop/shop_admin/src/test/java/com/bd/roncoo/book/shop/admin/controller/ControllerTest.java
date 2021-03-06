package com.bd.roncoo.book.shop.admin.controller;

import com.bd.roncoo.book.shop.admin.AdminApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        String result = mockMvc.perform(get("/book?page=1&size=15")
                //.param("name", "tom and jerry")
                //HTTP的参数都是String类型，Controller的方法参数可以是任意类型，Spring会进行类型转换
                .param("categoryId", "1")
                .param("name", "战争")
                //可以在get("/book")中包含参数
                //.param("page", "1")
                //.param("size", "15")
                //name和desc间不能有空格
                .param("sort", "name,desc", "createdTime,asc")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                //预期返回200
                .andExpect(status().isOk())
                //预期返回json格式的集合，长度为3
                //$.length()见GitHub的JsonPath
                .andExpect(jsonPath("$.length()").value(3))
                .andReturn().getResponse().getContentAsString();

        System.out.println(result);
    }

    @Test
    public void whenGetInfoSuccess() throws Exception {
        String result = mockMvc.perform(get("/book/1").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("战争与和平"))
                .andReturn().getResponse().getContentAsString();

        System.out.println(result);
    }

    @Test
    public void whenGetInfoFail() throws Exception {
        //发送id为2位数
        mockMvc.perform(get("/book/10").accept(MediaType.APPLICATION_JSON_UTF8))
                //希望返回404错误
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenCreateSuccess() throws Exception {
        //创建不指定id
        String content = "{\"id\":null, \"name\":\"战争与和平\", \"content\":\"hello spring\", \"publishDate\":\"2017-07-08\"}";
        mockMvc.perform(post("/book").content(content).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //创建成功后返回id为1
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    public void whenUpdateSuccess() throws Exception {
        String content = "{\"id\":1, \"name\":\"战争与和平\", \"content\":\"hello spring\", \"publishDate\":\"2017-07-08\"}";
        mockMvc.perform(put("/book/1").content(content).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    public void whenDeleteSuccess() throws Exception {
        mockMvc.perform(delete("/book/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenCookieOrHeaderExists() throws Exception {
        mockMvc.perform(get("/book/1")
                .cookie(new Cookie("token", "123456"))
                .header("auth", "987654")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenUploadSuccess() throws Exception {
        String result = mockMvc.perform(fileUpload("/file/upload")
                .file(new MockMultipartFile("file", "upload.txt",
                        "multipart/form-data", "hello upload".getBytes("UTF-8"))))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        System.out.println(result);
    }
}
