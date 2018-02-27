package com.bd.geek.design.pattern.observer.observable;

import lombok.Getter;

import java.util.Observable;

/**
 * Observable是类不是接口
 */
@Getter
public class WeatherData extends Observable {
    private float temperature;
    private float pressure;
    private float humidity;

    public void dataChange() {
        //可设定满足一定条件才通知观察者，提高灵活性
        this.setChanged();
        /*
            notifyObservers()只通知观察者有变化，不发送数据
            notifyObservers(arg)通知观察者有变化，并发送数据
         */
        this.notifyObservers(new Data(getTemperature(), getPressure(), getHumidity()));
    }

    public void setData(float temperature, float pressure, float humidity) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        dataChange();
    }

    @Getter
    public class Data {
        private float temperature;
        private float pressure;
        private float humidity;

        public Data(float temperature, float pressure, float humidity) {
            this.temperature = temperature;
            this.pressure = pressure;
            this.humidity = humidity;
        }
    }
}
