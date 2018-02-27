package com.bd.geek.design.pattern.observer.observer;

public class ForecastConditions implements Observer {
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
        System.out.println("tomorrow temperature: " + temperature + Math.random());
        System.out.println("tomorrow pressure: " + pressure + Math.random());
        System.out.println("tomorrow humidity: " + humidity + Math.random());
    }
}
