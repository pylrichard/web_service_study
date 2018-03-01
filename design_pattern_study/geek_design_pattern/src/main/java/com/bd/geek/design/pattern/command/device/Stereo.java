package com.bd.geek.design.pattern.command.device;

import lombok.Getter;
import lombok.Setter;

/**
 * 音响
 */
@Getter
@Setter
public class Stereo {
    private int volume = 0;

    public Stereo(int volume) {
        this.volume = volume;
    }

    public void on() {
        System.out.println("Stereo on");
    }

    public void off() {
        System.out.println("Stereo off");
    }
}
