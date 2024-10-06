package com.travelbuddy.tripservice.service;

import com.travelbuddy.tripservice.api.request.CreateTripRequest;
import com.travelbuddy.tripservice.api.response.GeneralResponse;
import com.travelbuddy.tripservice.model.PlacesEntity;
import com.travelbuddy.tripservice.model.TripEntity;
import com.travelbuddy.tripservice.repository.PlacesRepositoryInterface;
import com.travelbuddy.tripservice.repository.TripRepositoryInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.isNull;

@Service
@Slf4j
public class TripServiceImpl implements TripService{

    @Autowired
    TripRepositoryInterface tripRepository;
    @Autowired
    private PlacesRepositoryInterface placesRepositoryInterface;

    @Override
    public ResponseEntity<GeneralResponse> createTrip(CreateTripRequest createTripRequest) {
        log.info("Creating trip request: {}", createTripRequest);
        GeneralResponse createTripResponse = new GeneralResponse();
        try {
            if (!validateCreateTripRequest(createTripRequest)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Create Trip Request");
            }

            TripEntity newTrip = new TripEntity();
            List<String> touristPlaces = Arrays.asList(createTripRequest.getTouristPlaces().split(","));
            newTrip.setTripName(createTripRequest.getTripName());
            newTrip.setTripType(createTripRequest.getTripType());
            newTrip.setOrigin(createTripRequest.getStartLocation());
            newTrip.setDestination(createTripRequest.getDestination());
            newTrip.setStartDate(createTripRequest.getStartDate());
            newTrip.setEndDate(createTripRequest.getEndDate());
            newTrip.setTouristPlaces(touristPlaces);
            newTrip.setEstimatedExpenses(BigDecimal.valueOf(createTripRequest.getEstimatedExpenses()));
            newTrip.setTripDescription(createTripRequest.getDescription());
            tripRepository.save(newTrip);
            log.info("Creating trip saved successfully.");

            log.info("Creating trip response: {}", createTripResponse);
            createTripResponse.setStatus("Success");
            createTripResponse.setMessage("Created trip successfully.");
        } catch (Exception e) {
            createTripResponse.setMessage("Failed to create trip.");
        }

        return new ResponseEntity<>(createTripResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<GeneralResponse> getAllPlaces() {
        GeneralResponse placesResponse = new GeneralResponse();
        List<PlacesEntity> allPlaces = placesRepositoryInterface.findAll();

        placesResponse.setStatus("Success");
        placesResponse.setMessage("Found " + allPlaces.size() + " places");
        placesResponse.setResult(allPlaces);

        return new ResponseEntity<>(placesResponse, HttpStatus.OK);
    }

    private boolean validateCreateTripRequest(CreateTripRequest createTripRequest) {
        return !isNull(createTripRequest.getTripName()) &&
                 !isNull(createTripRequest.getEstimatedExpenses()) &&
                !isNull(createTripRequest.getTouristPlaces()) && !isNull(createTripRequest.getStartDate());
    }
}
