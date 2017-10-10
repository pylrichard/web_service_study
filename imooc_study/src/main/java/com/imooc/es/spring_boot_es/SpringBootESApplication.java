package com.imooc.es.spring_boot_es;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 注解@RestController 组合注解=@ResponseBody+@Controller
 * 注解@Controller 处理http请求
 */
@SpringBootApplication
@RestController
public class SpringBootESApplication {
    @Autowired
    private TransportClient client;

    /**
     * Postman执行GET 192.168.8.10:8080/get/book/novel?id=2
     *
     * 注解@RequestMapping/@GetMapping 配置url映射
     *
     * /get和/query都能访问到同一个方法
     * 注解@RequestMapping(value = {"/get", "/query"}, method = RequestMethod.GET)
     */
    @GetMapping("/get/book/novel")
    @ResponseBody
    public ResponseEntity get(@RequestParam(name = "id", defaultValue = "")String id) {
        if (id.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        GetResponse response = this.client.prepareGet("book", "novel", id).get();

        if (!response.isExists()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(response.getSource(), HttpStatus.OK);
    }

    /**
     * POST 192.168.8.10:8080/query/book/novel，Body->x-www-form-urlencoded
     *
     * 注解@RequestParam("id") 获取请求参数id的值，如/get?id=5中的5
     * 注解@PathVariable 获取url中的数据，如/get/5中的5
     */
    @PostMapping("/query/book/novel")
    @ResponseBody
    public ResponseEntity query(@RequestParam(name = "name", required = false) String name,
                                @RequestParam(name = "gt_word_count", defaultValue = "0") int gtWordCount,
                                @RequestParam(name = "lt_word_count", required = false) Integer ltWordCount) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("word_count").from(gtWordCount);

        if (name != null) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("name", name));
        }
        if (ltWordCount != null && ltWordCount > 0) {
            rangeQueryBuilder.to(ltWordCount);
        }
        boolQueryBuilder.filter(rangeQueryBuilder);

        SearchRequestBuilder searchRequestBuilder = this.client.prepareSearch("book")
                .setTypes("novel")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQueryBuilder)
                .setFrom(0)
                .setSize(10);
        //输出请求body内容，便于调试
        System.out.println(searchRequestBuilder);

        SearchResponse response = searchRequestBuilder.get();
        List<Map<String, Object>> result = new ArrayList<>();

        for (SearchHit hit:response.getHits()) {
            result.add(hit.getSource());
        }

        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * POST 192.168.8.10:8080/add/book/novel，Body->form-data填充数据
     * 根据返回id执行get()查询添加结果
     */
    @PostMapping("/add/book/novel")
    @ResponseBody
    public ResponseEntity add(@RequestParam(name = "name") String name,
                              @RequestParam(name = "word_count") int wordCount,
                              @RequestParam(name = "publish_date")
                              @DateTimeFormat(pattern = "yyyy-MM-dd") Date publishDate) {
        try {
            XContentBuilder context = XContentFactory.jsonBuilder().startObject()
                    .field("name", name)
                    .field("word_count", wordCount)
                    .field("publish_date", publishDate.getTime())
                    .endObject();

            IndexResponse response = this.client.prepareIndex("book", "novel").setSource(context).get();

            return new ResponseEntity(response.getId(), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();

            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/book/novel")
    @ResponseBody
    public ResponseEntity delete(@RequestParam(name = "id") String id) {
        DeleteResponse response = this.client.prepareDelete("book", "novel", id).get();

        return new ResponseEntity(response.getResult().toString(), HttpStatus.OK);
    }

    /**
     * PUT 192.168.8.10:8080/update/book/novel，Body->x-www-form-urlencoded
     *
     * required = false表示name参数不是必须填充的
     */
    @PutMapping("/update/book/novel")
    @ResponseBody
    public ResponseEntity update(@RequestParam(name = "id") String id,
                                 @RequestParam(name = "name", required = false) String name) {
        UpdateRequest request = new UpdateRequest("book", "novel", id);

        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();

            if (name != null) {
                builder.field("name", name);
            }
            builder.endObject();
            request.doc(builder);
        } catch (IOException e) {
            e.printStackTrace();

            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            UpdateResponse response = this.client.update(request).get();

            return new ResponseEntity(response.getResult().toString(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootESApplication.class, args);
    }
}
