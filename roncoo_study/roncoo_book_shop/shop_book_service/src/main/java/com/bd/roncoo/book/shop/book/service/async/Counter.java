package com.bd.roncoo.book.shop.book.service.async;

import lombok.Getter;

@Getter
public class Counter {
    private long total = 0;

    public void add(long value) {
        total += value;
    }
}
