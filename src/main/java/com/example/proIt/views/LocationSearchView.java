package com.example.proIt.views;


import com.example.proIt.dto.*;
import com.example.proIt.layout.NavBarLayout;
import com.example.proIt.service.OpenMeteoApiService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.ArrayList;

@Route(value = "location/search", layout = NavBarLayout.class)
@AnonymousAllowed
public class LocationSearchView extends VerticalLayout {

    OpenMeteoApiService openMeteoApiService;

    public LocationSearchView(OpenMeteoApiService openMeteoApiService) {
        this.openMeteoApiService = openMeteoApiService;
        H1 h1 = new H1("Search Location");
        add(h1);
        Input cityInput = new Input();
        cityInput.setWidth("40%");
        cityInput.setHeight("20px");
        add(cityInput);
        addSearchButton(cityInput);

        setAlignItems(Alignment.CENTER);

    }

    public void addSearchButton(Input cityInput) {
        Button searchButton = new Button("Search");
        searchButton.addThemeVariants(ButtonVariant.LUMO_LARGE);
        searchButton.addClickListener(e -> {
            getLocationListByCity(cityInput.getValue());
            searchButton.getUI().ifPresent(ui -> {
                ui.navigate(LocationListView.class);
            });
        });
        add(searchButton);
    }

    public void getLocationListByCity(String cityName) {

       try {

           CityListResponseDTO cities = openMeteoApiService.getLocationListByCity(cityName);

           VaadinSession.getCurrent().setAttribute("cityList", cities.getResults() != null ? cities.getResults() : new ArrayList<LocationDTO>());

       } catch (Exception e) {
           e.printStackTrace();
       }
    }

}
