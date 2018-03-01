package com.bd.geek.design.pattern.command.control;

import com.bd.geek.design.pattern.command.device.Light;
import com.bd.geek.design.pattern.command.device.Stereo;

public class ControlTest {
    public static void main(String[] args) {
        Light light = new Light("Bedroom");
        Stereo stereo = new Stereo(0);
        Control control = new TraditionControl(light, stereo);
        control.onButton(0);
        control.offButton(0);
        control.onButton(1);
        control.onButton(2);
        control.offButton(2);
        control.offButton(1);
    }
}