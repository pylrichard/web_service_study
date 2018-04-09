package com.bd.roncoo.book.shop.common.dto;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;

/**
 * Dubbo服务返回BookInfo需要序列化
 */
@Setter
@NoArgsConstructor
public class BookInfo implements Serializable {
    public interface BookListView {}
    public interface BookDetailView extends BookListView {}

    public BookInfo(String name) {
        this.name = name;
    }

    private long id;
    @ApiModelProperty("图书名称")
    private String name;
    @NotBlank
    private String content;
    /**
     * application.properties中添加spring.jackson.time-zone = GMT+8
     */
    private Date publishDate;

    @JsonView(BookListView.class)
    public long getId() {
        return id;
    }

    @JsonView(BookListView.class)
    public String getName() {
        return name;
    }

    @JsonView(BookDetailView.class)
    public String getContent() {
        return content;
    }

    @JsonView(BookListView.class)
    public Date getPublishDate() {
        return publishDate;
    }
}
