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
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.PermitAll;

import java.awt.*;
import java.util.List;

@PermitAll
@Route(value = "location/forecast/daily", layout = NavBarLayout.class)
public class DailyForecastView extends VerticalLayout  {

    LocationDTO locationDTO;
    OpenMeteoApiService openMeteoApiService;
    FavoriteLocationService favoriteLocationService;

    TextField favoriteLocationDescription;

    Dialog dialog;


    DailyForecastView(OpenMeteoApiService openMeteoApiService, FavoriteLocationService favoriteLocationService) {
        this.openMeteoApiService = openMeteoApiService;
        this.favoriteLocationService = favoriteLocationService;
        this.locationDTO = (LocationDTO) VaadinSession.getCurrent().getAttribute("city");

        H1 h1 = new H1("Daily Temperature of " + this.locationDTO.getName().toUpperCase() );
        add(h1);
        getDailyForecast(this.locationDTO);
        setAlignItems(Alignment.CENTER);
        addHourlyButton();
        addFavoriteButton();
        configureDialog();
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
                dialog.open();
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

    private void configureDialog() {
        this.dialog = new Dialog("Description");
        dialog.add(createDialogLayout());
        Button okButton = new Button("Ok", e -> {
            FavoriteLocation newFavoriteLocation = new FavoriteLocation();
            newFavoriteLocation.setCountry(this.locationDTO.getCountry());
            newFavoriteLocation.setLatitude(this.locationDTO.getLatitude());
            newFavoriteLocation.setLongitude(this.locationDTO.getLongitude());
            newFavoriteLocation.setName(this.locationDTO.getName());
            newFavoriteLocation.setDescription(favoriteLocationDescription.getValue());
            newFavoriteLocation.setUser((User) VaadinSession.getCurrent().getAttribute("user"));
            FavoriteLocation savedFavoriteLocation = this.favoriteLocationService.saveFavoriteLocation(newFavoriteLocation);
            List<FavoriteLocation> favoriteLocationList = (List<FavoriteLocation>) VaadinSession.getCurrent().getAttribute("favoriteLocationList");
            favoriteLocationList.add(savedFavoriteLocation);
            Notification.show("The record has been added to the list successfully!!");
            dialog.close();
        });
        dialog.getFooter().add(okButton);
        add(dialog);

        // Center the button within the example
        getStyle().set("position", "fixed").set("top", "0").set("right", "0")
                .set("bottom", "0").set("left", "0").set("display", "flex")
                .set("align-items", "center").set("justify-content", "center");

    }

    private VerticalLayout createDialogLayout() {

        this.favoriteLocationDescription = new TextField("Enter Description");

        VerticalLayout dialogLayout = new VerticalLayout(favoriteLocationDescription);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");

        return dialogLayout;
    }

}
