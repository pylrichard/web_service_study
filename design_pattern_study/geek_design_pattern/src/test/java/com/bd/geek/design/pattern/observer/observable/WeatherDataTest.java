package com.bd.geek.design.pattern.observer.observable;

public class WeatherDataTest {
    public static void main(String[] args) {
        CurrentConditions currentConditions = new CurrentConditions();
        ForecastConditions forecastConditions = new ForecastConditions();
        WeatherData weatherData = new WeatherData();
        weatherData.addObserver(currentConditions);
        weatherData.addObserver(forecastConditions);
        weatherData.setData(20, 30, 40);
        weatherData.deleteObserver(currentConditions);
        weatherData.setData(30, 40, 50);
    }
}