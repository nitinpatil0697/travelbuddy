package com.travelbuddy.tripservice.controller;

import com.travelbuddy.tripservice.api.request.CreateTripRequest;
import com.travelbuddy.tripservice.api.response.GeneralResponse;
import com.travelbuddy.tripservice.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
