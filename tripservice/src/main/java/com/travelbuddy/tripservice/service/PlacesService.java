package com.travelbuddy.tripservice.service;


import com.travelbuddy.tripservice.api.response.GeneralResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface PlacesService {
    ResponseEntity<GeneralResponse> savePlacesFromCSV(String filePath);
}
