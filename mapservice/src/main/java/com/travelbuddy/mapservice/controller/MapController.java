package com.travelbuddy.mapservice.controller;

import com.travelbuddy.mapservice.api.response.GeneralResponse;
import com.travelbuddy.mapservice.service.MapService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/map")
public class MapController {

    MapService mapService;

    // 1. Endpoint for Searching Locations
    @GetMapping("/search")
    public ResponseEntity<GeneralResponse> searchLocations(@RequestParam String query) {
        return mapService.searchLocations(query);
    }

    // 2. Endpoint for Getting Location Details
    @GetMapping("/location/{id}")
    public ResponseEntity<GeneralResponse> getLocationDetails(@PathVariable Long id) {
        return mapService.getLocationDetails(id);
    }

    // 3. Endpoint for Calculating Route
    @GetMapping("/route")
    public ResponseEntity<GeneralResponse> calculateRoute(
            @RequestParam Long startLocationId,
            @RequestParam Long endLocationId) {
        return mapService.calculateRoute(startLocationId, endLocationId);
    }

    // 4. Endpoint for Getting Nearby Locations
    @GetMapping("/nearby")
    public ResponseEntity<GeneralResponse> getNearbyLocations(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam double radius) {
        return mapService.getNearbyLocations(latitude, longitude, radius);
     }

}
