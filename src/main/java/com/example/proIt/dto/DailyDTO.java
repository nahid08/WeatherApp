package com.example.proIt.dto;

public class DailyDTO {

    String[] time;
    Double[] temperature_2m_max;
    Double[] temperature_2m_min;
    Double[] apparent_temperature_max;
    Double[] apparent_temperature_min;
    Double[] rain_sum;

    public String[] getTime() {
        return time;
    }

    public void setTime(String[] time) {
        this.time = time;
    }

    public Double[] getTemperature_2m_max() {
        return temperature_2m_max;
    }

    public void setTemperature_2m_max(Double[] temperature_2m_max) {
        this.temperature_2m_max = temperature_2m_max;
    }

    public Double[] getTemperature_2m_min() {
        return temperature_2m_min;
    }

    public void setTemperature_2m_min(Double[] temperature_2m_min) {
        this.temperature_2m_min = temperature_2m_min;
    }

    public Double[] getApparent_temperature_max() {
        return apparent_temperature_max;
    }

    public void setApparent_temperature_max(Double[] apparent_temperature_max) {
        this.apparent_temperature_max = apparent_temperature_max;
    }

    public Double[] getApparent_temperature_min() {
        return apparent_temperature_min;
    }

    public void setApparent_temperature_min(Double[] apparent_temperature_min) {
        this.apparent_temperature_min = apparent_temperature_min;
    }

    public Double[] getRain_sum() {
        return rain_sum;
    }

    public void setRain_sum(Double[] rain_sum) {
        this.rain_sum = rain_sum;
    }
}
