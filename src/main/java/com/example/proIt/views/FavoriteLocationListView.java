package com.example.proIt.views;

import com.example.proIt.dto.LocationDTO;
import com.example.proIt.layout.NavBarLayout;
import com.example.proIt.model.FavoriteLocation;
import com.example.proIt.model.User;
import com.example.proIt.service.FavoriteLocationService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import jakarta.annotation.security.PermitAll;
import java.util.List;
import java.util.Optional;

@Route(value = "location/favorite", layout = NavBarLayout.class)
@PermitAll
public class FavoriteLocationListView extends VerticalLayout {

    FavoriteLocationService favoriteLocationService;

    FavoriteLocationListView(FavoriteLocationService favoriteLocationService) {
        this.favoriteLocationService = favoriteLocationService;
        User user = (User) VaadinSession.getCurrent().getAttribute("user");
        add(new H1("Favorite Location List of " + user.getUserName()));
        setAlignItems(Alignment.CENTER);

        List<FavoriteLocation> favoriteLocationList = (List<FavoriteLocation>) VaadinSession.getCurrent().getAttribute("favoriteLocationList");
        gridConfiguration(favoriteLocationList);
    }


    public void gridConfiguration(List<FavoriteLocation> favoriteLocations) {

        Grid<FavoriteLocation> grid = new Grid<>(FavoriteLocation.class, false);
        grid.addColumn(FavoriteLocation::getName).setHeader("City Name");
        grid.addColumn(FavoriteLocation::getCountry).setHeader("Country");
        grid.addColumn(FavoriteLocation::getLatitude).setHeader("Latitude");
        grid.addColumn(FavoriteLocation::getLongitude).setHeader("Longitude");

        grid.setItems(favoriteLocations);

        grid.addSelectionListener(selectionEvent -> {
            Optional<FavoriteLocation> favoriteLocation = selectionEvent.getFirstSelectedItem();
            if(favoriteLocation.isPresent()) {
                LocationDTO locationDTO = new LocationDTO();
                locationDTO.setName(favoriteLocation.get().getName());
                locationDTO.setCountry(favoriteLocation.get().getCountry());
                locationDTO.setLatitude(favoriteLocation.get().getLatitude());
                locationDTO.setLongitude(favoriteLocation.get().getLongitude());
                VaadinSession.getCurrent().setAttribute("city", locationDTO);
                UI.getCurrent().navigate(DailyForecastView.class);
            }
        });

        add(grid);
    }


}
