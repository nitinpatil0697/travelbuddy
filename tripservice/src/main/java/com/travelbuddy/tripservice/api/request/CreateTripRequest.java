package com.travelbuddy.tripservice.api.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class CreateTripRequest {

    private String tripName;

    private String tripType;

    private String destination;

    private String startLocation;

    private Date startDate; // Start date of the trip

    private Date endDate;   // End date of the trip

    private String description;  // Optional trip description

    private String touristPlaces; // List of tourist places to visit

    private Double estimatedExpenses;   // Estimated expenses for the trip
}
