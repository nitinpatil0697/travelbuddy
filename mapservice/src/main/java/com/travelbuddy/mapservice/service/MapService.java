package com.travelbuddy.mapservice.service;

import com.travelbuddy.mapservice.api.response.GeneralResponse;
import org.springframework.http.ResponseEntity;

public interface MapService {

    ResponseEntity<GeneralResponse> searchLocations(String query);

    ResponseEntity<GeneralResponse> getLocationDetails(Long id);

    ResponseEntity<GeneralResponse> calculateRoute(Long startLocationId, Long endLocationId);

    ResponseEntity<GeneralResponse> getNearbyLocations(double latitude, double longitude, double radius);
}
