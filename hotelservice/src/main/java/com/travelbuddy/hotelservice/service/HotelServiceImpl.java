package com.travelbuddy.hotelservice.service;

import com.travelbuddy.hotelservice.api.response.GeneralResponse;
import com.travelbuddy.hotelservice.model.HotelEntity;
import com.travelbuddy.hotelservice.repository.HotelRepositoryInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelServiceImpl implements HotelService {
    private final HotelRepositoryInterface hotelRepositoryInterface;

    /**
     * Get All the Hotels
     *
     * @return
     */
    @Override
    public ResponseEntity<GeneralResponse> getAllHotel() {
        log.info("Get All Hotel Api called.");
        GeneralResponse hotelResponse = new GeneralResponse();
        List<HotelEntity> allHotel = hotelRepositoryInterface.findAll();
        hotelResponse.setStatus("SUCCESS");
        hotelResponse.setMessage("Hotel List fetched successfully");
        hotelResponse.setResult(allHotel);
        log.info("Hotel List fetched successfully.");
        return new ResponseEntity<>(hotelResponse, HttpStatus.OK);
    }

    /**
     * Get Hotel based on code
     *
     * @return
     */
    @Override
    public ResponseEntity<GeneralResponse> getHotelByCode(String code) {
        log.info("Get Hotel Api called.");
        GeneralResponse hotelResponse = new GeneralResponse();
        HotelEntity hotel = hotelRepositoryInterface.findByHotelCode(code);
        hotelResponse.setStatus("SUCCESS");
        hotelResponse.setMessage("Hotel fetched successfully");
        hotelResponse.setResult(hotel);
        log.info("Hotel fetched successfully.");
        return new ResponseEntity<>(new GeneralResponse(), HttpStatus.OK);
    }
}
