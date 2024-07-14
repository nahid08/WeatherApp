package com.example.proIt.service;

import com.example.proIt.dto.LocationDTO;
import com.example.proIt.dto.CityListResponseDTO;
import com.example.proIt.dto.DailyForcastResponseDTO;
import com.example.proIt.dto.HourlyForecastResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class OpenMeteoApiService {

    @Autowired
    RestTemplate restTemplate;


    public CityListResponseDTO getLocationListByCity(String cityName) {
        URI uri = UriComponentsBuilder.fromUriString("https://geocoding-api.open-meteo.com/v1/search").
                queryParam("name", cityName).queryParam("count", 100).build().toUri();
        CityListResponseDTO cities = restTemplate.getForObject(uri, CityListResponseDTO.class);
        return cities;

    }


    public DailyForcastResponseDTO getDailyForecast(LocationDTO locationDTO) {

        String[] dailyForeaseParameters = {"temperature_2m_max", "temperature_2m_min", "apparent_temperature_max",
                "apparent_temperature_min", "rain_sum"};


        URI uri = UriComponentsBuilder.fromUriString("https://api.open-meteo.com/v1/forecast").
                queryParam("latitude", locationDTO.getLatitude())
                .queryParam("longitude", locationDTO.getLongitude())
                .queryParam("daily", String.join(",", dailyForeaseParameters))
                .build().toUri();

        DailyForcastResponseDTO dailyForecast = restTemplate.getForObject(uri, DailyForcastResponseDTO.class);
        return dailyForecast;

    }


    public HourlyForecastResponseDTO getHourlyForecast(double latitude, double logitude) {

        String[] dailyForeaseParameters = {"temperature_2m", "rain", "surface_pressure",
                "wind_speed_10m", "wind_speed_80m", "wind_speed_120m", "wind_speed_180m"};


        URI uri = UriComponentsBuilder.fromUriString("https://api.open-meteo.com/v1/forecast").
                queryParam("latitude", latitude)
                .queryParam("longitude", logitude)
                .queryParam("hourly", String.join(",", dailyForeaseParameters))
                .queryParam("forecast_days", 1)
                .build().toUri();

        RestTemplate restTemplate = new RestTemplate();

        HourlyForecastResponseDTO hourlyForecast = restTemplate.getForObject(uri, HourlyForecastResponseDTO.class);
        return hourlyForecast;
    }



}
