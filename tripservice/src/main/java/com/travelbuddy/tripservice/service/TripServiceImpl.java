package com.travelbuddy.tripservice.service;

import com.travelbuddy.tripservice.api.request.CreateTripRequest;
import com.travelbuddy.tripservice.api.response.GeneralResponse;
import com.travelbuddy.tripservice.model.PlacesEntity;
import com.travelbuddy.tripservice.model.TripEntity;
import com.travelbuddy.tripservice.repository.PlacesRepositoryInterface;
import com.travelbuddy.tripservice.repository.TripRepositoryInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class TripServiceImpl implements TripService{

    @Autowired
    TripRepositoryInterface tripRepository;
    @Autowired
    private PlacesRepositoryInterface placesRepositoryInterface;
    @Autowired
    private ExpenseServiceImpl expenseServiceImpl;

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
            Map<String, Object> tripData = new HashMap<>();
            Map<String, Object> result = prepareTripResponseResult(createTripRequest, tripData);
            log.info("Creating trip response: {}", createTripResponse);
            createTripResponse.setStatus("Success");
            createTripResponse.setMessage("Created trip successfully.");
            createTripResponse.setResult(result);
        } catch (Exception e) {
            createTripResponse.setMessage("Failed to create trip.");
        }
        return new ResponseEntity<>(createTripResponse, HttpStatus.CREATED);
    }

    /**
     * To prepare Trip Response
     *
     * @param createTripRequest
     * @param tripData
     * @return
     */
    private Map<String, Object> prepareTripResponseResult(
            CreateTripRequest createTripRequest, Map<String, Object> tripData) {
        Map<String, Object> tripResponse = new HashMap<>();
        tripResponse.put("status", "created");
        tripResponse.put("tripId", tripData.get("tripId"));
        tripResponse.put("tripName", tripData.get("tripName"));
        tripResponse.put("tripType", tripData.get("tripType"));
        tripResponse.put("places", tripData.get("places"));
        tripResponse.put("estimatedExpenses", tripData.get("estimatedExpenses"));
        tripResponse.put("userDetails", tripData.get("user"));
        return tripResponse;
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
