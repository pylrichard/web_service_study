package com.bd.geek.design.pattern.command.command;

import com.bd.geek.design.pattern.command.device.Light;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LightOnCommand implements Command {
    private Light light;

    @Override
    public void execute() {
        light.on();
    }

    @Override
    public void undo() {
        light.off();
    }
}
