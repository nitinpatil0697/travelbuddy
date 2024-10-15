package com.travelbuddy.hotelservice.service;

import com.travelbuddy.hotelservice.api.request.FilterParamRequest;
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        log.info("Get Hotel Api called.{}", code);
        GeneralResponse hotelResponse = new GeneralResponse();
        HotelEntity hotel = hotelRepositoryInterface.findByHotelCode(code);
        hotelResponse.setStatus("SUCCESS");
        hotelResponse.setMessage("Hotel fetched successfully");
        hotelResponse.setResult(hotel);
        log.info("Hotel fetched successfully.");
        return new ResponseEntity<>(hotelResponse, HttpStatus.OK);
    }

    /**
     * Get list of hotels based on rating, city and its values
     *
     * @param filterParamReq
     * @return
     */
    @Override
    public ResponseEntity<GeneralResponse> getHotelByFilterParam(FilterParamRequest filterParamReq) {
        log.info("Get Hotel API called with filter params.");
        GeneralResponse hotelListResponse = new GeneralResponse();
        hotelListResponse.setStatus("FAILURE");

        if (!filterParamReq.getFilterField().isEmpty() && !filterParamReq.getFilterValue().isEmpty()) {
            List<HotelEntity> filteredHotels = new ArrayList<>();


            switch (filterParamReq.getFilterField().toLowerCase()) {
                case "rating":
                    filteredHotels = hotelRepositoryInterface.findByRating(Double.parseDouble(filterParamReq.getFilterValue()));
                    break;
                case "city":
                    filteredHotels = hotelRepositoryInterface.findByCity(filterParamReq.getFilterValue());
                    break;
                case "cost":
                    if (!filterParamReq.getFilterCondition().isEmpty()) {
                        if (filterParamReq.getFilterCondition().equals("greater")) {
                            // Fetch hotels where cost is greater than the provided value
                            filteredHotels = hotelRepositoryInterface.findHotelsByCostGreaterThan(Double.parseDouble(filterParamReq.getFilterValue()));
                        } else if (filterParamReq.getFilterCondition().equals("lesser")) {
                            // Fetch hotels where cost is less than the provided value
                            filteredHotels  = hotelRepositoryInterface.findHotelsByCostLessThan(Double.parseDouble(filterParamReq.getFilterValue()));
                        }
                    } else {
                        filteredHotels = hotelRepositoryInterface.findByCost(Double.parseDouble(filterParamReq.getFilterValue()));
                    }
                    break;
                default:
                    log.error("Invalid filter field: {}", filterParamReq.getFilterField());
                    break;
            }

            if (!filteredHotels.isEmpty()) {
                hotelListResponse.setStatus("SUCCESS");
                hotelListResponse.setResult(filteredHotels);
            } else {
                hotelListResponse.setMessage("No hotels found for the given filter.");
            }
        } else {
            hotelListResponse.setMessage("Filter field or value is missing.");
        }

        hotelListResponse.setMessage("Hotel List fetched successfully");
        return new ResponseEntity<>(hotelListResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GeneralResponse> getHotelByCodes(String codes) {
        log.info("Get Hotel API called with codes : {}", codes);
        String[] hotelCodesList = codes.split(",");
        GeneralResponse hotelResponse = new GeneralResponse();
        List<HotelEntity> hotelsData = new ArrayList<>();
        for (String code : hotelCodesList) {
            HotelEntity hotel = hotelRepositoryInterface.findByHotelCode(code);
            hotelsData.add(hotel);
        }
        hotelResponse.setStatus("SUCCESS");
        hotelResponse.setMessage("Hotel fetched successfully");
        hotelResponse.setResult(hotelsData);
        log.info("Hotel data fetched successfully.");
        return new ResponseEntity<>(hotelResponse, HttpStatus.OK);
    }
}
