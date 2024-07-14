package com.example.proIt.dto;

public class HourlyDTO {

    String[] time;
    Double[] temperature_2m;
    Double[] rain;
    Double[] surface_pressure;
    Double[] wind_speed_10m;
    Double[] wind_speed_80m;
    Double[] wind_speed_120m;
    Double[] wind_speed_180m;

    public Double[] getTemperature_2m() {
        return temperature_2m;
    }

    public void setTemperature_2m(Double[] temperature_2m) {
        this.temperature_2m = temperature_2m;
    }

    public Double[] getRain() {
        return rain;
    }

    public void setRain(Double[] rain) {
        this.rain = rain;
    }

    public Double[] getSurface_pressure() {
        return surface_pressure;
    }

    public void setSurface_pressure(Double[] surface_pressure) {
        this.surface_pressure = surface_pressure;
    }

    public Double[] getWind_speed_10m() {
        return wind_speed_10m;
    }

    public void setWind_speed_10m(Double[] wind_speed_10m) {
        this.wind_speed_10m = wind_speed_10m;
    }

    public Double[] getWind_speed_80m() {
        return wind_speed_80m;
    }

    public void setWind_speed_80m(Double[] wind_speed_80m) {
        this.wind_speed_80m = wind_speed_80m;
    }

    public Double[] getWind_speed_120m() {
        return wind_speed_120m;
    }

    public void setWind_speed_120m(Double[] wind_speed_120m) {
        this.wind_speed_120m = wind_speed_120m;
    }

    public Double[] getWind_speed_180m() {
        return wind_speed_180m;
    }

    public void setWind_speed_180m(Double[] wind_speed_180m) {
        this.wind_speed_180m = wind_speed_180m;
    }

    public String[] getTime() {
        return time;
    }

    public void setTime(String[] time) {
        this.time = time;
    }
}
