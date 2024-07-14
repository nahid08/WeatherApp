package com.example.proIt.dto;

import java.util.List;

public class CityListResponseDTO {

//    @JsonProperty("results")
    private List<LocationDTO> results;

    public List<LocationDTO> getResults() {
        return results;
    }

    public void setResults(List<LocationDTO> locationDTOList) {
        this.results = locationDTOList;
    }
}
