package com.bd.geek.design.pattern.command.device;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Light {
    private String loc = "";

    public void on() {
        System.out.println(loc + " on");
    }

    public void off() {
        System.out.println(loc + " off");
    }
}
