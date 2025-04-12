package com.travelbuddy.tripservice.controller;

import com.travelbuddy.tripservice.api.request.CalculateExpenseRequest;
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

    @GetMapping
    @RequestMapping("getAll")
    public ResponseEntity<GeneralResponse> getTripDetails() {
        return tripService.getAllTripDetails();
    }

    @PostMapping
    @RequestMapping("create")
    public ResponseEntity<GeneralResponse> createTrip(@RequestBody CreateTripRequest createTripRequest) {
        return tripService.createTrip(createTripRequest);
    }

    @PutMapping
    @RequestMapping("update/{tripId}")
    public ResponseEntity<GeneralResponse> updateTrip(@PathVariable Integer tripId, @RequestBody CreateTripRequest updateRequest) {
        return tripService.updateTrip(tripId, updateRequest);
    }

    @GetMapping
    @RequestMapping("places")
    public ResponseEntity<GeneralResponse> getAllPlaces() {
        return tripService.getAllPlaces();
    }

    @PostMapping
    @RequestMapping("calculate-expense")
    public ResponseEntity<GeneralResponse> calculateTripExpense(@RequestBody CalculateExpenseRequest expenseRequest) {
        return tripService.calculateTripExpense(expenseRequest);
    }


}
