package com.example.proIt.views;

import com.example.proIt.dto.LocationDTO;
import com.example.proIt.layout.NavBarLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import jakarta.annotation.security.PermitAll;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Route(value = "location/list", layout = NavBarLayout.class)
@PermitAll
public class LocationListView extends VerticalLayout {

    private final int pageSize = 10;
    private int currentPage = 0;
    private String currentFilter = "";
    private  Grid<LocationDTO> grid;

    private int length;

    private  List<LocationDTO> locationList;

    public LocationListView() {
        locationList = (List<LocationDTO>) VaadinSession.getCurrent().getAttribute("cityList");
        if(locationList == null) {
            locationList = new ArrayList<>();
        }
        grid = new Grid<>(LocationDTO.class, false);
        addColumns();
        grid.setPageSize(pageSize);
        addOnRowClick();

        addFilterField();
        addPagination();
        add(grid);
        updateGrid();
    }

    void addColumns() {
        grid.addColumn(LocationDTO::getName).setHeader("City Name");
        grid.addColumn(LocationDTO::getCountry).setHeader("Country");
        grid.addColumn(LocationDTO::getTimezone).setHeader("TimeZone");
        grid.addColumn(LocationDTO::getLatitude).setHeader("Latitude");
        grid.addColumn(LocationDTO::getLongitude).setHeader("Longitude");
    }
    
    void addFilterField() {
        TextField filterField = new TextField();
        filterField.setPlaceholder("Filter...");
        filterField.setValueChangeMode(ValueChangeMode.EAGER);
        filterField.addValueChangeListener(e -> {
            currentFilter = e.getValue();
            currentPage = 0; // Reset to the first page when filtering
            updateGridAfterFiltering();
        });
        add(filterField);
    }
    
    void addPagination() {
      
        Button prevButton = new Button("Previous", e -> previousPage());
        Button nextButton = new Button("Next", e -> nextPage());
        HorizontalLayout paginator = new HorizontalLayout(prevButton, nextButton);
        add(paginator);
    }

    public void addOnRowClick() {
        grid.addSelectionListener(selectionEvent -> {
            Optional<LocationDTO> city = selectionEvent.getFirstSelectedItem();
            if(city.isPresent()) {
                VaadinSession.getCurrent().setAttribute("city", city.get());
                UI.getCurrent().navigate(DailyForecastView.class);
            }
        });
    }
    
    private void previousPage() {
        if (currentPage > 0) {
            currentPage--;
            updateGrid();
        } else {
            Notification.show("You're already on the first page.");
        }
    }

    private void nextPage() {
        if ((currentPage + 1) * pageSize < length) {
            currentPage++;
            updateGrid();
        } else {
            Notification.show("You're already on the last page.");
        }
    }

    private void updateGrid() {
       length = locationList.size();
       grid.setItems(locationList.stream().skip(currentPage * pageSize).limit(pageSize).collect(Collectors.toList()));
    }

    private void updateGridAfterFiltering() {
        List<LocationDTO> locationList = (List<LocationDTO>) VaadinSession.getCurrent().getAttribute("cityList");
        List<LocationDTO> filteredList = locationList.stream().filter(item -> {
            String location = item.getName();
            int length = currentFilter.length();
            if(location.length() < length) {
                return false;
            }
            if(length ==0 || location.toLowerCase().startsWith(currentFilter.toLowerCase())) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());

        length = filteredList.size();
        grid.setItems(filteredList.stream().skip(currentPage * pageSize).limit(pageSize).collect(Collectors.toList()));
        
    }
    
}