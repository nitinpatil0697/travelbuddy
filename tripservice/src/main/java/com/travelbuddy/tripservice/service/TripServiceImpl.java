package com.travelbuddy.tripservice.service;

import com.travelbuddy.tripservice.api.request.CalculateExpenseRequest;
import com.travelbuddy.tripservice.api.request.CreateTripRequest;
import com.travelbuddy.tripservice.api.response.GeneralResponse;
import com.travelbuddy.tripservice.constants.ServiceConstants;
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
import java.util.*;

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
            createTripResponse.setStatus(ServiceConstants.SUCCESS);
            createTripResponse.setMessage("Created trip successfully.");
            createTripResponse.setResult(result);
        } catch (Exception e) {
            createTripResponse.setStatus(ServiceConstants.FAILURE);
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

        placesResponse.setStatus(ServiceConstants.SUCCESS);
        placesResponse.setMessage("Found " + allPlaces.size() + " places");
        placesResponse.setResult(allPlaces);

        return new ResponseEntity<>(placesResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GeneralResponse> updateTrip(Integer tripId, CreateTripRequest updateRequest) {
        GeneralResponse generalResponse = new GeneralResponse();
        String status = "", message = "";
        try {
            Optional<TripEntity> optionalTrip = tripRepository.findById(tripId);
            if (optionalTrip.isEmpty()) {
                generalResponse.setStatus(ServiceConstants.FAILURE);
                generalResponse.setMessage("Requested Trip details not available");
                return new ResponseEntity<>(generalResponse, HttpStatus.NOT_FOUND);
            } else {
                TripEntity existingTrip = optionalTrip.get();
                if (updateRequest.getTripName() != null)
                    existingTrip.setTripName(updateRequest.getTripName());
                if (updateRequest.getTripType() != null)
                    existingTrip.setTripType(updateRequest.getTripType());
                if (updateRequest.getStartLocation() != null)
                    existingTrip.setOrigin(updateRequest.getStartLocation());
                if (updateRequest.getDestination() != null)
                    existingTrip.setDestination(updateRequest.getDestination());
                if (updateRequest.getStartDate() != null)
                    existingTrip.setStartDate(updateRequest.getStartDate());
                if (updateRequest.getEndDate() != null)
                    existingTrip.setEndDate(updateRequest.getEndDate());
                if (updateRequest.getTouristPlaces() != null)
                    existingTrip.setTouristPlaces(new ArrayList<>(Arrays.asList(updateRequest.getTouristPlaces().split(","))));
                if (updateRequest.getEstimatedExpenses() != 0)
                    existingTrip.setEstimatedExpenses(BigDecimal.valueOf(updateRequest.getEstimatedExpenses()));
                if (updateRequest.getDescription() != null)
                    existingTrip.setTripDescription(updateRequest.getDescription());
                TripEntity updatedTrip = tripRepository.save(existingTrip);
                status = ServiceConstants.SUCCESS;
                message = "Update done for trip";
            }
        } catch (Exception e) {
            status = ServiceConstants.FAILURE;
            message = "Exception occurred in update trip.";
        }
        generalResponse.setStatus(status);
        generalResponse.setMessage(message);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GeneralResponse> getAllTripDetails() {
        GeneralResponse response = new GeneralResponse();
        try {
            List<TripEntity> allTripDetails = tripRepository.findAll();
            response.setStatus(ServiceConstants.SUCCESS);
            response.setMessage("Fetched all the trip records.");
            response.setResult(allTripDetails);
        } catch (Exception e){
            response.setStatus(ServiceConstants.FAILURE);
            response.setMessage("Exception occurred while getting trip details");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GeneralResponse> calculateTripExpense(CalculateExpenseRequest expenseRequest) {

        Double expenses = expenseServiceImpl.prepareAllExpenses(expenseRequest);
        GeneralResponse generalResponse = new GeneralResponse();
        generalResponse.setStatus(ServiceConstants.SUCCESS);
        generalResponse.setMessage("Calculated trip expenses.");
        generalResponse.setResult(expenses);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    private boolean validateCreateTripRequest(CreateTripRequest createTripRequest) {
        return !isNull(createTripRequest.getTripName()) &&
                 !isNull(createTripRequest.getEstimatedExpenses()) &&
                !isNull(createTripRequest.getTouristPlaces()) && !isNull(createTripRequest.getStartDate());
    }
}
