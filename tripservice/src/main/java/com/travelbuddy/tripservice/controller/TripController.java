package com.travelbuddy.tripservice.controller;

import com.travelbuddy.tripservice.api.request.CreateTripRequest;
import com.travelbuddy.tripservice.api.response.GeneralResponse;
import com.travelbuddy.tripservice.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("trip")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @PostMapping
    @RequestMapping("create")
    public ResponseEntity<GeneralResponse> createTrip(@RequestBody CreateTripRequest createTripRequest) {
        return tripService.createTrip(createTripRequest);
    }

    @GetMapping
    @RequestMapping("places")
    public ResponseEntity<GeneralResponse> getAllPlaces() {
        return tripService.getAllPlaces();
    }

}
