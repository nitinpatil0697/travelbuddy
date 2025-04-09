package com.travelbuddy.tripservice.service;

import com.travelbuddy.tripservice.api.request.CreateTripRequest;
import com.travelbuddy.tripservice.api.response.GeneralResponse;
import com.travelbuddy.tripservice.model.PlacesEntity;
import com.travelbuddy.tripservice.model.TripEntity;
import com.travelbuddy.tripservice.repository.PlacesRepositoryInterface;
import com.travelbuddy.tripservice.repository.TripRepositoryInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
            List<String> touristPlaces = Arrays.asList(createTripRequest.getTouristPlaces().split(","));
            TripEntity newTrip = TripEntity.builder()
                    .tripName(createTripRequest.getTripName())
                    .tripType(createTripRequest.getTripType())
                    .origin(createTripRequest.getStartLocation())
                    .destination(createTripRequest.getDestination())
                    .startDate(createTripRequest.getStartDate())
                    .endDate(createTripRequest.getEndDate())
                    .touristPlaces(touristPlaces)
                    .estimatedExpenses(BigDecimal.valueOf(createTripRequest.getEstimatedExpenses()))
                    .tripDescription(createTripRequest.getDescription())
                    .build();
            TripEntity savedTrip = tripRepository.save(newTrip);
            log.info("Creating trip saved successfully.");
            Map<String, Object> result = prepareTripResponseResult(savedTrip);
            log.info("Creating trip response: {}", createTripResponse);
            createTripResponse.setStatus("Success");
            createTripResponse.setMessage("Created trip successfully.");
            createTripResponse.setResult(result);
        } catch (Exception e) {
            createTripResponse.setStatus("Failed");
            createTripResponse.setMessage("Failed to create trip.");
        }
        return new ResponseEntity<>(createTripResponse, HttpStatus.CREATED);
    }

    /**
     * To prepare Trip Response
     *
     * @param savedTrip
     * @return
     */
    private Map<String, Object> prepareTripResponseResult(TripEntity savedTrip) {
        Map<String, Object> tripResponse = new HashMap<>();
        tripResponse.put("status", "created");
        tripResponse.put("tripId", savedTrip.getId());
        tripResponse.put("tripName", savedTrip.getTripName());
        tripResponse.put("tripType", savedTrip.getTripType());
        tripResponse.put("places", savedTrip.getTouristPlaces());
        tripResponse.put("estimatedExpenses", savedTrip.getEstimatedExpenses());
        tripResponse.put("userDetails", "Test User");
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
