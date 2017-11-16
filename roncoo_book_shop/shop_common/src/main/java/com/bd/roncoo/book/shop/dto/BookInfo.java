package com.bd.roncoo.book.shop.dto;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

public class BookInfo {
    public interface BookListView {}
    public interface BookDetailView extends BookListView {}

    private long id;
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

    public void setId(long id) {
        this.id = id;
    }

    @JsonView(BookListView.class)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonView(BookDetailView.class)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @JsonView(BookListView.class)
    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
}
