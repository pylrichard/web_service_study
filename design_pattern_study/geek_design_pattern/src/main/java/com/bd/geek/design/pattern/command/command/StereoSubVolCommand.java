package com.bd.geek.design.pattern.command.command;

import com.bd.geek.design.pattern.command.device.Stereo;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StereoSubVolCommand implements Command {
    private Stereo stereo;

    @Override
    public void execute() {
        int vol = stereo.getVolume();
        if (vol > 0) {
            stereo.setVolume(--vol);
        }
    }

    @Override
    public void undo() {
        int vol = stereo.getVolume();
        if (vol < 10) {
            stereo.setVolume(++vol);
        }
    }
}
