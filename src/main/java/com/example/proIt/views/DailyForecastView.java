package com.example.proIt.views;

import com.example.proIt.dto.LocationDTO;
import com.example.proIt.dto.DailyForcastResponseDTO;
import com.example.proIt.layout.NavBarLayout;
import com.example.proIt.model.FavoriteLocation;
import com.example.proIt.model.User;
import com.example.proIt.service.FavoriteLocationService;
import com.example.proIt.service.OpenMeteoApiService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.PermitAll;
import java.util.List;

@PermitAll
@Route(value = "location/forecast/daily", layout = NavBarLayout.class)
public class DailyForecastView extends VerticalLayout  {

    LocationDTO locationDTO;
    AuthenticationContext authenticationContext;
    OpenMeteoApiService openMeteoApiService;
    FavoriteLocationService favoriteLocationService;


    DailyForecastView(OpenMeteoApiService openMeteoApiService, FavoriteLocationService favoriteLocationService, AuthenticationContext authenticationContext) {
        this.openMeteoApiService = openMeteoApiService;
        this.favoriteLocationService = favoriteLocationService;
        this.authenticationContext = authenticationContext;
        this.locationDTO = (LocationDTO) VaadinSession.getCurrent().getAttribute("city");

        H1 h1 = new H1("Daily Temperature of " + this.locationDTO.getName().toUpperCase() );
        add(h1);
        getDailyForecast(this.locationDTO);
        setAlignItems(Alignment.CENTER);
        addHourlyButton();
        addFavoriteButton();
    }
    
    public void addHourlyButton() {
        Button showHourlyButton = new Button("Show Hourly Forecast  ");
        showHourlyButton.addClickListener(e -> {
            UI.getCurrent().navigate(HourlyForecastView.class);
        });

        add(showHourlyButton);
    }
    
    public void addFavoriteButton() {
        Button addFavoriteButton = new Button("Add this location to favorite", e -> {
            FavoriteLocation newFavoriteLocation = new FavoriteLocation();
            newFavoriteLocation.setCountry(this.locationDTO.getCountry());
            newFavoriteLocation.setLatitude(this.locationDTO.getLatitude());
            newFavoriteLocation.setLongitude(this.locationDTO.getLongitude());
            newFavoriteLocation.setName(this.locationDTO.getName());
            newFavoriteLocation.setUser((User) VaadinSession.getCurrent().getAttribute("user"));
            if(isLocationIsAleadyAdded(newFavoriteLocation)) {
                Notification.show("This location has already added to the favorite list.");
            } else {
                FavoriteLocation savedFavoriteLocation = this.favoriteLocationService.saveFavoriteLocation(newFavoriteLocation);
                List<FavoriteLocation> favoriteLocationList = (List<FavoriteLocation>) VaadinSession.getCurrent().getAttribute("favoriteLocationList");
                favoriteLocationList.add(savedFavoriteLocation);
                Notification.show("The record has been added to the list successfully!!");
            }

        });
        add(addFavoriteButton);
        
    }

    public boolean isLocationIsAleadyAdded(FavoriteLocation favoriteLocation) {
        List<FavoriteLocation> favoriteLocationList = (List<FavoriteLocation>) VaadinSession.getCurrent().getAttribute("favoriteLocationList");
        return favoriteLocationList.stream().anyMatch(e -> {
            if(e.getLatitude() == favoriteLocation.getLatitude() && e.getLongitude() == favoriteLocation.getLongitude()) {
                return true;
            }
            return false;
        });
    }

    public void getDailyForecast(LocationDTO locationDTO) {
        DailyForcastResponseDTO dailyForecast = this.openMeteoApiService.getDailyForecast(locationDTO);
        chartConfiguration(dailyForecast);
    }

    public void chartConfiguration(DailyForcastResponseDTO dailyForecast) {
        Chart chart = new Chart(ChartType.LINE);

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Daily Forecast");

        XAxis xAxis = new XAxis();
        xAxis.setCategories(dailyForecast.getDaily().getTime());
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Weather");
        conf.addyAxis(yAxis);

        ListSeries temperatureMaxSeries = new ListSeries("Temperature Max", dailyForecast.getDaily().getApparent_temperature_max());
        ListSeries temperatureMinSeries = new ListSeries("Temperature Min", dailyForecast.getDaily().getApparent_temperature_min());
        ListSeries temperature2mMaxSeries = new ListSeries("Temperature 2m Max", dailyForecast.getDaily().getTemperature_2m_max());
        ListSeries temperature2mMinSeries = new ListSeries("Temperature 2m Min", dailyForecast.getDaily().getTemperature_2m_min());
        ListSeries rainSumSeries = new ListSeries("Rain Sum", dailyForecast.getDaily().getRain_sum());

        conf.addSeries(temperatureMaxSeries);
        conf.addSeries(temperatureMinSeries);
        conf.addSeries(temperature2mMaxSeries);
        conf.addSeries(temperature2mMinSeries);
        conf.addSeries(rainSumSeries);

        add(chart);

    }


}
