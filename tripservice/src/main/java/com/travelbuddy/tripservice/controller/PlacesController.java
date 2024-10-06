package com.travelbuddy.tripservice.controller;

import com.travelbuddy.tripservice.service.PlacesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/places")
public class PlacesController {

    @Autowired
    private PlacesService placesService;

    @PostMapping("/upload-csv")
    public String uploadCSV() {
        placesService.savePlacesFromCSV("E:\\Career\\spring\\TravelBuddy\\tripservice\\src\\main\\java\\com\\travelbuddy\\tripservice\\files\\places_import.csv");
        return "Places inserted successfully!";
    }
}
