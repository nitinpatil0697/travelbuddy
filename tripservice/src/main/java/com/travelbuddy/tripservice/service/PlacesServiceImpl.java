package com.travelbuddy.tripservice.service;

import com.travelbuddy.tripservice.api.response.GeneralResponse;
import com.travelbuddy.tripservice.helper.CsvHelper;
import com.travelbuddy.tripservice.model.PlacesEntity;
import com.travelbuddy.tripservice.repository.PlacesRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlacesServiceImpl implements PlacesService{

    @Autowired
    private PlacesRepositoryInterface placesRepository;

    @Autowired
    private CsvHelper csvHelper;

    @Transactional
    @Override
    public ResponseEntity<GeneralResponse> savePlacesFromCSV(String filePath) {
        GeneralResponse csvResponse = new GeneralResponse();
        List<PlacesEntity> placesList = csvHelper.csvToPlaces(filePath);
        placesRepository.saveAll(placesList);
        return new ResponseEntity<>(csvResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GeneralResponse> getAllPlaces() {
        GeneralResponse allPlacesResponse = new GeneralResponse();
        List<PlacesEntity> placesList = placesRepository.findAll();
        allPlacesResponse.setStatus("SUCCESS");
        allPlacesResponse.setMessage("All Places fetched Successfully");
        allPlacesResponse.setResult(placesList);
        return new ResponseEntity<>(allPlacesResponse , HttpStatus.OK);
    }

    /**
     * Get place by code
     *
     * @param code
     * @return
     */
    public PlacesEntity getPlaceByCode(String code) {
        return placesRepository.findByPlaceId(code);
    }
}
