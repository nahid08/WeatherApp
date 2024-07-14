package com.example.proIt.dto;

public class HourlyForecastResponseDTO extends  ForecastResponseDTO {

    HourlyDTO hourly;

    public HourlyDTO getHourly() {
        return hourly;
    }

    public void setHourly(HourlyDTO hourly) {
        this.hourly = hourly;
    }
}
