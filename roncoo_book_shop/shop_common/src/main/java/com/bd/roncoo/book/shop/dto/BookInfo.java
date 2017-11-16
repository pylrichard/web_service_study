package com.bd.roncoo.book.shop.dto;

import com.fasterxml.jackson.annotation.JsonView;

public class BookInfo {
    public interface BookListView {}
    public interface BookDetailView extends BookListView {}

    private long id;
    private String name;
    private String content;

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
}
