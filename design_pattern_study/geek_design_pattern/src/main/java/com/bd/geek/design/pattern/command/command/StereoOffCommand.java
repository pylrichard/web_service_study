package com.bd.geek.design.pattern.command.command;

import com.bd.geek.design.pattern.command.device.Stereo;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StereoOffCommand implements Command {
    private Stereo stereo;

    @Override
    public void execute() {
        stereo.off();
    }

    @Override
    public void undo() {
        stereo.on();
    }
}

