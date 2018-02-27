package com.bd.geek.design.pattern.observer.common;

public class WeatherDataTest {
    public static void main(String[] args) {
        CurrentConditions currentConditions = new CurrentConditions();
        WeatherData weatherData = new WeatherData(currentConditions);
        weatherData.setData(20, 30, 40);
    }
}
