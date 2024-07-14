package com.example.proIt.model;


import jakarta.persistence.*;

import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    private String username;
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<FavoriteLocation> favoriteLocations;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<FavoriteLocation> getFavoriteLocations() {
        return favoriteLocations;
    }

    public void setFavoriteLocations(Set<FavoriteLocation> favoriteLocations) {
        this.favoriteLocations = favoriteLocations;
    }
}
