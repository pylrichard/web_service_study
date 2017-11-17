package com.bd.roncoo.book.shop.common.dto;

public class FileInfo {
    public FileInfo(String path) {
        this.path = path;
    }

    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
