package com.travelbuddy.tripservice.api.request;
import java.util.List;

public class CalculateExpenseRequest {
    private Long tripId;

    private String tripName;

    private Double numberOfDays;

    private List<Place> places;

    private Double transportationCost;

    private Double accommodationPerNight;

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public Double getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Double numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public Double getTransportationCost() {
        return transportationCost;
    }

    public void setTransportationCost(Double transportationCost) {
        this.transportationCost = transportationCost;
    }

    public Double getAccommodationPerNight() {
        return accommodationPerNight;
    }

    public void setAccommodationPerNight(Double accommodationPerNight) {
        this.accommodationPerNight = accommodationPerNight;
    }

    @Override
    public String toString() {
        return "CalculateExpenseRequest{" +
                "tripId=" + tripId +
                ", tripName='" + tripName + '\'' +
                ", numberOfDays=" + numberOfDays +
                ", places=" + places +
                ", transportationCost=" + transportationCost +
                ", accommodationPerNight=" + accommodationPerNight +
                '}';
    }

}
