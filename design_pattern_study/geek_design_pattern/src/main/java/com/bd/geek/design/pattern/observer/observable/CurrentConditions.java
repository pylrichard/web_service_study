package com.bd.geek.design.pattern.observer.observable;

import java.util.Observable;
import java.util.Observer;

public class CurrentConditions implements Observer {
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
        System.out.println("today temperature: " + temperature);
        System.out.println("today pressure: " + pressure);
        System.out.println("today humidity: " + humidity);
    }
}
