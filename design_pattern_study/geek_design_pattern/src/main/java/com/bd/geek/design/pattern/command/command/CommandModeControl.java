package com.bd.geek.design.pattern.command.command;

import com.bd.geek.design.pattern.command.control.Control;

import java.util.Stack;

/**
 * 命令模式将请求、命令、动作等封装成对象，可以使用这些对象来参数化其他对象。使命令的请求者和执行者解耦
 */
public class CommandModeControl implements Control {
    private static int LENGTH = 5;
    private Command[] onCommands;
    private Command[] offCommands;
    private Stack<Command> stack = new Stack<>();

    public CommandModeControl() {
        onCommands = new Command[LENGTH];
        offCommands = new Command[LENGTH];
        Command noCommand = new NoCommand();
        for (int i = 0; i < LENGTH; i++) {
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }
    }

    public void setCommand(int slot, Command onCommand, Command offCommand) {
        onCommands[slot] = onCommand;
        offCommands[slot] = offCommand;
    }

    @Override
    public void onButton(int slot) {
        onCommands[slot].execute();
        stack.push(onCommands[slot]);
    }

    @Override
    public void offButton(int slot) {
        offCommands[slot].execute();
        stack.push(offCommands[slot]);
    }

    @Override
    public void undoButton() {
        stack.pop().undo();
    }
}
