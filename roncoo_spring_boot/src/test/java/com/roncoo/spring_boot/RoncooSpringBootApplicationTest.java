package com.roncoo.spring_boot;

import com.roncoo.spring_boot.bean.UserLog;
import com.roncoo.spring_boot.component.RedisComponent;
import com.roncoo.spring_boot.controller.APIController;
import com.roncoo.spring_boot.dao.UserLogDAO;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoncooSpringBootApplicationTest {
    @Autowired
    private RedisComponent redisComponent;
    @Autowired
    private UserLogDAO userLogDAO;

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

    @Test
    public void insert() {
        UserLog entity = new UserLog();
        entity.setUserName("pyl");
        entity.setUserIp("192.168.0.1");
        entity.setCreateTime(new Date());
        userLogDAO.save(entity);
    }

    @Test
    public void delete() {
        userLogDAO.delete(1);
    }

    @Test
    public void update() {
        UserLog entity = new UserLog();
        entity.setId(2);
        entity.setUserName("pyl");
        entity.setUserIp("192.168.0.1");
        entity.setCreateTime(new Date());
        userLogDAO.save(entity);
    }

    @Test
    public void select1() {
        UserLog result = userLogDAO.findOne(2);
        System.out.println(result.getUserIp());
    }

    @Test
    public void select2() {
        UserLog result = userLogDAO.findByUserName("pyl");
        System.out.println(result.getUserName());
    }

    @Test
    public void queryForPage() {
        Pageable pageable = new PageRequest(0, 20, new Sort(new Order(Direction.DESC, "id")));
        Page<UserLog> result = userLogDAO.findByUserName("pyl", pageable);
        System.out.println(result.getContent());
        result = userLogDAO.findAll(pageable);
        System.out.println(result.getContent());
    }
}
