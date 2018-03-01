package com.bd.geek.design.pattern.command.command;

import com.bd.geek.design.pattern.command.device.Light;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LightOffCommand implements Command {
    private Light light;

    @Override
    public void execute() {
        light.off();
    }

    @Override
    public void undo() {
        light.on();
    }
}
