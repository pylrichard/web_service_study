package com.bd.geek.design.pattern.observer.observable;

import java.util.Observable;
import java.util.Observer;

public class ForecastConditions implements Observer {
    private float temperature;
    private float pressure;
    private float humidity;

    @Override
    public void update(Observable o, Object arg) {
        WeatherData.Data data = (WeatherData.Data) arg;
        this.temperature = data.getTemperature();
        this.pressure = data.getPressure();
        this.humidity = data.getHumidity();
        display();
    }

    public void display() {
        System.out.println("tomorrow temperature: " + temperature + Math.random());
        System.out.println("tomorrow pressure: " + pressure + Math.random());
        System.out.println("tomorrow humidity: " + humidity + Math.random());
    }
}
