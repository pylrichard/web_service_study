package com.roncoo.spring_boot;

import com.roncoo.spring_boot.component.RedisComponent;
import com.roncoo.spring_boot.controller.APIController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoncooSpringBootApplicationTest {
    @Autowired
    private RedisComponent redisComponent;

    private MockMvc mvc;

    @Before
    public void before() {
        this.mvc = MockMvcBuilders.standaloneSetup(new APIController()).build();
    }

    @Test
    public void contextLoads() throws Exception {
        RequestBuilder req = get("/api");
        mvc.perform(req).andExpect(status().isOk()).andExpect(content().string("hello spring boot"));
    }

    @Test
    public void setRedisValue() {
        redisComponent.set("redis", "hello world");
    }

    @Test
    public void getRedisValue() {
        System.out.println(redisComponent.get("redis"));
    }

    @Test
    public void delRedisValue() {
        redisComponent.del("redis");
    }
}
