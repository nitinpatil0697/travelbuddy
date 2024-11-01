package com.travelbuddy.mapservice.service;

import com.travelbuddy.mapservice.api.response.GeneralResponse;
import org.springframework.http.ResponseEntity;

public class MapServiceImpl implements MapService {

    @Override
    public ResponseEntity<GeneralResponse> searchLocations(String query) {
        return null;
    }

    @Override
    public ResponseEntity<GeneralResponse> getLocationDetails(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<GeneralResponse> calculateRoute(Long startLocationId, Long endLocationId) {
        return null;
    }

    @Override
    public ResponseEntity<GeneralResponse> getNearbyLocations(double latitude, double longitude, double radius) {
        return null;
    }
}
