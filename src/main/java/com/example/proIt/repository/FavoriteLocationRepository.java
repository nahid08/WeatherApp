package com.example.proIt.repository;

import com.example.proIt.model.FavoriteLocation;
import com.example.proIt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface FavoriteLocationRepository extends JpaRepository<FavoriteLocation, Long> {

    public List<FavoriteLocation> getFavoriteLocationByUser(User user);

}
