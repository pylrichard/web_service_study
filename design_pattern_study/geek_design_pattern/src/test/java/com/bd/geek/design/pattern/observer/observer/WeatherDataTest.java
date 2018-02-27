package com.bd.geek.design.pattern.observer.observer;

public class WeatherDataTest {
    public static void main(String[] args) {
        CurrentConditions currentConditions = new CurrentConditions();
        ForecastConditions forecastConditions = new ForecastConditions();
        WeatherData weatherData = new WeatherData();
        weatherData.registerObserver(currentConditions);
        weatherData.registerObserver(forecastConditions);
        weatherData.setData(20, 30, 40);
        weatherData.removeObserver(currentConditions);
        weatherData.setData(30, 40, 50);
    }
}