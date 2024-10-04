package com.travelbuddy.tripservice.service;

import com.travelbuddy.tripservice.api.request.CreateTripRequest;
import com.travelbuddy.tripservice.api.response.GeneralResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface TripService {
    ResponseEntity<GeneralResponse> createTrip(CreateTripRequest createTripRequest);
}
