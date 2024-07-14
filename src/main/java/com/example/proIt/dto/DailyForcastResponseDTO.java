package com.example.proIt.dto;

import java.util.Map;

public class DailyForcastResponseDTO extends ForecastResponseDTO {


    DailyDTO daily;

    public DailyDTO getDaily() {
        return daily;
    }

    public void setDaily(DailyDTO daily) {
        this.daily = daily;
    }
}
