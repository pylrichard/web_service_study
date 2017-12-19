package com.bd.roncoo.book.shop.book.service.batch;

import org.springframework.stereotype.Component;

@Component
public class BookTaskImpl implements BookTask {
    /**
     * 定时任务业务逻辑
     */
    @Override
    public void doTask() {
        System.out.println("doTask method");
    }
}
