package com.bd.geek.design.pattern.observer.common;

import lombok.Getter;

/**
 * 这种设计方案有2个问题：
 * 1 第三方对象接入气象站获取数据需要改动代码
 * 2 无法在运行时动态添加第三方对象
 */
@Getter
public class WeatherData {
    private float temperature;
    private float pressure;
    private float humidity;
    private CurrentConditions currentConditions;

    public WeatherData(CurrentConditions currentConditions) {
        this.currentConditions = currentConditions;
    }

    public void dataChange() {
        currentConditions.update(getTemperature(), getPressure(), getHumidity());
    }

    public void setData(float temperature, float pressure, float humidity) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        dataChange();
    }
}
