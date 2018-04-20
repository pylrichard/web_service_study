package com.bd.roncoo.multithread.sync.atomic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String name;
    public volatile int age;
}
