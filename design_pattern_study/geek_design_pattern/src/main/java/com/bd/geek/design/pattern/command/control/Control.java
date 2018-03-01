package com.bd.geek.design.pattern.command.control;

public interface Control {
    void onButton(int slot);

    void offButton(int slot);

    void undoButton();
}
