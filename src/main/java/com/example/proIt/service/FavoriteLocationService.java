package com.example.proIt.service;


import com.example.proIt.model.FavoriteLocation;
import com.example.proIt.model.User;
import com.example.proIt.repository.FavoriteLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteLocationService {

    @Autowired
    FavoriteLocationRepository favoriteLocationRepository;

    public FavoriteLocation saveFavoriteLocation(FavoriteLocation favoriteLocation) {
        FavoriteLocation newfavoriteLocation = favoriteLocationRepository.save(favoriteLocation);
        return newfavoriteLocation;
    }

    public List<FavoriteLocation> getFavoriteLocationByUser(User user) {
        return favoriteLocationRepository.getFavoriteLocationByUser(user);
    }

}
