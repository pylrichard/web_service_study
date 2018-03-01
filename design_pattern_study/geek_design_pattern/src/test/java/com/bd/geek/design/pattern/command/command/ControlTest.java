package com.bd.geek.design.pattern.command.command;

import com.bd.geek.design.pattern.command.device.Light;
import com.bd.geek.design.pattern.command.device.Stereo;

public class ControlTest {
    public static void main(String[] args) {
        CommandModeControl control = new CommandModeControl();
        Light bedRoomLight = new Light("BedRoom");
        Light kitchenLight = new Light("Kitchen");
        Stereo stereo = new Stereo(0);
        LightOnCommand bedRoomLightOn = new LightOnCommand(bedRoomLight);
        LightOffCommand bedRoomLightOff = new LightOffCommand(bedRoomLight);
        LightOnCommand kitchenLightOn = new LightOnCommand(kitchenLight);
        LightOffCommand kitchenLightOff = new LightOffCommand(kitchenLight);
        Command[] onCommands = {bedRoomLightOn, kitchenLightOn};
        Command[] offCommands = {bedRoomLightOff, kitchenLightOff};
        MarcoCommand onMarco = new MarcoCommand(onCommands);
        MarcoCommand offMarco = new MarcoCommand(offCommands);
        StereoOnCommand stereoOn = new StereoOnCommand(stereo);
        StereoOffCommand stereoOff = new StereoOffCommand(stereo);
        StereoAddVolCommand stereoAddVol = new StereoAddVolCommand(stereo);
        StereoSubVolCommand stereoSubVol = new StereoSubVolCommand(stereo);
        control.setCommand(0, bedRoomLightOn, bedRoomLightOff);
        control.setCommand(1, kitchenLightOn, kitchenLightOff);
        control.setCommand(2, stereoOn, stereoOff);
        control.setCommand(3, stereoAddVol, stereoSubVol);
        control.setCommand(4, onMarco, offMarco);
        control.onButton(0);
        control.undoButton();
        control.onButton(1);
        control.offButton(1);
        control.onButton(2);
        control.onButton(3);
        control.offButton(3);
        control.undoButton();
        control.offButton(2);
        control.undoButton();
        control.onButton(4);
        control.offButton(4);
    }
}
