package com.travelbuddy.tripservice.service;

import com.travelbuddy.tripservice.api.request.CreateTripRequest;
import com.travelbuddy.tripservice.api.response.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class TripServiceImpl implements TripService{
    @Override
    public ResponseEntity<GeneralResponse> createTrip(CreateTripRequest createTripRequest) {
        GeneralResponse createTripResponse = new GeneralResponse();
        return new ResponseEntity<>(createTripResponse, HttpStatus.CREATED);
    }
}
