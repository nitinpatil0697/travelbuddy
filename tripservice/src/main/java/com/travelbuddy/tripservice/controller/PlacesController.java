package com.travelbuddy.tripservice.controller;

import com.travelbuddy.tripservice.api.response.GeneralResponse;
import com.travelbuddy.tripservice.service.PlacesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("places")
public class PlacesController {

    @Autowired
    private PlacesService placesService;

    @PostMapping("/upload-csv")
    public String uploadCSV() {
        placesService.savePlacesFromCSV("E:\\Career\\spring\\TravelBuddy\\tripservice\\src\\main\\java\\com\\travelbuddy\\tripservice\\files\\places_import.csv");
        return "Places inserted successfully!";
    }

    @GetMapping("allPlaces")
    public ResponseEntity<GeneralResponse> getAllPlaces() {
        return placesService.getAllPlaces();
    }
}
