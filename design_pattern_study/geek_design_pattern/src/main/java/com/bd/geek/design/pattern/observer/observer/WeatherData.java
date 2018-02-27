package com.bd.geek.design.pattern.observer.observer;

import lombok.Getter;

import java.util.ArrayList;

/**
 * 观察者模式解决对象之间多对一依赖，被依赖对象为Subject，依赖对象为Observer，Subject通知Observer变化
 */
@Getter
public class WeatherData implements Subject {
    private float temperature;
    private float pressure;
    private float humidity;
    private ArrayList<Observer> observers;

    public WeatherData() {
        observers = new ArrayList<>();
    }

    public void dataChange() {
        notifyObservers();
    }

    public void setData(float temperature, float pressure, float humidity) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        dataChange();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        if (observers.contains(observer)) {
            observers.remove(observer);
        }
    }

    @Override
    public void notifyObservers() {
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).update(getTemperature(), getPressure(), getHumidity());
        }
    }
}
