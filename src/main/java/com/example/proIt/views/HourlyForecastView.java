package com.example.proIt.views;

import com.example.proIt.dto.LocationDTO;
import com.example.proIt.dto.HourlyDTO;
import com.example.proIt.dto.HourlyForecastResponseDTO;
import com.example.proIt.layout.NavBarLayout;
import com.example.proIt.service.OpenMeteoApiService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import jakarta.annotation.security.PermitAll;

@Route(value = "location/forecast/hourly", layout = NavBarLayout.class)
@PermitAll
public class HourlyForecastView extends VerticalLayout {

    LocationDTO locationDTO;
    OpenMeteoApiService openMeteoApiService;

    HourlyForecastView(OpenMeteoApiService openMeteoApiService) {
        this.openMeteoApiService = openMeteoApiService;
        this.locationDTO = (LocationDTO) VaadinSession.getCurrent().getAttribute("city");
        add(new H1("Show Hourly Forecast of " + locationDTO.getName().toUpperCase()));
        setAlignItems(Alignment.CENTER);

        getHourlyForecast(this.locationDTO);
        addDailyButton();
    }

    public void addDailyButton() {
        Button addDailyButton = new Button("Show Daily Forecast");
        addDailyButton.addClickListener(e -> {
            UI.getCurrent().navigate(DailyForecastView.class);
        });

        add(addDailyButton);

    }
    public void getHourlyForecast(LocationDTO locationDTO) {

        HourlyForecastResponseDTO hourlyForecast = openMeteoApiService.getHourlyForecast(locationDTO.getLatitude(), locationDTO.getLongitude());
        chartConfiguration(hourlyForecast);
    }

    public void chartConfiguration(HourlyForecastResponseDTO hourlyForecast) {
        Chart chart = new Chart(ChartType.LINE);

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Daily forecast");

        XAxis xAxis = new XAxis();
        xAxis.setCategories(hourlyForecast.getHourly().getTime());
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Weather");
        conf.addyAxis(yAxis);

        HourlyDTO hourlyForecastData = hourlyForecast.getHourly();


        ListSeries temperatureSeries = new ListSeries("Temperature 2m", hourlyForecastData.getTemperature_2m());
        ListSeries surfacePressureSeries = new ListSeries("Surface Pressure", hourlyForecastData.getSurface_pressure());
        ListSeries windSpeed10mPressureSeries = new ListSeries("Wind Speed 10m", hourlyForecastData.getWind_speed_10m());
        ListSeries windSpeed80mPressureSeries = new ListSeries("Wind Speed 80m", hourlyForecastData.getWind_speed_80m());
        ListSeries windSpeed120mPressureSeries = new ListSeries("Wind Speed 120m", hourlyForecastData.getWind_speed_120m());
        ListSeries windSpeed180mPressureSeries = new ListSeries("Wind Speed 180m", hourlyForecastData.getWind_speed_180m());
        ListSeries rainSeries = new ListSeries("Rain", hourlyForecastData.getRain());

        conf.addSeries(temperatureSeries);
        conf.addSeries(surfacePressureSeries);
        conf.addSeries(windSpeed10mPressureSeries);
        conf.addSeries(windSpeed80mPressureSeries);
        conf.addSeries(windSpeed120mPressureSeries);
        conf.addSeries(windSpeed180mPressureSeries);
        conf.addSeries(rainSeries);

        add(chart);

    }


}
