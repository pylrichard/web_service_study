package com.bd.geek.design.pattern.observer.observer;

public class CurrentConditions implements Observer {
    private float temperature;
    private float pressure;
    private float humidity;

    @Override
    public void update(float temperature, float pressure, float humidity) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        display();
    }

    public void display() {
        System.out.println("today temperature: " + temperature);
        System.out.println("today pressure: " + pressure);
        System.out.println("today humidity: " + humidity);
    }
}
