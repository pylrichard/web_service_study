package com.bd.geek.design.pattern.command.command;

import com.bd.geek.design.pattern.command.device.Stereo;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StereoOnCommand implements Command {
    private Stereo stereo;

    @Override
    public void execute() {
        stereo.on();
    }

    @Override
    public void undo() {
        stereo.off();
    }
}
