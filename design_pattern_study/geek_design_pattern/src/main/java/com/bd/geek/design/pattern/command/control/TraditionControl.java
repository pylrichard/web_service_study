package com.bd.geek.design.pattern.command.control;

import com.bd.geek.design.pattern.command.device.Light;
import com.bd.geek.design.pattern.command.device.Stereo;
import lombok.AllArgsConstructor;

/**
 * 遥控器控制灯和音响
 * 这种设计方案耦合度高
 */
@AllArgsConstructor
public class TraditionControl implements Control {
    Light light;
    Stereo stereo;

    @Override
    public void onButton(int slot) {
        switch (slot) {
            case 0:
                light.on();
                break;
            case 1:
                stereo.on();
                break;
            case 2:
                int vol = stereo.getVolume();
                if (vol < 10) {
                    stereo.setVolume(++vol);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void offButton(int slot) {
        switch (slot) {
            case 0:
                light.off();
                break;
            case 1:
                stereo.off();
                break;
            case 2:
                int vol = stereo.getVolume();
                if (vol > 0) {
                    stereo.setVolume(--vol);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void undoButton() {
    }
}
