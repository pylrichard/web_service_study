package com.bd.roncoo.spring.boot;

import com.bd.roncoo.spring.boot.bean.UserLog;
import com.bd.roncoo.spring.boot.component.JMSComponent;
import com.bd.roncoo.spring.boot.component.RedisComponent;
import com.bd.roncoo.spring.boot.controller.APIController;
import com.bd.roncoo.spring.boot.dao.UserLogDAO;
import com.bd.roncoo.spring.boot.mapper.UserLogMapper;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.protocol.HttpContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
//不指定classes，则Spring Boot会启动整个Spring容器，比如执行初始化、ApplicationRunner、CommandLineRunner等
@SpringBootTest//(classes={DataSourceAutoConfiguration.class, MybatisAutoConfiguration.class, MybatisScanConfiguration.class})
public class RoncooSpringBootApplicationTest {
    @Autowired
    private RedisComponent redisComponent;
    @Autowired
    private UserLogDAO userLogDAO;
    @Autowired
    private JMSComponent jmsComponent;
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    @Autowired
    private UserLogMapper userLogMapper;

    private MockMvc mvc;

    @Before
    public void before() {
        this.mvc = MockMvcBuilders.standaloneSetup(new APIController()).build();
    }

    @Test
    public void contextLoads() throws Exception {
        MockHttpServletRequestBuilder req = MockMvcRequestBuilders.get("/api");
        mvc.perform(req.accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("hello spring boot"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
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

    @Test
    public void send() {
        jmsComponent.send("hello world");
    }

    @Test
    public void getForObject() {
        //使用RestTemplateBuilder创建RestTemplate对象
        //RestTemplate是Spring提供的用于访问REST服务的客户端
        UserLog bean = restTemplateBuilder.build().getForObject("http://192.168.8.10:8080/api/update/{id}", UserLog.class, 1);
        System.out.println(bean.getUserName());

        Map<String, Object> map = new HashMap<>();
        map.put("id", 2);
        bean = restTemplateBuilder.build().postForObject("http://192.168.8.10:8080/api/update", map, UserLog.class);
        System.out.println(bean.getUserIp());

        //使用代理访问REST服务
//        String result = restTemplateBuilder.additionalCustomizers(new ProxyCustomizer()).build().getForObject("http://www.qq.com", String.class);
//        System.out.println(result);
    }

    static class ProxyCustomizer implements RestTemplateCustomizer {
        @Override
        public void customize(RestTemplate restTemplate) {
            String proxyHost = "x.x.x.x";
            int proxyPort = 8080;
            HttpHost proxy = new HttpHost(proxyHost, proxyPort);
            HttpClient httpClient = HttpClientBuilder.create().setRoutePlanner(new DefaultProxyRoutePlanner(proxy) {
                @Override
                public HttpHost determineProxy(HttpHost target, HttpRequest request, HttpContext context) throws HttpException {
                    System.out.println(target.getHostName());

                    return super.determineProxy(target, request, context);
                }
            }).build();
            HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

            httpComponentsClientHttpRequestFactory.setConnectTimeout(10000);
            httpComponentsClientHttpRequestFactory.setReadTimeout(60000);
            restTemplate.setRequestFactory(httpComponentsClientHttpRequestFactory);
        }
    }

    @Test
    public void testMyBatis() {
        UserLog userLog = new UserLog();
        userLog.setUserName("py");
        userLog.setCreateTime(new Date());
        userLog.setUserIp("192.168.8.10");

        userLogMapper.insert(userLog);

        //MySQL下构建相应测试数据，但测试用例依赖特定数据，不推荐
        //推荐使用H2在内存中构建测试数据
        userLog = userLogMapper.selectByPrimaryKey(1);
        System.out.println(userLog.getCreateTime());
        Assert.assertEquals(new Integer(1), userLog.getId());
    }
}
